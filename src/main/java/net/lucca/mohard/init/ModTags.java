package net.lucca.mohard.init;


import net.lucca.mohard.ModMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {

    public static final class Items{
        public static final TagKey<Item> ESSENCE = forge("essences/essence");

        private static TagKey<Item> forge(String path){
            return ItemTags.create(new ResourceLocation("forge" ,path));

        }

        private static TagKey<Item> mod(String path){
            return ItemTags.create(new ResourceLocation(ModMain.MODID ,path));


        }
    }

}
