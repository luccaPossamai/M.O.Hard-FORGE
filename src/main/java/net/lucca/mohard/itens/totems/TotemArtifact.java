package net.lucca.mohard.itens.totems;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TotemArtifact extends Item {

    private final ImmutableMultimap<Attribute, AttributeModifier> modifiers;

    public TotemArtifact(Properties p_41383_, Map<Attribute, AttributeModifier> attributeInstanceMap) {
        super(p_41383_);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        attributeInstanceMap.forEach(builder::put);
        this.modifiers = builder.build();
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot p_41388_) {
        return p_41388_ == EquipmentSlot.OFFHAND ? this.modifiers : super.getDefaultAttributeModifiers(p_41388_);
    }
}
