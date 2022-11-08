package net.lucca.mohard.data.advancementes;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModBlocks;
import net.lucca.mohard.init.ModEssences;
import net.lucca.mohard.init.ModItems;
import net.lucca.mohard.init.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {

    public ModAdvancementProvider(DataGenerator data, ExistingFileHelper helper) {
        super(data, helper);
    }

    @Override
    protected void registerAdvancements(@NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper fileHelper) {
        super.registerAdvancements(consumer, fileHelper);

        Advancement advancement = Advancement.Builder.advancement().display(ModEssences.ZOMBIE_ESSENCE.get(), path("root", true), path("root", false), loc("textures/gui/advancements/backgrounds/deepslate.png"), FrameType.TASK, false, false, false).addCriterion("get_essence", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModTags.Items.ESSENCE).build())).save(consumer, "essentials/root");
        Advancement.Builder.advancement().parent(advancement).display(ModEssences.BLUE_AXOLOTL_ESSENCE.get(), path("blue_axolotl", true), path("blue_axolotl", false), null, FrameType.CHALLENGE, true, true, true).addCriterion("blue_axolotl", InventoryChangeTrigger.TriggerInstance.hasItems(ModEssences.BLUE_AXOLOTL_ESSENCE.get())).save(consumer, "essentials/blue_axolotl");
        Advancement.Builder.advancement().parent(advancement).display(ModEssences.KILLER_BUNNY_ESSENCE.get(), path("killer_bunny", true), path("killer_bunny", false), null, FrameType.CHALLENGE, true, true, true).addCriterion("killer_bunny", InventoryChangeTrigger.TriggerInstance.hasItems(ModEssences.KILLER_BUNNY_ESSENCE.get())).save(consumer, "essentials/killer_bunny");
        Advancement.Builder.advancement().parent(advancement).display(ModEssences.BRUNO_ESSENCE.get(), path("bruno", true), path("bruno", false), null, FrameType.CHALLENGE, true, true, true).addCriterion("bruno", InventoryChangeTrigger.TriggerInstance.hasItems(ModEssences.BRUNO_ESSENCE.get())).save(consumer, "essentials/bruno");
        Advancement advancement1 = Advancement.Builder.advancement().parent(advancement).display(ModItems.VILIO_INGOT.get(), path("vilio_ingot", true), path("vilio_ingot", false), null, FrameType.TASK, true, true, false).addCriterion("get_vilio_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.VILIO_INGOT.get())).save(consumer, "essentials/get_vilio_ingot");
        Advancement advancement2 = Advancement.Builder.advancement().parent(advancement1).display(ModBlocks.VILIO_ALTAR.get(), path("vilio_altar", true), path("vilio_altar", false), null, FrameType.TASK, true, true, false).addCriterion("vilio_altar", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.VILIO_ALTAR.get())).save(consumer, "essentials/vilio_altar");
        Advancement advancement1_1 = Advancement.Builder.advancement().parent(advancement1).display(ModItems.VILIO_CHESTPLATE.get(), path("vilio_tools", true), path("vilio_tools", false), null, FrameType.GOAL, true, true, false).addCriterion("vilio_tools", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModTags.Items.VILIO_TOOLS).build())).save(consumer, "essentials/vilio_tools");
        Advancement.Builder.advancement().parent(advancement1_1).display(ModItems.VILIO_HOE.get(), path("vilio_hoe", true), path("vilio_hoe", false), null, FrameType.CHALLENGE, true, true, false).addCriterion("vilio_hoe", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.VILIO_HOE.get())).save(consumer, "essentials/vilio_hoe");

    }

    private static ResourceLocation loc(String string){
        return new ResourceLocation(ModMain.MODID, string);
    }

    private static Component path(String name, boolean title){
        return Component.translatable("advancements.essentials."+name+"."+(title ? "title" : "description"));
    }
}
