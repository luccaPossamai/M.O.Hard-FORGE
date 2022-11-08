package net.lucca.mohard.effects.corruption;

import net.lucca.mohard.entities.CorruptedMob;
import net.lucca.mohard.entities.etc.corruptedPlayer.CorruptedPlayer;
import net.lucca.mohard.init.ModEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class CorruptionMechanics {


    private static final EntityType<?>[] CONVERTIBLE_ENTITIES = {
            EntityType.PLAYER
    };


    public static boolean convertible(EntityType<?> type){
        return Arrays.asList(CONVERTIBLE_ENTITIES).contains(type);
    }

    public static boolean converted(LivingEntity entity){
        return entity instanceof CorruptedMob;
    }

    public static EntityType<?> getCorruptedVariant(EntityType<?> type){
        return ModEntityTypes.CORRUPTED_PLAYER.get();
    }

    public static Entity generateCorruptedPlayer(Player player){
        CorruptedPlayer corruptedPlayer = ModEntityTypes.CORRUPTED_PLAYER.get().create(player.level);
        corruptedPlayer.setPlayer(player.getUUID());
        corruptedPlayer.setCustomNameVisible(true);
        corruptedPlayer.setCustomName(player.getName());
        for(EquipmentSlot slot : EquipmentSlot.values()){
            ItemStack itemStack = player.getItemBySlot(slot).copy();
            player.getItemBySlot(slot).shrink(1);
            corruptedPlayer.setItemSlot(slot, itemStack);
        }
        CorruptedPlayer.copyAttributes(player, corruptedPlayer);
        corruptedPlayer.teleportTo(player.getX(), player.getY(), player.getZ());
        return corruptedPlayer;
    }
}
