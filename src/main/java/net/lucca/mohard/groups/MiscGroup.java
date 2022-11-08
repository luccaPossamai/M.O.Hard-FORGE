package net.lucca.mohard.groups;

import net.lucca.mohard.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MiscGroup extends CreativeModeTab {

    public MiscGroup() {
        super("misc");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.ESSENCE_BUNDLE.get());
    }
}
