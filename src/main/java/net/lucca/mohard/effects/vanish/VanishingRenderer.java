package net.lucca.mohard.effects.vanish;

import net.lucca.mohard.init.ModEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class VanishingRenderer {

    @SubscribeEvent
    public static void renderEvent(RenderLivingEvent<?, ?> event) {
        if(event.getEntity().hasEffect(ModEffects.VANISH.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void lookingEvent(LivingEvent.LivingVisibilityEvent event) {
        if(event.getEntity().hasEffect(ModEffects.VANISH.get())){
            event.modifyVisibility(0D);
        }
    }

    @SubscribeEvent
    public static void naoAtaca(LivingChangeTargetEvent event){
        LivingEntity newTarget = event.getNewTarget();
        LivingEntity originalTarget = event.getOriginalTarget();
        if(newTarget != null) {
            if (newTarget.hasEffect(ModEffects.VANISH.get())) {
                if(originalTarget != null && !originalTarget.hasEffect(ModEffects.VANISH.get())){
                    event.setNewTarget(originalTarget);
                    return;
                }
                event.setNewTarget(null);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void damageDone(LivingHurtEvent event) {
        if(event.getEntity().hasEffect(ModEffects.VANISH.get())){
            event.getEntity().removeEffect(ModEffects.VANISH.get());
        }
        Entity entity = event.getSource().getEntity();
        LivingEntity livingEntity = null;
        if(entity instanceof LivingEntity){
            livingEntity = (LivingEntity) entity;
        } else if(entity instanceof Projectile projectile && projectile.getOwner() instanceof LivingEntity){
            livingEntity = (LivingEntity) projectile.getOwner();
        }

        if(livingEntity != null){
            livingEntity.removeEffect(ModEffects.VANISH.get());
        }

    }

}
