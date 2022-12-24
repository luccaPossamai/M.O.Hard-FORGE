package net.lucca.mohard.data.client;

import net.lucca.mohard.init.ModBlocks;
import net.lucca.mohard.init.ModItems;
import net.lucca.mohard.init.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }


    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ENDER_ALTAR.get())
                .define('O', Items.OBSIDIAN)
                .define('Y', Items.ENDER_EYE)
                .define('E', ModTags.Items.ESSENCE)
                .pattern("OEO")
                .pattern("OYO")
                .unlockedBy("has_item", has(ModTags.Items.ESSENCE))
                .save(consumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DEEP_NETHER_PETRIFIED_TOME.get())
                .define('H', ModItems.HEART_OF_THE_GLOWING_DEPTHS.get())
                .define('M', Blocks.MAGMA_BLOCK)
                .define('N', Items.NETHERITE_SCRAP)
                .define('B', Blocks.BASALT)
                .pattern("BNB")
                .pattern("MHM")
                .pattern("BNB")
                .unlockedBy("has_item", has(ModItems.HEART_OF_THE_GLOWING_DEPTHS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SOUL_FIREBALL_SCEPTER.get())
                .define('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .define('C', ModItems.SOUL_FIRE_CHARGE.get())
                .pattern(" SC")
                .pattern(" SS")
                .pattern("S  ")
                .unlockedBy("has_item", has(ModItems.SOUL_FIRE_CHARGE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SOUL_FIRE_CHARGE.get(), 3).requires(Items.GUNPOWDER).requires(Items.BLAZE_POWDER).requires(Ingredient.of(ItemTags.SOUL_FIRE_BASE_BLOCKS)).unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER)).save(consumer);
    }

}
