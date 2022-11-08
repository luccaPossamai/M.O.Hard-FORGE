package net.lucca.mohard.entities.illagers.iceIsolator;

import net.lucca.mohard.itens.artifacts.algidAxe.AlgidAxe;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.Objects;

public class AlgidAxeRangedAttackGoal<T extends IceIsolator> extends RangedAttackGoal {

    private final IceIsolator iceIsolator;

    public AlgidAxeRangedAttackGoal(IceIsolator p_25768_, double p_25769_, int p_25770_, float p_25771_) {
        super(p_25768_, p_25769_, p_25770_, p_25771_);
        this.iceIsolator = p_25768_;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.iceIsolator.isRangedAttackOnCooldown();
    }
}
