package net.lucca.mohard.init;

import com.google.common.collect.Multimap;
import net.lucca.mohard.itens.custom.CustomItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ModCustomItems {

    public static final CustomItem<SwordItem> TRAINING_SWORD = new CustomItem<SwordItem>((SwordItem) Items.WOODEN_SWORD, 3)
            .withDefaultAttributes()
            .unbreakable(true)
            .withEnchantment(Enchantments.UNBREAKING, 3)
            .withEnchantment(Enchantments.SHARPNESS, 5)
            .withAttributeModifier(ModAttributes.RAW_ARMOR, 10, AttributeModifier.Operation.ADDITION, EquipmentSlot.MAINHAND)
            .withAttributeModifier(ModAttributes.RAW_ARMOR, 0.3, AttributeModifier.Operation.MULTIPLY_BASE, EquipmentSlot.MAINHAND);

    public static final CustomItem<CrossbowItem> MAGIC_CROSSBOW = new CustomItem<CrossbowItem>((CrossbowItem) Items.CROSSBOW, 3)
            .withDefaultAttributes()
            .withEnchantment(Enchantments.MULTISHOT, 1)
            .withEnchantment(Enchantments.QUICK_CHARGE, 3)
            .withEnchantment(Enchantments.PIERCING, 3)
            .withAttributeModifier(ModAttributes.MAGIC_DAMAGE, 20, AttributeModifier.Operation.ADDITION, EquipmentSlot.MAINHAND)
            .withAttributeModifier(ModAttributes.ARMOR_PENETRATION, 5, AttributeModifier.Operation.ADDITION, EquipmentSlot.MAINHAND);

    public static final CustomItem<BannerItem> BANNER = customBanner();

    public static final CustomItem<ShieldItem> SHIELD = customShield().unbreakable(true)
            .withEnchantment(Enchantments.SMITE, 5)
            .withEnchantment(Enchantments.KNOCKBACK, 2)
            .withAttributeModifier(ModAttributes.RAW_ARMOR, 0.5, AttributeModifier.Operation.MULTIPLY_BASE, EquipmentSlot.OFFHAND)
            .withAttributeModifier(Attributes.MAX_HEALTH, 0.5,AttributeModifier.Operation.MULTIPLY_BASE, EquipmentSlot.OFFHAND)
            .withAttributeModifier(Attributes.ATTACK_DAMAGE, 10,AttributeModifier.Operation.ADDITION, EquipmentSlot.MAINHAND)
            .withAttributeModifier(Attributes.ATTACK_SPEED, -3.5,AttributeModifier.Operation.ADDITION, EquipmentSlot.MAINHAND)
            .withAttributeModifier(Attributes.MAX_HEALTH, 0.5,AttributeModifier.Operation.MULTIPLY_BASE, EquipmentSlot.MAINHAND);


    public static LootPool shieldPool(NumberProvider rolls){
        return getCustomItemPool(rolls, SHIELD.getItemStack());
    }
    public static LootPool getTrainingSwordPool(NumberProvider rolls){
        return getCustomItemPool(rolls, TRAINING_SWORD.getItemStack());
    }
    public static LootPool magicCrossbowPool(NumberProvider rolls){
        return getCustomItemPool(rolls, MAGIC_CROSSBOW.getItemStack());
    }

    private static LootPool getCustomItemPool(NumberProvider rolls, ItemStack itemStack){
        return getCustomItemPool(rolls, itemStack.getItem(), Arrays.stream(EquipmentSlot.values()).filter(equipmentSlot -> !itemStack.getAttributeModifiers(equipmentSlot).isEmpty()).collect(Collectors.toMap(equipmentSlot -> equipmentSlot, itemStack::getAttributeModifiers)), itemStack.getAllEnchantments(), itemStack.getOrCreateTag());
    }
    public static LootPool getCustomItemPool(NumberProvider rolls, ItemLike itemLike, Map<EquipmentSlot, Multimap<Attribute, AttributeModifier>> map, Map<Enchantment, Integer> enchantmentsMap, CompoundTag tag){
        LootPool.Builder builder = LootPool.lootPool().setRolls(rolls).add(LootItem.lootTableItem(itemLike));
        SetAttributesFunction.Builder attributesFunctionBuilder = SetAttributesFunction.setAttributes();
        map.keySet().forEach(equipmentSlot -> map.get(equipmentSlot).forEach((attribute, attributeModifier) -> attributesFunctionBuilder.withModifier(SetAttributesFunction.modifier(attributeModifier.getName(), attribute, attributeModifier.getOperation(), ConstantValue.exactly((float) attributeModifier.getAmount())).withUuid(attributeModifier.getId()).forSlot(equipmentSlot))));
        SetEnchantmentsFunction.Builder enchantmentsFunctionBuilder = new SetEnchantmentsFunction.Builder();
        enchantmentsMap.keySet().forEach(enchantment -> enchantmentsFunctionBuilder.withEnchantment(enchantment, ConstantValue.exactly((float)enchantmentsMap.get(enchantment))));
        LootItemFunction.Builder nbtFunction = SetNbtFunction.setTag(tag);
        return builder.apply(attributesFunctionBuilder).apply(enchantmentsFunctionBuilder).apply(nbtFunction).build();
    }


    private static CustomItem<BannerItem> customBanner(){
        CustomItem<BannerItem> customItem = new CustomItem<>(((BannerItem) Items.PURPLE_BANNER), 4);
        CompoundTag tag = BlockItem.getBlockEntityData(customItem.getItemStack());
        if(tag == null) tag = new CompoundTag();
        @Nonnull ListTag tags = getBannerTag();
        tag.put("Patterns", tags);
        BlockItem.setBlockEntityData(customItem.getItemStack(), BlockEntityType.BANNER, tag);
        return customItem;
    }

    private static CustomItem<ShieldItem> customShield(){
        CustomItem<ShieldItem> customItem = new CustomItem<>(((ShieldItem) Items.SHIELD), 4);
        ItemStack stack = BANNER.getItemStack();
        CompoundTag compoundtag = BlockItem.getBlockEntityData(stack);
        CompoundTag compoundtag1 = compoundtag == null ? new CompoundTag() : compoundtag.copy();
        compoundtag1.putInt("Base", ((BannerItem)stack.getItem()).getColor().getId());
        BlockItem.setBlockEntityData(customItem.getItemStack(), BlockEntityType.BANNER, compoundtag1);
        return customItem;
    }
    @Nonnull
    private static ListTag getBannerTag(){
        ListTag listTag = new ListTag();
        CompoundTag compoundtag1 = new CompoundTag();
        compoundtag1.putString("Pattern", "gru");
        compoundtag1.putInt("Color", 15);
        listTag.add(compoundtag1);
        CompoundTag compoundtag2 = new CompoundTag();
        compoundtag2.putString("Pattern", "cbo");
        compoundtag2.putInt("Color", 8);
        listTag.add(compoundtag2);
        return listTag;
    }
}
