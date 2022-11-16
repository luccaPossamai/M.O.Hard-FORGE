package net.lucca.mohard.data.client;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.lucca.mohard.init.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(DataGenerator p_i50789_1_) {
        super(p_i50789_1_);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables(){

        return ImmutableList.of(
                Pair.of(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                Pair.of(ModEntityLoot::new, LootContextParamSets.ENTITY)

        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker){
        map.forEach((p_218436_2_, p_218436_3_) -> LootTables.validate(validationTracker, p_218436_2_, p_218436_3_));
    }


    public static class ModBlockLootTables extends BlockLoot {

        @Override
        protected void addTables() {

            dropSelf(ModBlocks.VILIO_ALTAR.get());
            dropSelf(ModBlocks.ESSENCE_EXHANGER_BLOCK.get());
            dropSelf(ModBlocks.VILIO_BLOCK.get());
            dropSelf(ModBlocks.TENDER_OBSIDIAN.get());

            this.add(ModBlocks.VILIO_ORE.get(), createVilioOreDrops(ModBlocks.VILIO_ORE.get()));
            this.add(ModBlocks.DEEPSLATE_VILIO_ORE.get(), createVilioOreDrops(ModBlocks.DEEPSLATE_VILIO_ORE.get()));
        }

        @Override
        protected Iterable<Block> getKnownBlocks(){
            return ModBlocks.BLOCOS.getEntries().stream()
                    .map(RegistryObject::get)
                    .collect(Collectors.toList());
        }

        protected static LootTable.Builder createVilioOreDrops(Block p_176047_) {
            return createSilkTouchDispatchTable(p_176047_, applyExplosionDecay(p_176047_, LootItem.lootTableItem(ModItems.RAW_VILIO.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
        }
    }

    public static class ModEntityLoot extends EntityLoot {
        @Override
        public void accept(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> resourceLocationBuilderBiConsumer) {
            this.add(ModEntityTypes.ICE_ISOLATOR.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.EMERALD).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 5.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))).when(LootItemKilledByPlayerCondition.killedByPlayer())));
        }

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
                lootTable.addPool(ModCustomItems.getMagicCrossbow(UniformGenerator.between(0, 1F)));
            }
            if(lootTable.getLootTableId().toString().equals("minecraft:chests/igloo_chest")){
                lootTable.addPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 1)).add(LootItem.lootTableItem(ModEssences.ICE_ISOLATOR_ESSENCE.get()).setWeight(99)).add(LootItem.lootTableItem(ModEssences.BRUNO_ESSENCE.get()).setWeight(1)).build());

            }



        }
    }

}
