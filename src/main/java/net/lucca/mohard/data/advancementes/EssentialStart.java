package net.lucca.mohard.data.advancementes;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModEssences;
import net.lucca.mohard.init.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class EssentialStart implements AdvancementSubProvider {
    @Override
    public void generate(HolderLookup.Provider provider, Consumer<Advancement> consumer) {
        Advancement advancement = Advancement.Builder.advancement().display(ModEssences.ZOMBIE_ESSENCE.get(), path("root", true), path("root", false), loc("textures/gui/advancements/backgrounds/deepslate.png"), FrameType.TASK, false, false, false).addCriterion("get_essence", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModTags.Items.ESSENCE).build())).save(consumer, "essentials/root");
        Advancement.Builder.advancement().parent(advancement).display(ModEssences.BLUE_AXOLOTL_ESSENCE.get(), path("blue_axolotl", true), path("blue_axolotl", false), null, FrameType.CHALLENGE, true, true, true).addCriterion("blue_axolotl", InventoryChangeTrigger.TriggerInstance.hasItems(ModEssences.BLUE_AXOLOTL_ESSENCE.get())).save(consumer, "essentials/blue_axolotl");
        Advancement.Builder.advancement().parent(advancement).display(ModEssences.KILLER_BUNNY_ESSENCE.get(), path("killer_bunny", true), path("killer_bunny", false), null, FrameType.CHALLENGE, true, true, true).addCriterion("killer_bunny", InventoryChangeTrigger.TriggerInstance.hasItems(ModEssences.KILLER_BUNNY_ESSENCE.get())).save(consumer, "essentials/killer_bunny");
        Advancement.Builder.advancement().parent(advancement).display(ModEssences.BRUNO_ESSENCE.get(), path("bruno", true), path("bruno", false), null, FrameType.CHALLENGE, true, true, true).addCriterion("bruno", InventoryChangeTrigger.TriggerInstance.hasItems(ModEssences.BRUNO_ESSENCE.get())).save(consumer, "essentials/bruno");
   }

    private static ResourceLocation loc(String string){
        return new ResourceLocation(ModMain.MODID, string);
    }

    private static Component path(String name, boolean title){
        return Component.translatable("advancements.essentials."+name+"."+(title ? "title" : "description"));
    }
}
