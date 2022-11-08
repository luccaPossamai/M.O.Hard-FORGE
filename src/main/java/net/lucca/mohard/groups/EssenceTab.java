package net.lucca.mohard.groups;

import net.lucca.mohard.init.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EssenceTab extends CreativeModeTab {

    public EssenceTab() {
        super("essences");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModBlocks.VILIO_ALTAR.get().asItem());
    }
}
