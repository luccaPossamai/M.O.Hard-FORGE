package net.lucca.mohard.util.mechanics.damage.nomad;

import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadEntity;
import net.lucca.mohard.util.mechanics.evolution.LevelMechanic;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class NomadDamageEvents {


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void damageTaken(LivingHurtEvent event){
        if(event.getEntity() instanceof SpiritualNomadEntity){
            SpiritualNomadEntity nomadEntity = (SpiritualNomadEntity) event.getEntity();
            double level = LevelMechanic.getLevel(nomadEntity.level);
            event.setAmount(event.getAmount() / (600 - (float) level));
        }

    }


}
