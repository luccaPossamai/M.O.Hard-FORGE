package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.effects.IncinerationEffect;
import net.lucca.mohard.effects.InspirationEffect;
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
    public static final RegistryObject<MobEffect> INSPIRATION = EFFECTS.register("inspiration", () -> new InspirationEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.05D, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(ModAttributes.MAGIC_DAMAGE, "91AEAA56-376B-4498-935B-2F7F68070635", 8D, AttributeModifier.Operation.ADDITION).addAttributeModifier(ModAttributes.INTELLECT, "91AEAA56-376B-4498-935B-2F7F68070635", 5D, AttributeModifier.Operation.ADDITION));

}
