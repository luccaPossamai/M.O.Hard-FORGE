package net.lucca.mohard.enchantments;

import net.lucca.mohard.itens.artifacts.GenericArtifact;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ModEnchantmentCategory {

    public static void register(){}

    public static final EnchantmentCategory ARTIFACT = EnchantmentCategory.create("ARTIFACT", item -> item instanceof GenericArtifact);

}
