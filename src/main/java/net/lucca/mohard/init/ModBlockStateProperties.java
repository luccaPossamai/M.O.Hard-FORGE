package net.lucca.mohard.init;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockStateProperties {

    public static void register(){}

    public static final IntegerProperty ESSENCE_EXCHANGER_CHARGES = IntegerProperty.create("exchange_charges", 0, 3);

}
