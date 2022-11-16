package net.lucca.mohard.entities.iceIsolator;

import net.minecraft.world.entity.ai.goal.RangedAttackGoal;

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
