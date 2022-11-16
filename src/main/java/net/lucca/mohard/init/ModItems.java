package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.itens.VilioCharge;
import net.lucca.mohard.itens.armors.MagicArmor;
import net.lucca.mohard.itens.armors.TenderObsidianArmor;
import net.lucca.mohard.itens.armors.VilioArmor;
import net.lucca.mohard.itens.artifacts.Dash;
import net.lucca.mohard.itens.artifacts.DeepNetherPetrifiedTome;
import net.lucca.mohard.itens.artifacts.FloydDagger;
import net.lucca.mohard.itens.artifacts.algidAxe.AlgidAxe;
import net.lucca.mohard.itens.artifacts.wand.Wand;
import net.lucca.mohard.itens.essenceBundle.EssenceBundleItem;
import net.lucca.mohard.itens.tools.VilioTier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);

    public static final RegistryObject<Item> GENERIC_ESSENCE = ITEMS.register("empty_essence", () ->
            new Item(new Item.Properties().tab(ModItemGroups.ESSENCE_TAB)));

    public static final RegistryObject<Item> VILIO_AXE = ITEMS.register("vilio_axe", () ->
            new AxeItem(new VilioTier(),
                    5.0F,
                    -3F,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
            .stacksTo(1)
            .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> VILIO_PICKAXE = ITEMS.register("vilio_pickaxe", () ->
            new PickaxeItem(new VilioTier(),
                    1,
                    -2.8F,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> VILIO_HOE = ITEMS.register("vilio_hoe", () ->
            new HoeItem(new VilioTier(),
                    -3,
                    0,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.COMMON)));


    public static final RegistryObject<Item> VILIO_SHOVEL = ITEMS.register("vilio_shovel", () ->
            new ShovelItem(new VilioTier(),
                    1.5F,
                    -3F,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> VILIO_SWORD = ITEMS.register("vilio_sword", () ->
            new SwordItem(new VilioTier(),
                    3,
                    -2.4F,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> HEART_OF_THE_GLOWING_DEPTHS = ITEMS.register("heart_of_the_glowing_depths", () ->
            new Item(new Item.Properties().tab(ModItemGroups.MISC)
                    .stacksTo(64)
                    .rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DEEP_NETHER_PETRIFIED_TOME = ITEMS.register("deep_nether_petrified_tome", () ->
            new DeepNetherPetrifiedTome(new Item.Properties().tab(ModItemGroups.ARTIFACTS)
                    .stacksTo(1)
                    .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> FLOYD_DAGGER = ITEMS.register("floyd_dagger", () ->
            new FloydDagger(new Item.Properties().tab(ModItemGroups.ARTIFACTS)
                    .stacksTo(1)
                    .durability(575)
                    .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> VILIO_IDOL = ITEMS.register("vilio_idol", () ->
            new Dash(new Item.Properties().tab(ModItemGroups.ARTIFACTS)
                    .stacksTo(1)
                    .rarity(Rarity.COMMON)));


    public static final RegistryObject<Item> ALGID_AXE = ITEMS.register("algid_axe", () ->
            new AlgidAxe(new Item.Properties().tab(ModItemGroups.ARTIFACTS)
                    .stacksTo(1)
                    .durability(1028)
                    .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> WAND = ITEMS.register("wand", () ->
            new Wand(new Item.Properties().tab(ModItemGroups.ARTIFACTS)
                    .stacksTo(1)
                    .rarity(Rarity.COMMON)));


    public static final RegistryObject<Item> MAGIC_BOOTS = ITEMS.register("magic_boots", () ->
            new MagicArmor(EquipmentSlot.FEET,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MAGIC_LEGGINGS = ITEMS.register("magic_leggings", () ->
            new MagicArmor(EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MAGIC_CHESTPLATE = ITEMS.register("magic_chestplate", () ->
            new MagicArmor(EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MAGIC_HELMET = ITEMS.register("magic_helmet", () ->
            new MagicArmor(EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> TENDER_OBSIDIAN_BOOTS = ITEMS.register("tender_obsidian_boots", () ->
            new TenderObsidianArmor(EquipmentSlot.FEET,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> TENDER_OBSIDIAN_LEGGINGS = ITEMS.register("tender_obsidian_leggings", () ->
            new TenderObsidianArmor(EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> TENDER_OBSIDIAN_CHESTPLATE = ITEMS.register("tender_obsidian_chestplate", () ->
            new TenderObsidianArmor(EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> TENDER_OBSIDIAN_HELMET = ITEMS.register("tender_obsidian_helmet", () ->
            new TenderObsidianArmor(EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> VILIO_BOOTS = ITEMS.register("vilio_boots", () ->
            new VilioArmor(EquipmentSlot.FEET,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> VILIO_LEGGINGS = ITEMS.register("vilio_leggings", () ->
            new VilioArmor(EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> VILIO_CHESTPLATE = ITEMS.register("vilio_chestplate", () ->
            new VilioArmor(EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> VILIO_HELMET = ITEMS.register("vilio_helmet", () ->
            new VilioArmor(EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModItemGroups.EQUIPMENT)
                            .stacksTo(1)
                            .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> ESSENCE_BUNDLE = ITEMS.register("essence_bundle", () ->
            new EssenceBundleItem((new Item.Properties().tab(ModItemGroups.EQUIPMENT).stacksTo(1)).rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> RAW_VILIO = ITEMS.register("raw_vilio", () ->
        new Item(new Item.Properties().rarity(Rarity.COMMON).tab(ModItemGroups.MISC)));

    public static final RegistryObject<Item> MELTED_VILIO = ITEMS.register("melted_vilio", () ->
            new VilioCharge(new Item.Properties().rarity(Rarity.COMMON).tab(ModItemGroups.MISC)));

    public static final RegistryObject<Item> VILIO_INGOT = ITEMS.register("vilio_ingot", () ->
            new Item(new Item.Properties().rarity(Rarity.COMMON).tab(ModItemGroups.MISC)));

    public static final RegistryObject<Item> COLD_CHARGE = ITEMS.register("cold_charge", () ->
            new Item(new Item.Properties().rarity(Rarity.COMMON).tab(ModItemGroups.MISC)));


    public static final RegistryObject<Item> MUSIC_DISC_BREATHE = ITEMS.register("music_disc_breathe", () ->
            new RecordItem(1, () -> ModSounds.MUSIC_DISC_BREATHE,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)
                            .stacksTo(1)
                            .rarity(Rarity.EPIC), 174)
            );

    public static final RegistryObject<Item> DISC_FRAGMENT_BREATHE = ITEMS.register("disc_fragment_breathe", () ->
            new DiscFragmentItem((new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> ESSENCE_BANNER_PATTERN = ITEMS.register("essence_banner_pattern", () ->
            new BannerPatternItem(ModTags.BannerPatterns.PATTERN_ITEM_ESSENCE, (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> NOMADS_BANNER_PATTERN = ITEMS.register("nomads_banner_pattern", () ->
            new BannerPatternItem(ModTags.BannerPatterns.PATTERN_ITEM_NOMADS, (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
}
