package net.lucca.mohard.data.client;


import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModBlockStateProperties;
import net.lucca.mohard.init.ModBlocks;
import net.minecraft.data.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.stream.IntStream;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ModMain.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        simpleBlock(ModBlocks.ENDER_ALTAR.get(), models().cubeBottomTop("ender_altar",
                mcLoc("mohard:block/ender_altar_side"),
                mcLoc("mohard:block/ender_altar_bottom"),
                mcLoc("mohard:block/ender_altar_top")));



        ConfiguredModel[] essenceExchangerFiles = IntStream.rangeClosed(0, 3)
                .mapToObj(j -> {
                    String varName = "essence_exchanger_" + j;
                    if(j == 0) {
                        return models().cubeBottomTop(varName, mcLoc("mohard:block/essence_exchanger_side_0"),
                                mcLoc("mohard:block/essence_exchanger_bottom"),
                                mcLoc("mohard:block/essence_exchanger_top_off"));
                    } else {
                        return models().cubeBottomTop(varName, mcLoc("mohard:block/essence_exchanger_side_"+j),
                                mcLoc("mohard:block/essence_exchanger_bottom"),
                                mcLoc("mohard:block/essence_exchanger_top"));
                    }
                })
                .map(ConfiguredModel::new).toArray(ConfiguredModel[]::new);
        int j = 0;
        for(ConfiguredModel model : essenceExchangerFiles) {
            getVariantBuilder(ModBlocks.ESSENCE_EXHANGER_BLOCK.get()).partialState().with(ModBlockStateProperties.ESSENCE_EXCHANGER_CHARGES, j).setModels(model);
            j++;

        }
    }


}
