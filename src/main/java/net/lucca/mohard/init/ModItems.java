package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.itens.armors.MagicArmor;
import net.lucca.mohard.itens.artifacts.AmethystAmulet;
import net.lucca.mohard.itens.artifacts.DeepNetherPetrifiedTome;
import net.lucca.mohard.itens.artifacts.FloydDagger;
import net.lucca.mohard.itens.artifacts.SoulFireballScepter;
import net.lucca.mohard.itens.artifacts.algidAxe.AlgidAxe;
import net.lucca.mohard.itens.essenceBundle.EssenceBundleItem;
import net.lucca.mohard.itens.totems.TotemArtifact;
import net.lucca.mohard.itens.totems.TotemsAttributesModifiers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);


    public static final RegistryObject<Item> HEART_OF_THE_GLOWING_DEPTHS = ITEMS.register("heart_of_the_glowing_depths", () ->
            new Item(new Item.Properties()
                    .stacksTo(64)
                    .rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DEEP_NETHER_PETRIFIED_TOME = ITEMS.register("deep_nether_petrified_tome", () ->
            new DeepNetherPetrifiedTome(new Item.Properties()
                    .stacksTo(1)
                    .durability(64)
                    .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> FLOYD_DAGGER = ITEMS.register("floyd_dagger", () ->
            new FloydDagger(new Item.Properties()
                    .stacksTo(1)
                    .durability(575)
                    .rarity(Rarity.COMMON)));



    public static final RegistryObject<Item> ALGID_AXE = ITEMS.register("algid_axe", () ->
            new AlgidAxe(new Item.Properties()
                    .stacksTo(1)
                    .durability(1028)
                    .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> SOUL_FIREBALL_SCEPTER = ITEMS.register("soul_fireball_scepter", () ->
            new SoulFireballScepter(new Item.Properties()
                    .stacksTo(1)
                    .durability(32)
                    .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> AMETHYST_AMULET = ITEMS.register("amethyst_amulet", () ->
            new AmethystAmulet(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> MAGIC_CHESTPLATE = ITEMS.register("magic_chestplate", () ->
            new MagicArmor(EquipmentSlot.CHEST,
                    new Item.Properties()
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MAGIC_HELMET = ITEMS.register("magic_helmet", () ->
            new MagicArmor(EquipmentSlot.HEAD,
                    new Item.Properties()
                            .stacksTo(1)
                            .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ESSENCE_BUNDLE = ITEMS.register("essence_bundle", () ->
            new EssenceBundleItem((new Item.Properties().stacksTo(1)).rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> SOUL_FIRE_CHARGE = ITEMS.register("soul_fire_charge", () ->
            new Item(new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> TOTEM_OF_THE_DEAD = ITEMS.register("totem_of_the_dead", () ->
            new TotemArtifact(new Item.Properties().rarity(Rarity.RARE), TotemsAttributesModifiers.goldenTotem()));

    public static final RegistryObject<Item> WHEEL_OF_THE_UNKNOWN_GOD = ITEMS.register("wheel_of_the_unknown_god", () ->
            new TotemArtifact(new Item.Properties().rarity(Rarity.RARE), TotemsAttributesModifiers.cobblestoneTotem()));

}
