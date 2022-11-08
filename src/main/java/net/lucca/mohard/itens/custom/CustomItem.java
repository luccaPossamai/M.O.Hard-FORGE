package net.lucca.mohard.itens.custom;

import net.lucca.mohard.util.mechanics.evolution.ModifiedItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.UUID;

public class CustomItem {

    private final ItemStack itemStack;

    public CustomItem(Item item, int level){
        this(new ItemStack(item, 1), level);
    }

    private CustomItem(ItemStack itemStack, int level){
        this.itemStack = itemStack;
        itemStack.getOrCreateTag().putInt("CustomLevel", level);
        itemStack.getOrCreateTag().putBoolean("Modified", true);
    }

    public CustomItem withDefaultAttributes(){
        ModifiedItem.populateDefaultAttributes(itemStack);
        return this;
    }

    public CustomItem withEnchantment(Enchantment enchantment){
        return this.withEnchantment(enchantment, 1);
    }

    public CustomItem withEnchantment(Enchantment enchantment, int value){
        this.itemStack.enchant(enchantment, value);
        return this;
    }

    public CustomItem withAttributeModifier(Attribute attribute, double value, AttributeModifier.Operation operation, EquipmentSlot slot){
        this.itemStack.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.getDescriptionId()+" "+operation.name()+" boost", value, operation), slot);
        return this;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
