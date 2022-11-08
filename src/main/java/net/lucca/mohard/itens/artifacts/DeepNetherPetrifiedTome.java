package net.lucca.mohard.itens.artifacts;

import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.init.ModDamageSources;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DeepNetherPetrifiedTome extends GenericArtifact implements IForgeItem {

    private static final int ENTITIES_CAPACITY = 8;

    public DeepNetherPetrifiedTome(Properties p_i48487_1_) {
        super(p_i48487_1_, 1, 15 * 20, true);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 64;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(this.canCast(player, itemStack) && player instanceof ServerPlayer && this.isEnabled(itemStack)) {
            TargetingConditions pred = TargetingConditions.forCombat().range(5);
            List<LivingEntity> livingEntityList = world.getNearbyEntities(LivingEntity.class, pred, player, player.getBoundingBox().inflate(5));
            if (livingEntityList.size() > 0) {
                double baseDamage = player.getAttributeValue(ModAttributes.MAGIC_DAMAGE) * this.getMagicMultiplier(player, itemStack);
                double dano = Math.min(2 * baseDamage, (baseDamage * ENTITIES_CAPACITY) / livingEntityList.size());
                livingEntityList.forEach(livingEntity -> livingEntity.hurt(ModDamageSources.ancientBook(player, player), (float) dano));
                generateCooldown(player, itemStack);
                this.hurtItem(player, itemStack);
                return InteractionResultHolder.consume(player.getItemInHand(hand));
            }
        }
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack p_41402_, ItemStack p_41403_) {
        return p_41403_.is(Blocks.MAGMA_BLOCK.asItem());
    }
}
