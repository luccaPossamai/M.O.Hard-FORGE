package net.lucca.mohard.init;

import net.lucca.mohard.particles.ModGlowParticle;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticlesSetup {

    @SubscribeEvent
    public static void registerParticle(RegisterParticleProvidersEvent event){

        event.register(ModParticles.VILIO_PARTICLE.get(), ModGlowParticle.VilioProvider::new);
        event.register(ModParticles.FREEZE_PARTICLE.get(), ModGlowParticle.FreezeProvider::new);
    }

}
