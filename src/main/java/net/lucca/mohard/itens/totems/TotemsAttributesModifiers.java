package net.lucca.mohard.itens.totems;

import net.lucca.mohard.init.ModAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Map;
import java.util.UUID;

public class TotemsAttributesModifiers {

    private static final UUID UUID_ = UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B");

    public static void register(){}

    public static Map<Attribute, AttributeModifier> goldenTotem(){
        return Map.of(
                ModAttributes.PROJECTILE_DAMAGE, attributeModifierFor(ModAttributes.PROJECTILE_DAMAGE, 0.60, AttributeModifier.Operation.MULTIPLY_BASE),
                ModAttributes.AGILITY, attributeModifierFor(ModAttributes.AGILITY, 0.5, AttributeModifier.Operation.MULTIPLY_BASE)
        );
    }

    public static Map<Attribute, AttributeModifier> cobblestoneTotem(){
        return Map.of(
                ModAttributes.MAGIC_DAMAGE, attributeModifierFor(ModAttributes.MAGIC_DAMAGE, 0.30, AttributeModifier.Operation.MULTIPLY_BASE),
                ModAttributes.INTELLECT, attributeModifierFor(ModAttributes.INTELLECT, 25, AttributeModifier.Operation.ADDITION)
        );
    }

    private static AttributeModifier attributeModifierFor(Attribute attribute, double amount, AttributeModifier.Operation operation){
        return new AttributeModifier(UUID_, attribute.getDescriptionId(), amount, operation);
    }

}
