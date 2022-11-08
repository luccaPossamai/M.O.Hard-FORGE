package net.lucca.mohard.entities.illagers.iceIsolator;

import net.lucca.mohard.itens.artifacts.algidAxe.AlgidAxe;
import net.lucca.mohard.util.mechanics.damage.DirectEntityDamageSource;
import net.lucca.mohard.init.ModDamageSources;
import net.lucca.mohard.init.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class AlgidAxeMeleeAttackGoal<T extends IceIsolator> extends MeleeAttackGoal {

    protected final IceIsolator iceIsolator;
    protected final AlgidAxe algidAxe;

    public AlgidAxeMeleeAttackGoal(T p_25552_, double p_25553_, boolean p_25554_) {
        super(p_25552_, p_25553_, p_25554_);
        this.iceIsolator = p_25552_;
        if(this.iceIsolator.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AlgidAxe){
            this.algidAxe = (AlgidAxe) this.iceIsolator.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        } else {
            this.algidAxe = (AlgidAxe) ModItems.ALGID_AXE.get();
        }
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.iceIsolator.isRangedAttackOnCooldown() && !this.iceIsolator.isMeleeAttackOnCooldown();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.iceIsolator.isMeleeAttackOnCooldown();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
        double d0 = this.getAttackReachSqr(p_25557_);
        if (p_25558_ <= d0 && !this.iceIsolator.isMeleeAttackOnCooldown()) {
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            p_25557_.hurt(ModDamageSources.algidAxe(this.mob, this.mob), (float)this.algidAxe.getMagicMultiplier(p_25557_, this.iceIsolator.getItemInHand(InteractionHand.MAIN_HAND)));
            this.iceIsolator.setMeleeAttackCooldown(100);
            this.iceIsolator.setTarget(null);
        }
    }
}
