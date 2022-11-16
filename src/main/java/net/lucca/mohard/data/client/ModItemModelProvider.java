package net.lucca.mohard.data.client;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.itens.essence.EssenceDataHelper;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.lucca.mohard.init.ModEssences;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ModMain.MODID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandHeld = getExistingFile(mcLoc("item/handheld"));
        ModelFile itemSpawnEgg = getExistingFile(mcLoc("item/template_spawn_egg"));
        withExistingParent("vilio_altar", new ResourceLocation(ModMain.MODID, "block/vilio_altar"));
        withExistingParent("essence_exchanger", new ResourceLocation(ModMain.MODID, "block/essence_exchanger_0"));
        withExistingParent("vilio_ore", mcLoc("mohard:block/vilio_ore"));
        withExistingParent("deepslate_vilio_ore", mcLoc("mohard:block/deepslate_vilio_ore"));
        withExistingParent("vilio_block", mcLoc("mohard:block/vilio_block"));
        withExistingParent("tender_obsidian", mcLoc("mohard:block/tender_obsidian"));
        ModelFile bundleFilled = getBuilder("essence_bundle_filled").parent(itemGenerated).texture("layer0","item/essence_bundle_filled");
        getBuilder("essence_bundle").texture("layer0", "item/essence_bundle").parent(itemGenerated).override().predicate(new ResourceLocation(ModMain.MODID, "filled"), 0.0000001F).model(bundleFilled);
        ModelFile dashArtifact1 = getBuilder("vilio_idol_1").parent(itemGenerated).texture("layer0","item/vilio_idol_1");
        ModelFile dashArtifact2 = getBuilder("vilio_idol_2").parent(itemGenerated).texture("layer0","item/vilio_idol_2");
        getBuilder("vilio_idol").texture("layer0", "item/vilio_idol_0").parent(itemGenerated)
                .override().predicate(new ResourceLocation(ModMain.MODID, "state"), 1F).model(dashArtifact1).end()
                .override().predicate(new ResourceLocation(ModMain.MODID, "state"), 2F).model(dashArtifact2).end();

        builder(itemGenerated, "disc_fragment_breathe", "disc_fragment_breathe");
        builder(itemGenerated, "nomads_banner_pattern", "nomads_banner_pattern");
        builder(itemGenerated, "essence_banner_pattern", "essence_banner_pattern");
        builder(itemGenerated, "music_disc_breathe", "music_disc_breathe");
        builder(itemGenerated, "empty_essence", "empty_essence");
        builder(itemGenerated, "tender_obsidian_boots", "tender_obsidian_boots");
        builder(itemGenerated, "tender_obsidian_leggings", "tender_obsidian_leggings");
        builder(itemGenerated, "tender_obsidian_chestplate", "tender_obsidian_chestplate");
        builder(itemGenerated, "tender_obsidian_helmet", "tender_obsidian_helmet");
        builder(itemGenerated, "vilio_boots", "vilio_boots");
        builder(itemGenerated, "vilio_leggings", "vilio_leggings");
        builder(itemGenerated, "vilio_chestplate", "vilio_chestplate");
        builder(itemGenerated, "vilio_helmet", "vilio_helmet");
        builder2(itemGenerated, "magic_boots", "magic_boots", "magic_boots_overlay");
        builder2(itemGenerated, "magic_leggings", "magic_leggings", "magic_leggings_overlay");
        builder2(itemGenerated, "magic_chestplate", "magic_chestplate", "magic_chestplate_overlay");
        builder2(itemGenerated, "magic_helmet", "magic_helmet", "magic_helmet_overlay");
        builder(itemGenerated, "raw_vilio", "raw_vilio");
        builder(itemGenerated, "melted_vilio", "melted_vilio");
        builder(itemGenerated, "vilio_ingot", "vilio_ingot");
        builder(itemHandHeld,"vilio_axe", "vilio_axe");
        builder(itemHandHeld,"vilio_pickaxe", "vilio_pickaxe");
        builder(itemHandHeld,"vilio_hoe", "vilio_hoe");
        builder(itemHandHeld,"vilio_shovel", "vilio_shovel");
        builder(itemHandHeld,"vilio_sword", "vilio_sword");
        builder(itemHandHeld,"floyd_dagger", "floyd_dagger");
        builder(itemHandHeld,"algid_axe", "algid_axe");
        builder(itemGenerated,"deep_nether_petrified_tome", "deep_nether_petrified_tome");
        builder(itemGenerated, "heart_of_the_glowing_depths", "heart_of_the_glowing_depths");
        builder(itemHandHeld, "wand", "wand");
        builder2(itemSpawnEgg, "essence", "essence_1", "essence_2");
        builder(itemGenerated, "easter_essence", "easter_essence");
        builder(itemGenerated, "cold_charge", "cold_charge");
        EssenceDataHelper.printEssence();


        for(RegistryObject<Item> en: ModEssences.ESSENCE_ITEMS.getEntries()) {
            if (en.get() instanceof EssenceItem essence) {
                String nome = en.getId().toString();
                if(essence.hasOwnTexture()) continue;

                withExistingParent(nome, modLoc("item/essence"));
            }
        }



    }




    private ItemModelBuilder builder(ModelFile itemGenerated, String nome, String texture) {
        return getBuilder(nome).parent(itemGenerated).texture("layer0", "item/"+texture);

    }
    private ItemModelBuilder builder2(ModelFile itemGenerated, String nome, String texture1, String texture2) {
        return getBuilder(nome).parent(itemGenerated).texture("layer0", "item/"+texture1).texture("layer1", "item/"+texture2);
    }

}
