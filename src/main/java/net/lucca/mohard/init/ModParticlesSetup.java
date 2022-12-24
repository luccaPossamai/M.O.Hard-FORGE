package net.lucca.mohard.init;

import net.lucca.mohard.particles.AmethystParticle;
import net.lucca.mohard.particles.ModGlowParticle;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticlesSetup {

    @SubscribeEvent
    public static void registerParticle(RegisterParticleProvidersEvent event){

        event.register(ModParticles.ENDER_ALTAR_PARTICLE.get(), ModGlowParticle.EnderAltarParticleProvider::new);
        event.register(ModParticles.FREEZE_PARTICLE.get(), ModGlowParticle.FreezeParticleProvider::new);
        event.register(ModParticles.AMETHYST_PARTICLE.get(), AmethystParticle.Provider::new);
    }

}
