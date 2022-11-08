package net.lucca.mohard.init;

import com.google.common.collect.Multimap;
import net.lucca.mohard.itens.custom.CustomItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ModCustomItems {

    public static final ItemStack TRAINING_SWORD = new CustomItem(Items.WOODEN_SWORD, 3)
            .withDefaultAttributes()
            .withEnchantment(Enchantments.UNBREAKING, 3)
            .withEnchantment(Enchantments.SHARPNESS, 5)
            .withAttributeModifier(ModAttributes.RAW_ARMOR, 10, AttributeModifier.Operation.ADDITION, EquipmentSlot.MAINHAND)
            .withAttributeModifier(ModAttributes.RAW_ARMOR, 0.3, AttributeModifier.Operation.MULTIPLY_BASE, EquipmentSlot.MAINHAND).getItemStack();

    public static final ItemStack MAGIC_CROSSBOW = new CustomItem(Items.CROSSBOW, 3)
            .withDefaultAttributes()
            .withEnchantment(Enchantments.MULTISHOT, 1)
            .withEnchantment(Enchantments.QUICK_CHARGE, 3)
            .withEnchantment(Enchantments.PIERCING, 3)
            .withAttributeModifier(ModAttributes.MAGIC_DAMAGE, 20, AttributeModifier.Operation.ADDITION, EquipmentSlot.MAINHAND)
            .withAttributeModifier(ModAttributes.ARMOR_PENETRATION, 5, AttributeModifier.Operation.ADDITION, EquipmentSlot.MAINHAND).getItemStack();


    public static LootPool getTrainingSwordPool(NumberProvider rolls){
        return getCustomItemPool(rolls, TRAINING_SWORD);
    }
    public static LootPool getMagicCrossbow(NumberProvider rolls){
        return getCustomItemPool(rolls, MAGIC_CROSSBOW);
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
}
