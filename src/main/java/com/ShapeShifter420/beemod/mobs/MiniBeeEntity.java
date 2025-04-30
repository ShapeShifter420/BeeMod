package com.ShapeShifter420.beemod.mobs;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MiniBeeEntity extends Bee {
    public MiniBeeEntity(EntityType<? extends Bee> type, Level world) {
        super(type, world);
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MiniBeeEntity.class, false));

    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        // Remove the normal bee goals that might interfere
        this.goalSelector.getAvailableGoals().removeIf(goal -> goal.getGoal().getClass().getSimpleName().equals("BeePollinateGoal"));
    }

    @Override
    public boolean isAggressive() {
        return true; // Всегда агрессивны
    }

}