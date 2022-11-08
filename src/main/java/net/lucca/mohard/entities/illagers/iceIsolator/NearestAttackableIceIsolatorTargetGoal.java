package net.lucca.mohard.entities.illagers.iceIsolator;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class NearestAttackableIceIsolatorTargetGoal extends NearestAttackableTargetGoal {

    private final IceIsolator iceIsolator;

    public NearestAttackableIceIsolatorTargetGoal(IceIsolator p_26060_, Class p_26061_, boolean p_26062_) {
        super(p_26060_, p_26061_, p_26062_);
        this.iceIsolator = p_26060_;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.iceIsolator.isBruno();
    }
}
