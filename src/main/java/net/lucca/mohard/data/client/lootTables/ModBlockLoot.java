package net.lucca.mohard.data.client.lootTables;

import net.lucca.mohard.init.ModBlocks;
import net.lucca.mohard.init.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModBlockLoot extends BlockLootSubProvider {

    static Set<Item> set = Stream.of(ModItems.AMETHYST_AMULET.get()).collect(Collectors.toSet());

    public ModBlockLoot() {
        super(set, FeatureFlags.DEFAULT_FLAGS);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.ENDER_ALTAR.get());
        dropSelf(ModBlocks.ESSENCE_EXHANGER_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return ModBlocks.BLOCOS.getEntries().stream()
                .map(RegistryObject::get)
                .collect(Collectors.toList());

    }
}

