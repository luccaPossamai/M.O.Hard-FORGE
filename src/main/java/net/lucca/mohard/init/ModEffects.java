package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.effects.IncinerationEffect;
import net.lucca.mohard.effects.StunningEffect;
import net.lucca.mohard.effects.TeleportationEffect;
import net.lucca.mohard.effects.VanishEffect;
import net.lucca.mohard.effects.corruption.CorruptionEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
    public static final RegistryObject<MobEffect> STUN = EFFECTS.register("stun", ()-> new StunningEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -100D, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<MobEffect> TELEPORTATION = EFFECTS.register("teleportation", TeleportationEffect::new);
    public static final RegistryObject<MobEffect> CORRUPTION = EFFECTS.register("corruption", CorruptionEffect::new);
    public static final RegistryObject<MobEffect> VANISH = EFFECTS.register("vanish", VanishEffect::new);

}
