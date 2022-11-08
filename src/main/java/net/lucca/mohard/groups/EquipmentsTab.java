package net.lucca.mohard.groups;

import net.lucca.mohard.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EquipmentsTab extends CreativeModeTab {

    public EquipmentsTab() {
        super("tools");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.VILIO_AXE.get());
    }
}
