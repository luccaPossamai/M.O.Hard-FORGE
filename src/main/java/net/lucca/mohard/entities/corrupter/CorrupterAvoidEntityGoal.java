package net.lucca.mohard.entities.corrupter;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

class CorrupterAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
    private final Corrupter corrupter;

    public CorrupterAvoidEntityGoal(Corrupter p_28191_, Class<T> p_28192_, float p_28193_, double p_28194_, double p_28195_) {
        super(p_28191_, p_28192_, p_28193_, p_28194_, p_28195_, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
        this.corrupter = p_28191_;
    }

    @Override
    public void start() {
        super.start();
    }

    public boolean canUse() {
        return this.corrupter.stoleEssence() && super.canUse();
    }

    public boolean canContinueToUse() {
        return this.corrupter.stoleEssence() && super.canContinueToUse();
    }
}
