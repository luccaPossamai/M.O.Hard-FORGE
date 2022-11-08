package net.lucca.mohard.enchantments;

import net.lucca.mohard.itens.artifacts.GenericArtifact;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class DoubleBane extends Enchantment implements ArtifactEnchantment {

    public DoubleBane(EquipmentSlot... p_44678_) {
        super(Rarity.COMMON, ModEnchantmentCategory.ARTIFACT, p_44678_);
    }

    @Override
    protected boolean checkCompatibility(Enchantment p_44690_) {
        return super.checkCompatibility(p_44690_) && !(p_44690_ instanceof HalfBane);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinCost(int p_44633_) {
        return 1;
    }

    @Override
    public int getMaxCost(int p_44646_) {
        return 50;
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
