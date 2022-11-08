package net.lucca.mohard.itens.armors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;


public class VilioArmor extends ArmorItem implements IForgeItem {

    private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    private final Multimap<Attribute, AttributeModifier> modifiers;

    public VilioArmor(EquipmentSlot equip, Properties prop) {
        super(ModArmorMaterials.VILIO, equip, prop);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[equip.getIndex()];
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double)this.material.getDefenseForSlot(slot), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double)this.material.getToughness(), AttributeModifier.Operation.ADDITION));
        builder.put(ModAttributes.MAGIC_RESISTANCE, new AttributeModifier(uuid, "Strong Magic Resistance", -2.5F, AttributeModifier.Operation.ADDITION));
        if (this.knockbackResistance > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double)this.knockbackResistance, AttributeModifier.Operation.ADDITION));
        }

        this.modifiers = builder.build();
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if(stack.getItem() instanceof VilioArmor){
            if(slot.getIndex() == 1){
                return new ResourceLocation(ModMain.MODID, "textures/models/armor/vilio_armor_layer_2.png").toString();
            }
            return new ResourceLocation(ModMain.MODID, "textures/models/armor/vilio_armor_layer_1.png").toString();
        }
        return null;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_40390_) {
        return p_40390_ == this.slot ? this.modifiers : super.getDefaultAttributeModifiers(p_40390_);
    }

    private static Item getNetheriteVersion(Item item){
         Map<Item, Item> VILIO_TO_NETHERITE = Map.of(
                ModItems.VILIO_BOOTS.get(), Items.NETHERITE_BOOTS,
                ModItems.VILIO_LEGGINGS.get(), Items.NETHERITE_LEGGINGS,
                ModItems.VILIO_CHESTPLATE.get(), Items.NETHERITE_CHESTPLATE,
                ModItems.VILIO_HELMET.get(), Items.NETHERITE_HELMET);
         if(VILIO_TO_NETHERITE.containsKey(item)){
             return VILIO_TO_NETHERITE.get(item);
         }
         return Items.AIR;
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        ItemStack stack = new ItemStack(getNetheriteVersion(itemEntity.getItem().getItem()), itemEntity.getItem().getCount());
        itemEntity.getItem().getAllEnchantments().forEach(stack::enchant);
        itemEntity.level.addFreshEntity(new ItemEntity(itemEntity.getLevel(), itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), stack));
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if(stack.getDamageValue() + amount >= stack.getMaxDamage()){
            ItemStack itemStack = stack.copy();
            itemStack.setDamageValue(0);
            stack.shrink(stack.getCount());
            ItemStack itemStack1 = new ItemStack(getNetheriteVersion(itemStack.getItem()), itemStack.getCount());
            itemStack.getAllEnchantments().forEach(itemStack1::enchant);
            entity.setItemSlot(this.slot, itemStack1);
            return 0;
        }
        return amount;
    }

}
