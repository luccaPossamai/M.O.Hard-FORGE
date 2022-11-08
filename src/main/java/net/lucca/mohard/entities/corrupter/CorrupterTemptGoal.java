package net.lucca.mohard.entities.corrupter;

import net.lucca.mohard.itens.essence.EssenceItem;
import net.lucca.mohard.itens.essenceBundle.EssenceBundleItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;

class CorrupterTemptGoal extends TemptGoal {

    @Nullable
    private Player selectedPlayer;
    private final Corrupter corrupter;

    public CorrupterTemptGoal(Corrupter p_28219_, double p_28220_, Ingredient p_28221_, boolean p_28222_) {
        super(p_28219_, p_28220_, p_28221_, p_28222_);
        this.corrupter = p_28219_;
    }

    @Override
    public void stop() {
        if(this.player != null && this.mob.distanceToSqr(this.player) < 2F) {
            ItemStack item = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack stack = item.getItem() instanceof EssenceItem || item.getItem() instanceof EssenceBundleItem ? item : player.getItemInHand(InteractionHand.OFF_HAND);
            this.corrupter.setItemInHand(InteractionHand.MAIN_HAND, stack);
            this.player.setItemInHand(item.getItem() instanceof EssenceItem || item.getItem() instanceof EssenceBundleItem ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, ItemStack.EMPTY);
        }
        super.stop();
    }

    public void tick() {
        this.mob.getLookControl().setLookAt(this.player, (float)(this.mob.getMaxHeadYRot() + 20), (float)this.mob.getMaxHeadXRot());
        if (!this.mob.getNavigation().moveTo(this.player, 0.6)) {
            this.stop();
        }

        if (this.selectedPlayer == null) {
            this.selectedPlayer = this.player;
        } else {
            this.selectedPlayer = null;
        }

    }

    public boolean canUse() {
        return super.canUse() && !this.corrupter.stoleEssence();
    }
}
