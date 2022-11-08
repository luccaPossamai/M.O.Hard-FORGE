package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.world.gen.structure.corrupter.CorrupterWagon;
import net.lucca.mohard.world.gen.structure.iceisolator.IceIsolatorHouse;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class ModStructures {

    public static void register(){}
    public static final DeferredRegister<Structure> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(Registry.STRUCTURE_REGISTRY, ModMain.MODID);

    public static final RegistryObject<Structure> ICE_ISOLATOR_HOUSE_STRUCTURE = DEFERRED_REGISTRY_STRUCTURE.register("ice_isolator_house", () ->
        new IceIsolatorHouse(structure(ModTags.Biomes.HAS_ICE_ISOLATOR_HOUSE, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(new MobSpawnSettings.SpawnerData(ModEntityTypes.ICE_ISOLATOR.get(), 1, 1, 1)))), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BURY))
    );

    public static final RegistryObject<Structure> CORRUPTER_WAGON_STRUCTURE = DEFERRED_REGISTRY_STRUCTURE.register("corrupter_wagon", () ->
            new CorrupterWagon(structure(ModTags.Biomes.HAS_NOMAD_CAMP, Map.of(MobCategory.CREATURE, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(new MobSpawnSettings.SpawnerData(ModEntityTypes.CORRUPTER.get(), 1, 1, 1)))), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN))
    );

    public static void register(IEventBus bus) {
        DEFERRED_REGISTRY_STRUCTURE.register(bus);
    }

    private static Structure.StructureSettings structure(TagKey<Biome> p_236546_, Map<MobCategory, StructureSpawnOverride> p_236547_, GenerationStep.Decoration p_236548_, TerrainAdjustment p_236549_) {
        return new Structure.StructureSettings(biomes(p_236546_), p_236547_, p_236548_, p_236549_);
    }

    private static Structure.StructureSettings structure(TagKey<Biome> p_236539_, GenerationStep.Decoration p_236540_, TerrainAdjustment p_236541_) {
        return structure(p_236539_, Map.of(), p_236540_, p_236541_);
    }

    private static Structure.StructureSettings structure(TagKey<Biome> p_236543_, TerrainAdjustment p_236544_) {
        return structure(p_236543_, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, p_236544_);
    }

    private static HolderSet<Biome> biomes(TagKey<Biome> p_236537_) {
        return BuiltinRegistries.BIOME.getOrCreateTag(p_236537_);
    }

}
