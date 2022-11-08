package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructureSets {

    public static final DeferredRegister<StructureSet> DEFERRED_REGISTRY_STRUCTURE_SET =
            DeferredRegister.create(Registry.STRUCTURE_SET_REGISTRY, ModMain.MODID);


    public static final RegistryObject<StructureSet> ICE_ISOLATOR_HOUSE_STRUCTURE_SET = register("ice_isolator_house", ModStructures.ICE_ISOLATOR_HOUSE_STRUCTURE.getHolder().get(),  new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.LINEAR, 1287848122));

    public static final RegistryObject<StructureSet> CORRUPTER_WAGON_STRUCTURE_SET = register("corrupter_wagon", ModStructures.CORRUPTER_WAGON_STRUCTURE.getHolder().get(), new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.LINEAR, 1923658123));
    public static void register(IEventBus bus) {
        DEFERRED_REGISTRY_STRUCTURE_SET.register(bus);

    }


    static RegistryObject<StructureSet> register(String name, StructureSet p_211130_) {
        return DEFERRED_REGISTRY_STRUCTURE_SET.register(name, () -> p_211130_);
    }

    static RegistryObject<StructureSet> register(String name, Holder<Structure> structure, StructurePlacement structurePlacement) {
        return register(name, new StructureSet(structure, structurePlacement));
    }
}
