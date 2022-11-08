package net.lucca.mohard.effects;

import net.lucca.mohard.init.ModEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Random;

@Mod.EventBusSubscriber
public class VanishEffect extends MobEffect {

    public VanishEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity p_19462_, @Nullable Entity p_19463_, LivingEntity p_19464_, int p_19465_, double p_19466_) {
        super.applyInstantenousEffect(p_19462_, p_19463_, p_19464_, p_19465_, p_19466_);
        Random rand = new Random();
        p_19464_.level.addParticle(ParticleTypes.SMOKE, p_19464_.getX(), p_19464_.getY() + 1, p_19464_.getZ(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble());

    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity p_19467_, int p_19468_) {
        MobEffectInstance instance = p_19467_.getEffect(ModEffects.VANISH.get());
        if(instance != null) {
            int dur = instance.getDuration();
            if(p_19467_.getLastHurtMobTimestamp() >= dur){
                p_19467_.removeEffect(ModEffects.VANISH.get());
                Random rand = new Random();
                p_19467_.level.addParticle(ParticleTypes.SMOKE, p_19467_.getX(), p_19467_.getY() + 1, p_19467_.getZ(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
            }

        }

    }

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

}
