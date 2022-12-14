package net.lucca.mohard.init;


import net.lucca.mohard.ModMain;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ModMain.MODID);

    public static final RegistryObject<SimpleParticleType> ENDER_ALTAR_PARTICLE = PARTICLES.register("ender_altar", () ->
            new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FREEZE_PARTICLE = PARTICLES.register("freeze", () ->
            new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> AMETHYST_PARTICLE = PARTICLES.register("amethyst", () ->
            new SimpleParticleType(false));

    public static void register(IEventBus bus){
        PARTICLES.register(bus);
    };

}
