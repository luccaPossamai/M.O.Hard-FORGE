package net.lucca.mohard.util.mechanics.adventure;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class DifferentPillagers {


    @SubscribeEvent
    public static void differentPillagers(LivingSpawnEvent.SpecialSpawn event){
        if(event.getEntity() instanceof Pillager pillager) {
            BlockPos pos = pillager.getOnPos();
            Random random = new Random();
            int i = random.nextInt(100);
            if(i > 90){
                EntityType.ILLUSIONER.spawn((ServerLevel) pillager.level, null, null, null, pos, MobSpawnType.EVENT, true, false);
            }
        }
    }

}
