package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.effects.IncinerationEffect;
import net.lucca.mohard.effects.vanish.VanishEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ModMain.MODID);

    public static void register(IEventBus bus){
        EFFECTS.register(bus);
    }

    public static final RegistryObject<MobEffect> INCINERATION = EFFECTS.register("incineration", IncinerationEffect::new);
    public static final RegistryObject<MobEffect> VANISH = EFFECTS.register("vanish", VanishEffect::new);

}
