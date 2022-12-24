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
        withExistingParent("ender_altar", new ResourceLocation(ModMain.MODID, "block/ender_altar"));
        withExistingParent("essence_exchanger", new ResourceLocation(ModMain.MODID, "block/essence_exchanger_0"));
        ModelFile bundleFilled = getBuilder("essence_bundle_filled").parent(itemGenerated).texture("layer0","item/essence_bundle_filled");
        getBuilder("essence_bundle").texture("layer0", "item/essence_bundle").parent(itemGenerated).override().predicate(new ResourceLocation(ModMain.MODID, "filled"), 0.0000001F).model(bundleFilled);

        ModelFile amethystAmulet1 = getBuilder("amethyst_amulet_1").parent(itemHandHeld).texture("layer0","item/amethyst_amulet_1");
        ModelFile amethystAmulet2 = getBuilder("amethyst_amulet_2").parent(itemHandHeld).texture("layer0","item/amethyst_amulet_2");
        ModelFile amethystAmulet3 = getBuilder("amethyst_amulet_3").parent(itemHandHeld).texture("layer0","item/amethyst_amulet_3");
        getBuilder("amethyst_amulet").texture("layer0", "item/amethyst_amulet_0").parent(itemHandHeld)
                .override().predicate(new ResourceLocation(ModMain.MODID, "binding"), 1F).model(amethystAmulet1).end()
                .override().predicate(new ResourceLocation(ModMain.MODID, "binding"), 2F).model(amethystAmulet2).end()
                .override().predicate(new ResourceLocation(ModMain.MODID, "binding"), 3F).model(amethystAmulet3).end();

        builder2(itemGenerated, "magic_chestplate", "magic_chestplate", "magic_chestplate_overlay");
        builder2(itemGenerated, "magic_helmet", "magic_helmet", "magic_helmet_overlay");
        builder(itemHandHeld,"floyd_dagger", "floyd_dagger");
        builder(itemHandHeld,"algid_axe", "algid_axe");
        builder(itemGenerated,"deep_nether_petrified_tome", "deep_nether_petrified_tome");
        builder(itemGenerated, "heart_of_the_glowing_depths", "heart_of_the_glowing_depths");
        builder(itemHandHeld, "soul_fireball_scepter", "soul_fireball_scepter");
        builder2(itemSpawnEgg, "essence", "essence_1", "essence_2");
        builder(itemGenerated, "easter_essence", "easter_essence");
        builder(itemGenerated, "totem_of_the_dead", "totem_of_the_dead");
        builder(itemGenerated, "wheel_of_the_unknown_god", "wheel_of_the_unknown_god");
        builder(itemGenerated, "soul_fire_charge", "soul_fire_charge");
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
