package net.lucca.mohard.groups;

import net.lucca.mohard.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ArtifactsTab extends CreativeModeTab {

    public ArtifactsTab() {
        super("artifacts");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.DEEP_NETHER_PETRIFIED_TOME.get());
    }
}
