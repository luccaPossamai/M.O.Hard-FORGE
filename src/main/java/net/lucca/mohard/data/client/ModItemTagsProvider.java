package net.lucca.mohard.data.client;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModEssences;
import net.lucca.mohard.init.ModTags;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput p_255871_, CompletableFuture<HolderLookup.Provider> p_256035_, TagsProvider<Block> p_256467_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_255871_, p_256035_, p_256467_, ModMain.MODID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider p_256380_){
         for(RegistryObject<Item> en: ModEssences.ESSENCE_ITEMS.getEntries()) {
            if (en.get() instanceof EssenceItem) {
                tag(ModTags.Items.ESSENCE).add(en.get());
            }
        }

    }
}
