package net.lucca.mohard.data.client;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModBiomeTagsProvider extends BiomeTagsProvider {
    public ModBiomeTagsProvider(DataGenerator p_211094_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_211094_, ModMain.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(){

        tag(ModTags.Biomes.HAS_ICE_ISOLATOR_HOUSE)
                .add(Biomes.SNOWY_TAIGA)
                .add(Biomes.SNOWY_PLAINS)
                .add(Biomes.SNOWY_SLOPES);
        tag(ModTags.Biomes.HAS_NOMAD_CAMP)
                .addTag(Tags.Biomes.IS_DENSE_OVERWORLD)
                .addTag(Tags.Biomes.IS_CONIFEROUS)
                .addTag(Tags.Biomes.IS_WET_OVERWORLD);

    }
}
