package com.ShapeShifter420.beemod.items;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

import com.ShapeShifter420.beemod.mobs.MiniBeeEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownBeeGranate extends ThrowableItemProjectile {
    private static final EntityDimensions ZERO_SIZED_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);

    public ThrownBeeGranate(EntityType<? extends net.minecraft.world.entity.projectile.ThrownEgg> p_37473_, Level p_37474_) {
        super(p_37473_, p_37474_);
    }

    public ThrownBeeGranate(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(EntityType.EGG, livingEntity, level, itemStack);
    }

    public ThrownBeeGranate(Level level, double x, double y, double z, ItemStack itemStack) {
        super(EntityType.EGG, x, y, z, level, itemStack);
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 3) {
            double d0 = 0.08;

            for (int i = 0; i < 8; i++) {
                this.level()
                        .addParticle(
                                new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                (this.random.nextFloat() - 0.5) * 0.08,
                                (this.random.nextFloat() - 0.5) * 0.08,
                                (this.random.nextFloat() - 0.5) * 0.08
                        );
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        onHit(entityHitResult);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            try {
                for (int j = 0; j < SecureRandom.getInstanceStrong().nextInt(4, 10); j++) {
                    Bee bee = new MiniBeeEntity(EntityType.BEE,this.level());
                    if (bee != null) {
                        bee.deathTime = 10000;
                        bee.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        if (!bee.fudgePositionAfterSizeChange(ZERO_SIZED_DIMENSIONS)) {
                            break;
                        }

                        this.level().addFreshEntity(bee);
                    }

                    this.level().broadcastEntityEvent(this, (byte) 3);
                    this.discard();
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EGG;
    }
}
