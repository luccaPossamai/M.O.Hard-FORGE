package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.block.altar.GUI.AltarMenu;
import net.lucca.mohard.block.essenceExchanger.EssenceExchangerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModMain.MODID);

    public static void register(IEventBus bus){
        CONTAINERS.register(bus);
    }

    public static final RegistryObject<MenuType<AltarMenu>> ALTAR =
            CONTAINERS.register("altar", () ->
                    new MenuType<>(AltarMenu::new));

    public static final RegistryObject<MenuType<EssenceExchangerMenu>> ESSENCE_EXCHANGER =
            CONTAINERS.register("essence_exchanger", () ->
                    new MenuType<>(EssenceExchangerMenu::new));

}
