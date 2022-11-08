package net.lucca.mohard.data.client;

import com.google.common.collect.ImmutableList;
import net.lucca.mohard.init.ModBlocks;
import net.lucca.mohard.init.ModItems;
import net.lucca.mohard.init.ModTags;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    protected static final ImmutableList<ItemLike> VILIO_SMELTABLES = ImmutableList.of(ModItems.RAW_VILIO.get(), ModBlocks.VILIO_ORE.get(), ModBlocks.DEEPSLATE_VILIO_ORE.get());


    public ModRecipeProvider(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(ModBlocks.VILIO_ALTAR.get())
                .define('V', ModItems.VILIO_INGOT.get())
                .define('D', Items.POLISHED_DEEPSLATE)
                .define('E', ModTags.Items.ESSENCE)
                .pattern(" E ")
                .pattern("VVV")
                .pattern("DDD")
                .unlockedBy("has_item", has(ModTags.Items.ESSENCE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ESSENCE_EXHANGER_BLOCK.get())
                .define('C', Items.COPPER_INGOT)
                .define('D', Items.POLISHED_DEEPSLATE)
                .define('V', ModBlocks.VILIO_BLOCK.get())
                .pattern("DCD")
                .pattern("DVD")
                .pattern("CCC")
                .unlockedBy("has_item", has(Items.COPPER_INGOT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.ESSENCE_BUNDLE.get())
                .define('L', Items.RABBIT_HIDE)
                .define('S', Items.STRING)
                .define('B', Items.BLUE_DYE)
                .define('P', Items.PURPLE_DYE)
                .define('N', ModItems.NOMADS_BANNER_PATTERN.get())
                .pattern("SLS")
                .pattern("BNP")
                .pattern("LLL")
                .unlockedBy("has_item", has(ModItems.NOMADS_BANNER_PATTERN.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.MUSIC_DISC_BREATHE.get())
                .define('#', ModItems.DISC_FRAGMENT_BREATHE.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_item", has(ModItems.DISC_FRAGMENT_BREATHE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.DEEP_NETHER_PETRIFIED_TOME.get())
                .define('H', ModItems.HEART_OF_THE_GLOWING_DEPTHS.get())
                .define('M', Blocks.MAGMA_BLOCK)
                .define('N', Items.NETHERITE_SCRAP)
                .define('B', Blocks.BASALT)
                .pattern("BNB")
                .pattern("MHM")
                .pattern("BNB")
                .unlockedBy("has_item", has(ModItems.HEART_OF_THE_GLOWING_DEPTHS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.TENDER_OBSIDIAN_HELMET.get())
                .define('T', ModBlocks.TENDER_OBSIDIAN.get())
                .pattern("TTT")
                .pattern("T T")
                .unlockedBy("has_item", has(ModBlocks.TENDER_OBSIDIAN.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.TENDER_OBSIDIAN_CHESTPLATE.get())
                .define('T', ModBlocks.TENDER_OBSIDIAN.get())
                .pattern("T T")
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("has_item", has(ModBlocks.TENDER_OBSIDIAN.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.TENDER_OBSIDIAN_LEGGINGS.get())
                .define('T', ModBlocks.TENDER_OBSIDIAN.get())
                .pattern("TTT")
                .pattern("T T")
                .pattern("T T")
                .unlockedBy("has_item", has(ModBlocks.TENDER_OBSIDIAN.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.TENDER_OBSIDIAN_BOOTS.get())
                .define('T', ModBlocks.TENDER_OBSIDIAN.get())
                .pattern("T T")
                .pattern("T T")
                .unlockedBy("has_item", has(ModBlocks.TENDER_OBSIDIAN.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.WAND.get())
                .define('I', Items.IRON_INGOT)
                .define('C', ModItems.COLD_CHARGE.get())
                .pattern("  C")
                .pattern(" I ")
                .pattern("I  ")
                .unlockedBy("has_item", has(ModItems.COLD_CHARGE.get()))
                .save(consumer);

        smeltingAndBlasting(consumer, Blocks.OBSIDIAN, ModBlocks.TENDER_OBSIDIAN.get(), 5, 6000);

        RecipeProvider.oreSmelting(consumer, VILIO_SMELTABLES, ModItems.MELTED_VILIO.get(), 0.5F, 200, "melted_vilio");
        RecipeProvider.oreBlasting(consumer, VILIO_SMELTABLES, ModItems.MELTED_VILIO.get(), 0.5F, 100, "melted_vilio");
        ShapelessRecipeBuilder.shapeless(ModItems.VILIO_INGOT.get()).requires(ModItems.MELTED_VILIO.get(), 4).requires(Items.COPPER_INGOT, 4).requires(Items.GOLD_INGOT, 1).group("vilio_ingot").unlockedBy("has_melted_vilio", has(ModItems.MELTED_VILIO.get())).save(consumer);
        vilioSmithing(consumer, Items.NETHERITE_CHESTPLATE, ModItems.VILIO_CHESTPLATE.get());
        vilioSmithing(consumer, Items.NETHERITE_LEGGINGS, ModItems.VILIO_LEGGINGS.get());
        vilioSmithing(consumer, Items.NETHERITE_HELMET, ModItems.VILIO_HELMET.get());
        vilioSmithing(consumer, Items.NETHERITE_BOOTS, ModItems.VILIO_BOOTS.get());
        vilioSmithing(consumer, Items.NETHERITE_SWORD, ModItems.VILIO_SWORD.get());
        vilioSmithing(consumer, Items.NETHERITE_AXE, ModItems.VILIO_AXE.get());
        vilioSmithing(consumer, Items.NETHERITE_PICKAXE, ModItems.VILIO_PICKAXE.get());
        vilioSmithing(consumer, Items.NETHERITE_HOE, ModItems.VILIO_HOE.get());
        vilioSmithing(consumer, Items.NETHERITE_SHOVEL, ModItems.VILIO_SHOVEL.get());
        ShapelessRecipeBuilder.shapeless(ModItems.COLD_CHARGE.get()).requires(Items.FIRE_CHARGE, 1).requires(Items.BLUE_ICE, 1).unlockedBy("has_fire_charge", has(Items.FIRE_CHARGE)).save(consumer);
    }

    protected static void vilioSmithing(Consumer<FinishedRecipe> p_125995_, Item p_125996_, Item p_125997_) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(p_125996_), Ingredient.of(ModItems.VILIO_INGOT.get()), p_125997_).unlocks("has_vilio_ingot", has(ModItems.VILIO_INGOT.get())).save(p_125995_, getItemName(p_125997_) + "_smithing");
    }

    protected static void smeltingAndBlasting(Consumer<FinishedRecipe> p_125995_, ItemLike ingredient, ItemLike itemLike, float experience, int timeInTicks){
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient),itemLike, experience, timeInTicks).unlockedBy("has_obsidian", has(ingredient)).save(p_125995_, getItemName(itemLike) + "_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient),itemLike, experience, timeInTicks / 2).unlockedBy("has_obsidian", has(ingredient)).save(p_125995_, getItemName(itemLike) + "_from_blasting");
    }


}
