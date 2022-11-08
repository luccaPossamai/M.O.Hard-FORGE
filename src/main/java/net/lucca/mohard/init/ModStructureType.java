package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.world.gen.structure.corrupter.CorrupterWagon;
import net.lucca.mohard.world.gen.structure.iceisolator.IceIsolatorHouse;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructureType {

    public static final DeferredRegister<StructureType<?>> DEFERRED_REGISTRY_STRUCTURE =
            DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, ModMain.MODID);

    public static final RegistryObject<StructureType<IceIsolatorHouse>> ICE_ISOLATOR_HOUSE_STRUCTURE = DEFERRED_REGISTRY_STRUCTURE.register("ice_isolator_house", () -> () -> IceIsolatorHouse.CODEC);
    public static final RegistryObject<StructureType<CorrupterWagon>> CORRUPTER_WAGON_STRUCTURE = DEFERRED_REGISTRY_STRUCTURE.register("corrupter_wagon", () -> () -> CorrupterWagon.CODEC);

    public static void register(IEventBus bus) {
        DEFERRED_REGISTRY_STRUCTURE.register(bus);
    }
}
