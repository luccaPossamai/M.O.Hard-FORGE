package net.lucca.mohard.enchantments;

import net.lucca.mohard.itens.artifacts.GenericArtifact;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class Pishogue extends Enchantment implements ArtifactEnchantment{

    public Pishogue(EquipmentSlot... p_44678_) {
        super(Rarity.COMMON, ModEnchantmentCategory.ARTIFACT, p_44678_);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinCost(int p_44633_) {
        return (p_44633_ * 11) - 10;
    }

    @Override
    public int getMaxCost(int p_44646_) {
        return this.getMinCost(p_44646_) + 20;
    }

    @Override
    public boolean canEnchant(ItemStack p_44689_) {
        return p_44689_.getItem() instanceof GenericArtifact;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
        return stack.getItem() instanceof GenericArtifact || super.canApplyAtEnchantingTable(stack);
    }
}
