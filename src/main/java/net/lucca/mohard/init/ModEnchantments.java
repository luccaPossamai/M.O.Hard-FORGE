package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.enchantments.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ModMain.MODID);

    public static void register(IEventBus bus){
        ENCHANTMENTS.register(bus);
    }

    public static final RegistryObject<Enchantment> PISHOGUE = ENCHANTMENTS.register("pishogue", ()->
            new Pishogue(EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> DOUBLE_BANE = ENCHANTMENTS.register("double_bane", ()->
            new DoubleBane(EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> HALF_BANE = ENCHANTMENTS.register("half_bane", ()->
            new HalfBane(EquipmentSlot.MAINHAND));




}
