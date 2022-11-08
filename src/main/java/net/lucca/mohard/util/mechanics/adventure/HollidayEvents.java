package net.lucca.mohard.util.mechanics.adventure;


import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;

@Mod.EventBusSubscriber
public class HollidayEvents {

    @SubscribeEvent
    public static void easter(LivingSpawnEvent.SpecialSpawn event){
        LivingEntity entity = event.getEntity();
        if(entity instanceof Rabbit rabbit) {
            LocalDate localdate = LocalDate.now();
            int i = localdate.get(ChronoField.DAY_OF_MONTH);
            int j = localdate.get(ChronoField.MONTH_OF_YEAR);
            if ((j == 4 && i == 10 && new Random().nextFloat() < 0.01F)) {
                rabbit.setRabbitType(99);
                sendMessage(entity.level, new Vec3(rabbit.getX(), rabbit.getY(), rabbit.getZ()));
            }
        }
    }

    private static void sendMessage(Level level, Vec3 pos){
        level.players().forEach(player -> player.displayClientMessage(Component.translatable("holliday.message", (int) Math.round(pos.x), (int) Math.round(pos.y), (int) Math.round(pos.z)).withStyle(ChatFormatting.GOLD), false));
    }


}
