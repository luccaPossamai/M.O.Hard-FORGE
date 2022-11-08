package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModMain.MODID);

    public static void register(IEventBus bus){
        SOUND_EVENTS.register(bus);
    }

    public static final SoundEvent MUSIC_DISC_BREATHE = register("music_disc.mohard_music",
            new ResourceLocation(ModMain.MODID, "music_disc.mohard_music"));

    public static final SoundEvent HABILITY_SOUND = register("hability.hability_sound",
            new ResourceLocation(ModMain.MODID, "hability.hability_sound"));

    private static SoundEvent register(String name, ResourceLocation resource){
        SoundEvent soundEvent = new SoundEvent(resource);
        SOUND_EVENTS.register(name, () ->
                soundEvent
                );
        return soundEvent;
    }


}
