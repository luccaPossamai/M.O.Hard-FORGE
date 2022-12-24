package net.lucca.mohard.data.client;

import com.google.common.collect.ImmutableList;
import net.lucca.mohard.data.client.lootTables.ModBlockLoot;
import net.lucca.mohard.data.client.lootTables.ModEntityLoot;
import net.lucca.mohard.init.ModCustomItems;
import net.lucca.mohard.init.ModEssences;
import net.lucca.mohard.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;

public class ModLootTableProvider extends LootTableProvider {


    static List<SubProviderEntry> list = ImmutableList.of(
            new LootTableProvider.SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK),
            new LootTableProvider.SubProviderEntry(ModEntityLoot::new, LootContextParamSets.ENTITY)
    );
    public ModLootTableProvider(PackOutput p_254123_) {
        super(p_254123_, BuiltInLootTables.all(), list);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker){
        map.forEach((p_218436_2_, p_218436_3_) -> LootTables.validate(validationTracker, p_218436_2_, p_218436_3_));
    }


    @Mod.EventBusSubscriber
    public static class lootTablesLoad {
        @SubscribeEvent
        public static void onLootTablesLoading(LootTableLoadEvent event){
            LootTable lootTable = event.getTable();
            if(lootTable.getLootTableId().toString().equals("minecraft:chests/bastion_treasure")){
                lootTable.addPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 1)).add(LootItem.lootTableItem(ModItems.HEART_OF_THE_GLOWING_DEPTHS.get())).build());
            }
            if(lootTable.getLootTableId().toString().equals("minecraft:chests/ancient_city")){
                lootTable.addPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 1)).add(LootItem.lootTableItem(ModItems.FLOYD_DAGGER.get()).setWeight(20)).add(LootItem.lootTableItem(Items.CLOCK).setWeight(80)).build());
            }
            if(lootTable.getLootTableId().toString().equals("minecraft:chests/pillager_outpost")){
                lootTable.addPool(ModCustomItems.magicCrossbowPool(UniformGenerator.between(0, 1F)));
            }
            if(lootTable.getLootTableId().toString().equals("minecraft:chests/igloo_chest")){
                lootTable.addPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 1)).add(LootItem.lootTableItem(ModEssences.ICE_ISOLATOR_ESSENCE.get()).setWeight(99)).add(LootItem.lootTableItem(ModEssences.BRUNO_ESSENCE.get()).setWeight(1)).build());
            }
            if(lootTable.getLootTableId().toString().equals("minecraft:chests/jungle_temple")){
                lootTable.addPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 1)).add(LootItem.lootTableItem(ModItems.WHEEL_OF_THE_UNKNOWN_GOD.get())).build());
            }
            if(lootTable.getLootTableId().toString().equals("minecraft:chests/desert_pyramid")){
                lootTable.addPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 1)).add(LootItem.lootTableItem(ModItems.TOTEM_OF_THE_DEAD.get()).setWeight(25)).add(LootItem.lootTableItem(Items.CLOCK).setWeight(75)).build());
            }
            if(lootTable.getLootTableId().toString().equals("minecraft:chests/end_city_treasure")){
                lootTable.addPool(ModCustomItems.shieldPool(UniformGenerator.between(0, 1)));
            }
            if(lootTable.getLootTableId().toString().equals("minecraft:chests/abandoned_mineshaft")){
                lootTable.addPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 1)).add(LootItem.lootTableItem(ModItems.AMETHYST_AMULET.get()).setWeight(1)).add(LootItem.lootTableItem(Items.AMETHYST_SHARD).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))).setWeight(3)).build());
            }

        }
    }

}
