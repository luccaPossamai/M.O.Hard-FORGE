package net.lucca.mohard.itens.tools;

import net.lucca.mohard.init.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class VilioTier implements Tier{

    @Override
    public int getUses() {
        return 3062;
    }

    @Override
    public float getSpeed() {
        return 10F;
    }

    @Override
    public float getAttackDamageBonus() {
        return 5F;
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public int getEnchantmentValue() {
        return 5;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.VILIO_INGOT.get());
    }
}
