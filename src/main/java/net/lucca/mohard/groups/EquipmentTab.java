package net.lucca.mohard.groups;

import net.lucca.mohard.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EquipmentTab extends CreativeModeTab {

    public EquipmentTab() {
        super("equipment");
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ModItems.VILIO_HOE.get());
    }
}
