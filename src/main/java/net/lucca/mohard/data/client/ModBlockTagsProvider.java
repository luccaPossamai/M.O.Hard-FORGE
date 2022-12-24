package net.lucca.mohard.data.client;


import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ModMain.MODID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider p_256380_){
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ENDER_ALTAR.get() )
                .add(ModBlocks.ESSENCE_EXHANGER_BLOCK.get())
        ;

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ENDER_ALTAR.get())
                .add(ModBlocks.ESSENCE_EXHANGER_BLOCK.get())
        ;

    }


}
