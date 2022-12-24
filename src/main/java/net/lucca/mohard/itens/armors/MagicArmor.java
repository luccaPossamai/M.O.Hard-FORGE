package net.lucca.mohard.itens.armors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.init.ModModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Consumer;

public class MagicArmor extends DyeableArmorItem implements IForgeItem {


    private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    private final Multimap<Attribute, AttributeModifier> modifiers;

    public MagicArmor(EquipmentSlot equip, Properties properties) {
        super(ModArmorMaterials.MAGIC, equip, properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[equip.getIndex()];
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double)this.material.getDefenseForSlot(slot), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double)this.material.getToughness(), AttributeModifier.Operation.ADDITION));
        builder.put(ModAttributes.MAGIC_RESISTANCE, new AttributeModifier(uuid, "Strong Magic Resistance", 2.5F, AttributeModifier.Operation.ADDITION));
        builder.put(ModAttributes.INTELLECT, new AttributeModifier(uuid, "Intellect modifier", 15F, AttributeModifier.Operation.ADDITION));

        if (this.knockbackResistance > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double)this.knockbackResistance, AttributeModifier.Operation.ADDITION));
        }

        this.modifiers = builder.build();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                HumanoidModel<?> model = slot.equals(EquipmentSlot.LEGS) ? new HumanoidModel<>(ModModelLayers.PLAYER_FIT_INNER_ARMOR.bakeRoot())
                        : new HumanoidModel<>(ModModelLayers.PLAYER_FIT_OUTER_ARMOR.bakeRoot());

                model.crouching = original.crouching;
                model.riding = original.riding;
                model.young = original.young;

                return model;
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String typeS = type == null ? "" : "_overlay";
        if (slot.getIndex() == 1) {
            return new ResourceLocation(ModMain.MODID, "textures/models/armor/magic_layer_2"+typeS+".png").toString();
        }
        return new ResourceLocation(ModMain.MODID, "textures/models/armor/magic_layer_1"+typeS+".png").toString();
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot p_40390_) {
        return p_40390_ == this.slot ? this.modifiers : super.getDefaultAttributeModifiers(p_40390_);
    }

}
