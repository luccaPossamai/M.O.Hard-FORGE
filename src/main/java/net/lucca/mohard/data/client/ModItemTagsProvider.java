package net.lucca.mohard.data.client;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.lucca.mohard.init.ModEssences;
import net.lucca.mohard.init.ModItems;
import net.lucca.mohard.init.ModTags;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemTagsProvider extends ItemTagsProvider {


    public ModItemTagsProvider(DataGenerator gen, BlockTagsProvider blockTags, ExistingFileHelper existingFileHelper) {
        super(gen, blockTags, ModMain.MODID, existingFileHelper);

    }

    @Override
    protected void addTags(){
        tag(ItemTags.MUSIC_DISCS).add(ModItems.MUSIC_DISC_BREATHE.get());
        tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(ModItems.MUSIC_DISC_BREATHE.get());
        tag(Tags.Items.INGOTS).add(ModItems.VILIO_INGOT.get());
        tag(ModTags.Items.THROWABLE_ITEMS).add(Items.ENDER_PEARL).add(Items.SNOWBALL).add(Items.EXPERIENCE_BOTTLE).add(Items.SPLASH_POTION).add(Items.EGG);
        tag(ModTags.Items.VILIO_TOOLS).add(ModItems.VILIO_AXE.get()).add(ModItems.VILIO_SHOVEL.get()).add(ModItems.VILIO_SWORD.get()).add(ModItems.VILIO_HOE.get()).add(ModItems.VILIO_HELMET.get()).add(ModItems.VILIO_CHESTPLATE.get()).add(ModItems.VILIO_LEGGINGS.get()).add(ModItems.VILIO_BOOTS.get());
        for(RegistryObject<Item> en: ModEssences.ESSENCE_ITEMS.getEntries()) {
            if (en.get() instanceof EssenceItem) {
                tag(ModTags.Items.ESSENCE).add(en.get());
            }
        }

    }
}
