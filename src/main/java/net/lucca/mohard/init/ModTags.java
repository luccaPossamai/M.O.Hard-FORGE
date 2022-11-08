package net.lucca.mohard.init;


import net.lucca.mohard.ModMain;
import net.minecraft.core.Registry;
import net.minecraft.tags.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModTags {
    public static final class Blocks{

        private static TagKey<Block> forge(String path){
            return BlockTags.create(new ResourceLocation("forge", path));

        }

        private static TagKey<Block> mod(String path){
            return BlockTags.create(new ResourceLocation(ModMain.MODID ,path));

        }

    }

    public static final class Items{
        public static final TagKey<Item> ESSENCE = forge("essences/essence");
        public static final TagKey<Item> THROWABLE_ITEMS = forge("throwable_items");
        public static final TagKey<Item> VILIO_TOOLS = forge("vilio_tools");

        private static TagKey<Item> forge(String path){
            return ItemTags.create(new ResourceLocation("forge" ,path));

        }

        private static TagKey<Item> mod(String path){
            return ItemTags.create(new ResourceLocation(ModMain.MODID ,path));


        }
    }
    public static final class EntityType{

        private static TagKey<net.minecraft.world.entity.EntityType<?>> forge(String path){
            return create(new ResourceLocation("forge" ,path));

        }

        private static TagKey<net.minecraft.world.entity.EntityType<?>> mod(String path){
            return create(new ResourceLocation(ModMain.MODID ,path));
        }

        private static TagKey<net.minecraft.world.entity.EntityType<?>> create(ResourceLocation p_203849_) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, p_203849_);
        }
    }

    public static final class Biomes{

        public static final TagKey<Biome> HAS_ICE_ISOLATOR_HOUSE = mod("has_structure/ice_isolator_house");
        public static final TagKey<Biome> HAS_NOMAD_CAMP = mod("has_structure/nomad_camp");

        private static TagKey<Biome> forge(String path){
            return create(new ResourceLocation("forge" ,path));

        }

        private static TagKey<Biome> mod(String path){
            return create(new ResourceLocation(ModMain.MODID ,path));
        }

        private static TagKey<Biome> create(ResourceLocation p_207631_) {
            return TagKey.create(Registry.BIOME_REGISTRY, p_207631_);
        }
    }

    public static final class BannerPatterns{

        public static final TagKey<BannerPattern> PATTERN_ITEM_ESSENCE = mod("pattern_item/essence");
        public static final TagKey<BannerPattern> PATTERN_ITEM_NOMADS = mod("pattern_item/nomads");

        private static TagKey<BannerPattern> forge(String path){
            return create(new ResourceLocation("forge" ,path));


        }

        private static TagKey<BannerPattern> mod(String path){
            return create(new ResourceLocation(ModMain.MODID ,path));
        }

        private static TagKey<BannerPattern> create(ResourceLocation p_207631_) {
            return TagKey.create(Registry.BANNER_PATTERN_REGISTRY, p_207631_);
        }
    }

    public static final class Structures{

        public static final TagKey<Structure> ON_ISOLATOR_EXPLORER_MAPS = mod("on_isolator_explorer_maps");
        public static final TagKey<Structure> ON_CORRUPTER_WAGON_EXPLORER_MAPS = mod("on_corrupter_wagon_explorer_maps");


        private static TagKey<Structure> forge(String path){
            return create(new ResourceLocation("forge" ,path));


        }

        private static TagKey<Structure> mod(String path){
            return create(new ResourceLocation(ModMain.MODID ,path));
        }

        private static TagKey<Structure> create(ResourceLocation p_207631_) {
            return TagKey.create(Registry.STRUCTURE_REGISTRY, p_207631_);
        }
    }

}
