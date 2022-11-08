package net.lucca.mohard.data.client;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModStructures;
import net.lucca.mohard.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModStructureTagsProvider extends StructureTagsProvider {

    public ModStructureTagsProvider(DataGenerator p_236437_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_236437_, ModMain.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Structures.ON_ISOLATOR_EXPLORER_MAPS).add(ModStructures.ICE_ISOLATOR_HOUSE_STRUCTURE.get());
    }
}
