package net.lucca.mohard.effects;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

public class ModBrewingRecipe implements IBrewingRecipe {
    @NotNull
    private final Potion inputPotion;
    @NotNull private final Ingredient ingredient;
    @NotNull
    private final Ingredient input;
    @NotNull private final ItemStack output;

    public ModBrewingRecipe(@NotNull ItemStack input, @NotNull Ingredient ingredient, @NotNull ItemStack output){
        this.inputPotion = PotionUtils.getPotion(input);
        this.input = Ingredient.of(input);
        this.output = output;
        this.ingredient = ingredient;
    }

    @Override
    public boolean isInput(@NotNull ItemStack stack)
    {
        return this.input.test(stack) && PotionUtils.getPotion(stack).equals(this.inputPotion);
    }

    @Override
    public @NotNull ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredient)
    {
        return isInput(input) && isIngredient(ingredient) ? getOutput().copy() : ItemStack.EMPTY;
    }


    public @NotNull Ingredient getIngredient()
    {
        return ingredient;
    }

    public @NotNull ItemStack getOutput()
    {
        return output;
    }

    @Override
    public boolean isIngredient(@NotNull ItemStack ingredient)
    {
        return this.ingredient.test(ingredient);
    }
}
