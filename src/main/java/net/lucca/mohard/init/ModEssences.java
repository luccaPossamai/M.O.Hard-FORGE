package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.entities.illagers.iceIsolator.IceIsolator;
import net.lucca.mohard.itens.essence.EssenceData;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.lucca.mohard.itens.essence.EssencePredicate;
import net.lucca.mohard.itens.essence.EssencesData;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEssences {

    public static final DeferredRegister<Item> ESSENCE_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);

    public static final RegistryObject<EssenceItem> AXOLOTL_ESSENCE = register("axolotl", EssencesData.AXOLOTL, 16499171, 10890612);
    public static final RegistryObject<EssenceItem> BAT_ESSENCE = register("bat", EssencesData.BAT, 4996656, 986895);
    public static final RegistryObject<EssenceItem> BEE_ESSENCE = register("bee", EssencesData.BEE, 15582019, 4400155);
    public static final RegistryObject<EssenceItem> BLAZE_ESSENCE = register("blaze", EssencesData.BLAZE, 16167425, 16775294);
    public static final RegistryObject<EssenceItem> CAT_ESSENCE = register("cat", EssencesData.CAT, 15714446, 9794134);
    public static final RegistryObject<EssenceItem> CAVE_SPIDER_ESSENCE = register("cave_spider", EssencesData.CAVE_SPIDER, 803406, 11013646);
    public static final RegistryObject<EssenceItem> CHICKEN_ESSENCE = register("chicken", EssencesData.CHICKEN, 10592673, 16711680);
    public static final RegistryObject<EssenceItem> COD_ESSENCE = register("cod", EssencesData.COD, 12691306, 15058059);
    public static final RegistryObject<EssenceItem> COW_ESSENCE = register("cow", EssencesData.COW, 4470310, 10592673);
    public static final RegistryObject<EssenceItem> CREEPER_ESSENCE = register("creeper", EssencesData.CREEPER, 894731, 0);
    public static final RegistryObject<EssenceItem> DOLPHIN_ESSENCE = register("dolphin", EssencesData.DOLPHIN, 2243405, 16382457);
    public static final RegistryObject<EssenceItem> DONKEY_ESSENCE = register("donkey", EssencesData.DONKEY, 5457209, 8811878);
    public static final RegistryObject<EssenceItem> DROWNED_ESSENCE = register("drowned", EssencesData.DROWNED, 9433559, 7969893);
    public static final RegistryObject<EssenceItem> ELDER_GUARDIAN_ESSENCE = register("elder_guardian", EssencesData.ELDER_GUARDIAN, 13552826, 7632531);
    public static final RegistryObject<EssenceItem> ENDERMAN_ESSENCE = register("enderman", EssencesData.ENDERMAN, 1447446, 0);
    public static final RegistryObject<EssenceItem> ENDERMITE_ESSENCE = register("endermite", EssencesData.ENDERMITE, 1447446, 7237230);
    public static final RegistryObject<EssenceItem> EVOKER_ESSENCE = register("evoker", EssencesData.EVOKER, 9804699, 1973274);
    public static final RegistryObject<EssenceItem> FOX_ESSENCE = register("fox", EssencesData.FOX, 14005919, 13396256);
    public static final RegistryObject<EssenceItem> FROG_ESSENCE = register("frog", EssencesData.FROG, 13661252, 16762748);
    public static final RegistryObject<EssenceItem> GHAST_ESSENCE = register("ghast", EssencesData.GHAST, 16382457, 12369084);
    public static final RegistryObject<EssenceItem> GOAT_ESSENCE = register("goat", EssencesData.GOAT, 10851452, 5589310);
    public static final RegistryObject<EssenceItem> GUARDIAN_ESSENCE = register("guardian", EssencesData.GUARDIAN, 5931634, 15826224);
    public static final RegistryObject<EssenceItem> HOGLIN_ESSENCE = register("hoglin", EssencesData.HOGLIN, 13004373, 6251620);
    public static final RegistryObject<EssenceItem> HORSE_ESSENCE = register("horse", EssencesData.HORSE, 12623485, 15656192);
    public static final RegistryObject<EssenceItem> HUSK_ESSENCE = register("husk", EssencesData.HUSK, 7958625, 15125652);
    public static final RegistryObject<EssenceItem> LLAMA_ESSENCE = register("llama", EssencesData.LLAMA, 12623485, 10051392);
    public static final RegistryObject<EssenceItem> MAGMA_CUBE_ESSENCE = register("magma_cube", EssencesData.MAGMA_CUBE, 3407872, 16579584);
    public static final RegistryObject<EssenceItem> MOOSHROOM_ESSENCE = register("mooshroom", EssencesData.MOOSHROOM, 10489616, 12040119);
    public static final RegistryObject<EssenceItem> MULE_ESSENCE = register("mule", EssencesData.MULE, 1769984, 5321501);
    public static final RegistryObject<EssenceItem> OCELOT_ESSENCE = register("ocelot", EssencesData.OCELOT, 15720061, 5653556);
    public static final RegistryObject<EssenceItem> PANDA_ESSENCE = register("panda", EssencesData.PANDA, 15198183, 1776418);
    public static final RegistryObject<EssenceItem> PARROT_ESSENCE = register("parrot", EssencesData.PARROT, 894731, 16711680);
    public static final RegistryObject<EssenceItem> PHANTOM_ESSENCE = register("phantom", EssencesData.PHANTOM, 4411786, 8978176);
    public static final RegistryObject<EssenceItem> PIG_ESSENCE = register("pig", EssencesData.PIG, 15771042, 14377823);
    public static final RegistryObject<EssenceItem> PIGLIN_ESSENCE = register("piglin", EssencesData.PIGLIN, 10051392, 16380836);
    public static final RegistryObject<EssenceItem> PIGLIN_BRUTE_ESSENCE = register("piglin_brute", EssencesData.PIGLIN_BRUTE, 5843472, 16380836);
    public static final RegistryObject<EssenceItem> PILLAGER_ESSENCE = register("pillager", EssencesData.PILLAGER, 5451574, 9804699);
    public static final RegistryObject<EssenceItem> POLAR_BEAR_ESSENCE = register("polar_bear", EssencesData.POLAR_BEAR, 15921906, 9803152);
    public static final RegistryObject<EssenceItem> PUFFERFISH_ESSENCE = register("pufferfish", EssencesData.PUFFERFISH, 16167425, 3654642);
    public static final RegistryObject<EssenceItem> RABBIT_ESSENCE = register("rabbit", EssencesData.RABBIT, 10051392, 7555121);
    public static final RegistryObject<EssenceItem> RAVAGER_ESSENCE = register("ravager", EssencesData.RAVAGER, 7697520, 5984329);
    public static final RegistryObject<EssenceItem> SALMON_ESSENCE = register("salmon", EssencesData.SALMON, 10489616, 951412);
    public static final RegistryObject<EssenceItem> SHEEP_ESSENCE = register("sheep", EssencesData.SHEEP, 15198183, 16758197);
    public static final RegistryObject<EssenceItem> SHULKER_ESSENCE = register("shulker", EssencesData.SHULKER, 9725844, 5060690);
    public static final RegistryObject<EssenceItem> SILVERFISH_ESSENCE = register("silverfish", EssencesData.SILVERFISH, 7237230, 3158064);
    public static final RegistryObject<EssenceItem> SKELETON_ESSENCE = register("skeleton", EssencesData.SKELETON, 12698049, 4802889);
    public static final RegistryObject<EssenceItem> SKELETON_HORSE_ESSENCE = register("skeleton_horse", EssencesData.SKELETON_HORSE, 6842447, 15066584);
    public static final RegistryObject<EssenceItem> SLIME_ESSENCE = register("slime", EssencesData.SLIME, 5349438, 8306542);
    public static final RegistryObject<EssenceItem> SPIDER_ESSENCE = register("spider", EssencesData.SPIDER, 3419431, 11013646);
    public static final RegistryObject<EssenceItem> SQUID_ESSENCE = register("squid", EssencesData.SQUID, 2243405, 7375001);
    public static final RegistryObject<EssenceItem> GLOW_SQUID_ESSENCE = register("glow_squid", EssencesData.GLOW_SQUID, 611926, 8778172);
    public static final RegistryObject<EssenceItem> STRAY_ESSENCE = register("stray", EssencesData.STRAY, 6387319, 14543594);
    public static final RegistryObject<EssenceItem> STRIDER_ESSENCE = register("strider", EssencesData.STRIDER, 10236982, 5065037);
    public static final RegistryObject<EssenceItem> TRADER_LLAMA_ESSENCE = register("trader_llama", EssencesData.TRADER_LLAMA, 15377456, 4547222);
    public static final RegistryObject<EssenceItem> TROPICAL_FISH_ESSENCE = register("tropical_fish", EssencesData.TROPICAL_FISH, 15690005, 16775663);
    public static final RegistryObject<EssenceItem> TURTLE_ESSENCE = register("turtle", EssencesData.TURTLE, 15198183, 44975);
    public static final RegistryObject<EssenceItem> VEX_ESSENCE = register("vex", EssencesData.VEX, 8032420, 15265265);
    public static final RegistryObject<EssenceItem> VILLAGER_ESSENCE = register("villager", EssencesData.VILLAGER, 5651507, 12422002);
    public static final RegistryObject<EssenceItem> VINDICATOR_ESSENCE = register("vindicator", EssencesData.VINDICATOR, 9804699, 2580065);
    public static final RegistryObject<EssenceItem> WANDERING_TRADER_ESSENCE = register("wandering_trader", EssencesData.WANDERING_TRADER, 4547222, 15377456);
    public static final RegistryObject<EssenceItem> WITCH_ESSENCE = register("witch", EssencesData.WITCH, 3407872, 5349438);
    public static final RegistryObject<EssenceItem> WITHER_SKELETON_ESSENCE = register("wither_skeleton", EssencesData.WITHER_SKELETON, 1315860, 4672845);
    public static final RegistryObject<EssenceItem> WOLF_ESSENCE = register("wolf", EssencesData.WOLF, 14144467, 13545366);
    public static final RegistryObject<EssenceItem> ZOGLIN_ESSENCE = register("zoglin", EssencesData.ZOGLIN, 13004373, 15132390);
    public static final RegistryObject<EssenceItem> ZOMBIE_ESSENCE = register("zombie", EssencesData.ZOMBIE, 44975, 7969893);
    public static final RegistryObject<EssenceItem> ZOMBIE_HORSE_ESSENCE = register("zombie_horse", EssencesData.ZOMBIE_HORSE, 3232308, 9945732);
    public static final RegistryObject<EssenceItem> ZOMBIE_VILLAGER_ESSENCE = register("zombie_villager", EssencesData.ZOMBIE_VILLAGER, 5651507, 7969893);
    public static final RegistryObject<EssenceItem> ZOMBIFIED_PIGLIN_ESSENCE = register("zombified_piglin", EssencesData.ZOMBIFIED_PIGLIN, 15373203, 5009705);
    //TODO COLORS OF THE NEW SPAWNEGGS
    public static final RegistryObject<EssenceItem> IRON_GOLEM_ESSENCE = register("iron_golem", EssencesData.IRON_GOLEM, 15198183, 894731 );
    public static final RegistryObject<EssenceItem> ILLUSIONER_ESSENCE = register("illusioner", EssencesData.ILLUSIONER, 2243405, 16579584);
    //TODO COLORS OF THE NEW SPAWNEGGS
    public static final RegistryObject<EssenceItem> SNOW_GOLEM_ESSENCE = register("snow_golem", EssencesData.SNOW_GOLEM, 15198183, 15690005);
    public static final RegistryObject<EssenceItem> ICE_ISOLATOR_ESSENCE = register("ice_isolator", EssencesData.ICE_ISOLATOR, 9156025, 9804699);
    public static final RegistryObject<EssenceItem> BRUNO_ESSENCE = register("bruno", EssencesData.BRUNO, 9156025, 10489616, entity -> (entity instanceof IceIsolator iceIsolator && ChatFormatting.stripFormatting(iceIsolator.getName().getString()).equals("Bruno")));
    public static final RegistryObject<EssenceItem> KILLER_BUNNY_ESSENCE = register("easter", EssencesData.KILLER_BUNNY, 16777215, 7735831, entity -> (entity instanceof Rabbit rabbit && rabbit.getRabbitType() == 99), true);
    public static final RegistryObject<EssenceItem> BLUE_AXOLOTL_ESSENCE = register("blue_axolotl", EssencesData.BLUE_AXOLOTL, 6903523, 14058539, entity -> (entity instanceof Axolotl axolotl && axolotl.getVariant().equals(Axolotl.Variant.BLUE)));
    public static final RegistryObject<EssenceItem> CHARGED_CREEPER_ESSENCE = register("charged_creeper", EssencesData.CHARGED_CREEPER, 894731, 9156025, entity -> (entity instanceof Creeper creeper && creeper.isPowered()));
    //TODO COLORS OF THE NEW SPAWNEGGS
    public static final RegistryObject<EssenceItem> ENDER_DRAGON_ESSENCE = register("ender_dragon", EssencesData.ENDER_DRAGON, 894731, 9156025);
    //TODO COLORS OF THE NEW SPAWNEGGS
    public static final RegistryObject<EssenceItem> WITHER_ESSENCE = register("wither", EssencesData.WITHER, 894731, 9156025);
    public static final RegistryObject<EssenceItem> WARDEN_ESSENCE = register("warden", EssencesData.WARDEN, 1001033, 3790560);



    private static RegistryObject<EssenceItem> register(String string, EssenceData essenceData, int color1, int color2){
        return register(string, essenceData, color1, color2,(livingEntity) -> true);
    }

    private static RegistryObject<EssenceItem> register(String string, EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> sup) {
        return register(string, essenceData, color1, color2, sup, false);
    }

    private static RegistryObject<EssenceItem> register(String string, EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> sup, boolean withOwnTexture){
        return register(getEssenceProperties(), string, essenceData, color1, color2, sup, withOwnTexture);
    }

    private static RegistryObject<EssenceItem> register(Item.Properties properties, String string, EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> sup, boolean withOwnTexture){
        return ESSENCE_ITEMS.register(string+"_essence", () -> new EssenceItem(properties, essenceData, color1, color2, sup).withOwnTexture(withOwnTexture));
    }


    public static EssenceItem.Properties getEssenceProperties(){
        Item.Properties prop = new EssenceItem.Properties().stacksTo(64);
        return prop.tab(ModItemGroups.ESSENCE_TAB);
    }
}
