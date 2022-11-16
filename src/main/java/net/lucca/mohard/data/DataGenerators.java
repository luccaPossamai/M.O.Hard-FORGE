package net.lucca.mohard.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.lucca.mohard.ModMain;
import net.lucca.mohard.data.advancementes.ModAdvancementProvider;
import net.lucca.mohard.data.client.*;
import net.lucca.mohard.init.ModBlocks;
import net.lucca.mohard.world.gen.ModFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;

import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;


@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {


    private DataGenerators(){}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());

        features(event.includeServer(), gen, existingFileHelper, ops);

        gen.addProvider(true, new ModAdvancementProvider(gen, existingFileHelper));
        gen.addProvider(true, new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(true, new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(true, new ModLootTableProvider(gen));
        gen.addProvider(true, new ModRecipeProvider(gen));

        ModBlocksTagProvider blockTags = new ModBlocksTagProvider(gen, existingFileHelper);
        gen.addProvider(true, new ModBannerPatternTagsProvider(gen, existingFileHelper));
        gen.addProvider(true, blockTags);
        gen.addProvider(true, new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(true, new ModBiomeTagsProvider(gen, existingFileHelper));
}

    public static void features(boolean includeServer, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> ops){
        List<OreConfiguration.TargetBlockState> ESSENCE_ORE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.VILIO_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_VILIO_ORE.get().defaultBlockState()));
        HeightRangePlacement heightRangePlacement = HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32));

        Holder<ConfiguredFeature<?, ?>> essenceOre_CF = Holder.direct(new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ESSENCE_ORE_TARGET_LIST, 10)));

        Holder<ConfiguredFeature<?, ?>> deepslateEssenceOre_CF = Holder.direct(new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ESSENCE_ORE_TARGET_LIST, 10)));

        Holder<ConfiguredFeature<?, ?>> rareEssenceOre_CF = Holder.direct(new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ESSENCE_ORE_TARGET_LIST, 20)));

        Holder<ConfiguredFeature<?, ?>> rareDeepslateEssenceOre_CF = Holder.direct(new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ESSENCE_ORE_TARGET_LIST, 20)));

        Holder<PlacedFeature> placedEssenceOre = Holder.direct(new PlacedFeature(essenceOre_CF, ModFeatures.commonOrePlacement(11, heightRangePlacement)));

        Holder<PlacedFeature> placedDeepslateEssenceOre = Holder.direct(new PlacedFeature(deepslateEssenceOre_CF, ModFeatures.commonOrePlacement(11, heightRangePlacement)));

        Holder<PlacedFeature> placedRareEssenceOre = Holder.direct(new PlacedFeature(rareEssenceOre_CF, ModFeatures.rareOrePlacement(11, heightRangePlacement)));

        Holder<PlacedFeature> placedRareDeepslateEssenceOre = Holder.direct(new PlacedFeature(rareDeepslateEssenceOre_CF, ModFeatures.rareOrePlacement(11, heightRangePlacement)));


        BiomeModifier addEssenceOre = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedEssenceOre), GenerationStep.Decoration.UNDERGROUND_ORES);

        BiomeModifier addDeepslateEssenceOre = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedDeepslateEssenceOre), GenerationStep.Decoration.UNDERGROUND_ORES);

        BiomeModifier addRareEssenceOre = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedRareEssenceOre), GenerationStep.Decoration.UNDERGROUND_ORES);

        BiomeModifier addRareDeepslateEssenceOre = new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedRareDeepslateEssenceOre), GenerationStep.Decoration.UNDERGROUND_ORES);


        generator.addProvider(includeServer, JsonCodecProvider.forDatapackRegistry(
                generator, existingFileHelper, ModMain.MODID, ops, Registry.CONFIGURED_FEATURE_REGISTRY,
                Map.of(
                        new ResourceLocation(ModMain.MODID, "vilio_ore"), essenceOre_CF.get(),
                        new ResourceLocation(ModMain.MODID, "deepslate_vilio_ore"), deepslateEssenceOre_CF.get(),
                        new ResourceLocation(ModMain.MODID, "rare_vilio_ore"), rareEssenceOre_CF.get(),
                        new ResourceLocation(ModMain.MODID, "rare_deepslate_vilio_ore"), rareDeepslateEssenceOre_CF.get()
                )
        ));

        generator.addProvider(includeServer, JsonCodecProvider.forDatapackRegistry(
                generator, existingFileHelper, ModMain.MODID, ops, Registry.PLACED_FEATURE_REGISTRY,
                Map.of(
                        new ResourceLocation(ModMain.MODID, "vilio_ore"), placedEssenceOre.get(),
                        new ResourceLocation(ModMain.MODID, "deepslate_vilio_ore"), placedDeepslateEssenceOre.get(),
                        new ResourceLocation(ModMain.MODID, "rare_vilio_ore"), placedRareEssenceOre.get(),
                        new ResourceLocation(ModMain.MODID, "rare_deepslate_vilio_ore"), placedRareDeepslateEssenceOre.get()
                )
        ));

        generator.addProvider(includeServer, JsonCodecProvider.forDatapackRegistry(
                generator, existingFileHelper, ModMain.MODID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS,
                Map.of(
                        new ResourceLocation(ModMain.MODID, "add_vilio_ore"), addEssenceOre,
                        new ResourceLocation(ModMain.MODID, "add_deepslate_vilio_ore"), addDeepslateEssenceOre,
                        new ResourceLocation(ModMain.MODID, "add_rare_vilio_ore"), addRareEssenceOre,
                        new ResourceLocation(ModMain.MODID, "add_rare_deepslate_vilio_ore"), addRareDeepslateEssenceOre
                )
        ));
    }

}
