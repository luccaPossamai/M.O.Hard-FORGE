package net.lucca.mohard.itens.artifacts;

import net.lucca.mohard.entities.coldfireball.SoulFireball;
import net.lucca.mohard.init.ModAttributes;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SoulFireballScepter extends GenericArtifact {

    public SoulFireballScepter(Properties properties) {
        super(properties, 1.0F, 20, true);
    }

    @Override
    public boolean isValidRepairItem(ItemStack p_41402_, ItemStack p_41403_) {
        return p_41403_.is(Items.FIRE_CHARGE);
    }

    protected boolean isEnabled(ItemStack p_41141_) {
        return (p_41141_.getDamageValue()) < p_41141_.getMaxDamage() - 1;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack itemStack = player.getItemInHand(hand);
        if(!level.isClientSide && this.canCast(player, itemStack) && this.isEnabled(itemStack)) {
            this.generateCooldown(player, itemStack);
            this.hurtItem(player, itemStack);
            double damage = player.getAttributeValue(ModAttributes.MAGIC_DAMAGE) * ((GenericArtifact) itemStack.getItem()).getMagicMultiplier(player, itemStack);

            float x = -Mth.sin(player.getYRot() * ((float) Math.PI / 180F)) * Mth.cos(player.getXRot() * ((float) Math.PI / 180F));
            float y = -Mth.sin((player.getXRot()) * ((float) Math.PI / 180F));
            float z = Mth.cos(player.getYRot() * ((float) Math.PI / 180F)) * Mth.cos(player.getXRot() * ((float) Math.PI / 180F));
            SoulFireball smallfireball = new SoulFireball(player.level, player, x, y, z, damage);
            smallfireball.setPos(smallfireball.getX(), player.getY(0.5D) + 0.5D, smallfireball.getZ());
            player.level.addFreshEntity(smallfireball);
            return InteractionResultHolder.consume(itemStack);
        }
        return super.use(level, player, hand);
    }
}


