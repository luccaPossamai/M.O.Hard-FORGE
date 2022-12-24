package net.lucca.mohard.itens.armors;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    MAGIC("magic", 13, new int[]{2, 4, 5, 3}, 12, SoundEvents.ARMOR_EQUIP_LEATHER, 0, 0, () -> {
        return Ingredient.of(Items.LEATHER);
    }, false),
    ;
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private static final int[] CONSTANT_HEALTH_PER_SLOT = new int[]{10, 10, 10, 10};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;
    private final boolean constantHealth;

    ModArmorMaterials(String p_40474_, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> ingredientSupplier, boolean constantHealth) {
        this.name = p_40474_;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(ingredientSupplier);
        this.constantHealth = constantHealth;
    }

    @Override
    public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
        return (constantHealth ? CONSTANT_HEALTH_PER_SLOT[slot.getIndex()] : HEALTH_PER_SLOT[slot.getIndex()]) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return this.slotProtections[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
