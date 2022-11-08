package net.lucca.mohard.data.client;


import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModBlocks;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlocksTagProvider extends BlockTagsProvider {


    public ModBlocksTagProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen, ModMain.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(){
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.VILIO_ALTAR.get() )
                .add(ModBlocks.ESSENCE_EXHANGER_BLOCK.get())
                .add(ModBlocks.VILIO_ORE.get())
                .add(ModBlocks.DEEPSLATE_VILIO_ORE.get())
        ;

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.VILIO_ALTAR.get())
                .add(ModBlocks.ESSENCE_EXHANGER_BLOCK.get())
                .add(ModBlocks.VILIO_ORE.get())
                .add(ModBlocks.DEEPSLATE_VILIO_ORE.get())
        ;

    }



}
