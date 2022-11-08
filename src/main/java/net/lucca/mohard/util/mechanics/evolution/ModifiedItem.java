package net.lucca.mohard.util.mechanics.evolution;

import com.google.common.collect.Multimap;
import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.itens.VilioCharge;
import net.lucca.mohard.itens.artifacts.DeepNetherPetrifiedTome;
import net.lucca.mohard.itens.artifacts.GenericArtifact;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;

import java.util.*;

public class ModifiedItem {

    private final boolean changed;

    public ModifiedItem(ItemStack itemStack){
        EquipmentSlot slot = getSlotOfItem(itemStack);
        if(slot != null && !itemStack.getOrCreateTag().contains("Modified")){
            List<Attribute> attributes = getAttributesForItem(itemStack).stream().filter(attribute -> (new Random().nextFloat() * LevelMechanic.getLevel(null) > 70)).toList();
            if(!attributes.isEmpty()) {
                populateDefaultAttributes(itemStack);

                this.changed = true;
                CompoundTag tag = itemStack.getOrCreateTag();
                tag.putInt("Modified", attributes.size());
                itemStack.setTag(tag);
                for (Attribute attribute : attributes) {
                    boolean op = new Random().nextInt(9) <= 1;
                    itemStack.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.getDescriptionId() + " boost", (new Random().nextFloat() * (5 + LevelMechanic.getLevel(null) % 50)) / (op ? 1E2 : 1), op ? AttributeModifier.Operation.MULTIPLY_BASE : AttributeModifier.Operation.ADDITION), slot);
                }
                return;
            }
        }
        this.changed = false;

    }

    public boolean isChanged() {
        return changed;
    }


    public static void populateDefaultAttributes(ItemStack itemStack){
        Map<EquipmentSlot, Multimap<Attribute, AttributeModifier>> map = new HashMap<>();
        for(EquipmentSlot slot : EquipmentSlot.values()){
            map.put(slot, itemStack.getAttributeModifiers(slot));
        }
        Arrays.stream(EquipmentSlot.values()).toList().forEach(equipmentSlot -> {
            Multimap<Attribute, AttributeModifier> multimap = map.get(equipmentSlot);
            Registry.ATTRIBUTE.entrySet().forEach(resourceKeyAttributeEntry -> {
                if(multimap.containsKey(resourceKeyAttributeEntry.getValue())) {
                    multimap.get(resourceKeyAttributeEntry.getValue()).forEach(attributeModifier -> itemStack.addAttributeModifier(resourceKeyAttributeEntry.getValue(), attributeModifier, equipmentSlot));
                }
            });
        });
    }

    private static List<Attribute> getAttributesForItem(ItemStack itemStack){
        List<Attribute> attributes = new ArrayList<>(List.of(Attributes.MAX_HEALTH, ModAttributes.RAW_ARMOR));
        Item item = itemStack.getItem();
        if(item instanceof GenericArtifact){
            attributes.add(ModAttributes.MAGIC_DAMAGE);
            attributes.add(ModAttributes.ARMOR_PENETRATION);
        }
        if(item instanceof TieredItem){
            attributes.add(ModAttributes.PHYSICAL_DAMAGE);
            attributes.add(ModAttributes.ARMOR_PENETRATION);
        }
        if(item instanceof ProjectileWeaponItem){
            attributes.add(ModAttributes.PROJECTILE_DAMAGE);
            attributes.add(ModAttributes.AGILITY);
            attributes.add(ModAttributes.ARMOR_PENETRATION);
        }
        if(item instanceof ShieldItem || item.equals(Items.TOTEM_OF_UNDYING) || item instanceof VilioCharge || item instanceof DeepNetherPetrifiedTome){
            attributes.add(ModAttributes.INTELLECT);
        }
        return attributes;
    }
    private static EquipmentSlot getSlotOfItem(ItemStack itemStack){
        Item item = itemStack.getItem();
        if(item instanceof ShieldItem || item.equals(Items.TOTEM_OF_UNDYING) || item instanceof VilioCharge || item instanceof DeepNetherPetrifiedTome){
                return EquipmentSlot.OFFHAND;
        }
        if(item instanceof TieredItem || item instanceof GenericArtifact || item instanceof ProjectileWeaponItem){
            return EquipmentSlot.MAINHAND;
        }
        if(item instanceof ArmorItem armorItem){
            return armorItem.getSlot();
        }

        return null;
    }
}
