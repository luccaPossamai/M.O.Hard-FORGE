package net.lucca.mohard.entities.villagers;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.lucca.mohard.init.ModItems;
import net.lucca.mohard.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber
public class VillagerTradesSetup {

    @SubscribeEvent
    public static void villagerTrades(VillagerTradesEvent event){
        if(event.getType() == VillagerProfession.CARTOGRAPHER){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.put(5, List.of(
                    new TreasureMapForEmeralds(15, ModTags.Structures.ON_ISOLATOR_EXPLORER_MAPS, "filled_map.isolator", MapDecoration.Type.MANSION, 12, 5),
                    new TreasureMapForEmeralds(15, ModTags.Structures.ON_CORRUPTER_WAGON_EXPLORER_MAPS, "filled_map.corrupter", MapDecoration.Type.BANNER_GREEN, 12, 5)

            ));
        }
    }

    @SubscribeEvent
    public static void wandererTrades(WandererTradesEvent event){
        event.getRareTrades().add(new EmeraldsForMagicArmor(new ItemStack(ModItems.MAGIC_BOOTS.get(), 1), 36));
        event.getRareTrades().add(new EmeraldsForMagicArmor(new ItemStack(ModItems.MAGIC_LEGGINGS.get(), 1), 36));
        event.getRareTrades().add(new EmeraldsForMagicArmor(new ItemStack(ModItems.MAGIC_CHESTPLATE.get(), 1), 36));
        event.getRareTrades().add(new EmeraldsForMagicArmor(new ItemStack(ModItems.MAGIC_HELMET.get(), 1), 36));

    }

    static class EmeraldsForMagicArmor implements VillagerTrades.ItemListing{

        private final ItemStack itemstack;
        private final int emeraldCost;

        EmeraldsForMagicArmor(ItemStack itemstack,int emeraldCost){
            this.itemstack = itemstack;
            this.emeraldCost = emeraldCost;
        }
        @org.jetbrains.annotations.Nullable
        @Override
        public MerchantOffer getOffer(Entity p_219693_, RandomSource p_219694_) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), ItemStack.EMPTY, itemstack, 1, 0, 0.2F);
        }
    }

    static class TreasureMapForEmeralds implements VillagerTrades.ItemListing {
        private final int emeraldCost;
        private final TagKey<Structure> destination;
        private final String displayName;
        private final MapDecoration.Type destinationType;
        private final int maxUses;
        private final int villagerXp;

        public TreasureMapForEmeralds(int p_207767_, TagKey<Structure> p_207768_, String p_207769_, MapDecoration.Type p_207770_, int p_207771_, int p_207772_) {
            this.emeraldCost = p_207767_;
            this.destination = p_207768_;
            this.displayName = p_207769_;
            this.destinationType = p_207770_;
            this.maxUses = p_207771_;
            this.villagerXp = p_207772_;
        }

        @Nullable
        public MerchantOffer getOffer(Entity p_219708_, RandomSource p_219709_) {
            if (!(p_219708_.level instanceof ServerLevel serverlevel)) {
                return null;
            } else {
                BlockPos blockpos = serverlevel.findNearestMapStructure(this.destination, p_219708_.blockPosition(), 100, true);
                if (blockpos != null) {
                    ItemStack itemstack = MapItem.create(serverlevel, blockpos.getX(), blockpos.getZ(), (byte)2, true, true);
                    MapItem.renderBiomePreviewMap(serverlevel, itemstack);
                    MapItemSavedData.addTargetDecoration(itemstack, blockpos, "+", this.destinationType);
                    itemstack.setHoverName(Component.translatable(this.displayName));
                    return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(Items.COMPASS), itemstack, this.maxUses, this.villagerXp, 0.2F);
                } else {
                    return null;
                }
            }
        }
    }

}