package net.lucca.mohard.itens.essence;

import net.lucca.mohard.enchantments.essences.EssenceBindingHelper;
import net.lucca.mohard.enchantments.essences.EssenceEntailBinding;
import net.lucca.mohard.init.ModBindings;
import net.lucca.mohard.util.mechanics.evolution.LevelMechanic;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EssenceGiver {

    private final List<EssenceItem> essenceItems;
    private List<EssenceEntailBinding> bindings = new ArrayList<>();
    private boolean canHaveBinding = true;
    private int bindingChance = 1;
    private int negativeLevel = 0;
    private int upgradeLevel = 0;

    public EssenceGiver(EssenceItem essenceItem){
        this(List.of(essenceItem));
    }
    public EssenceGiver(){
        this(validEssences());
    }

    public EssenceGiver(int level){
        this(validEssencesAboveLevel(level));
    }

    private EssenceGiver(List<EssenceItem> essenceItems){
        this.essenceItems = essenceItems;

    }

    public ItemStack castToItemStack(){
        if(canHaveBinding) {
            if (new Random().nextInt(100) < bindingChance) {
                this.randomBinding();
            }
        }
        EssenceEntailBinding binding = this.bindings.isEmpty() ? null : this.bindings.get(new Random().nextInt(this.bindings.size()));
        ItemStack itemStack = new ItemStack(this.essenceItems.get(this.essenceItems.size() > 1 ? new Random().nextInt(this.essenceItems.size()) : 0));
        if(EssenceBindingHelper.canBind(itemStack) && binding != null){
            EssenceBindingHelper.bind(itemStack, binding);
        }
        if(EssenceDataHelper.canUpgradeEssence(itemStack)){
            while(this.upgradeLevel > 0){
                this.upgradeLevel--;
                itemStack = EssenceDataHelper.upgradeLevel(itemStack);
            }
        }
        if(EssenceDataHelper.canUpgradeNegativeEssence(itemStack)){
            while(this.negativeLevel > 0){
                this.negativeLevel--;
                itemStack = EssenceDataHelper.upgradeNegativeLevel(itemStack);
            }
        }
        return itemStack;
    }

    public EssenceGiver randomBinding(boolean randomBinding){
        return randomBinding ? randomBinding() : this;
    }


    public EssenceGiver randomBinding(){
        this.bindings = ModBindings.BINDINGS;
        Collections.shuffle(this.bindings);
        return this;
    }
    public EssenceGiver bindingWithChance(int mult){
        this.bindingChance = mult;
        return this;
    }
    public EssenceGiver withUpgradesLevel(int i){
        return this.withPositiveUpgradesLevel(i).withNegativeUpgradeLevel(i);
    }

    public EssenceGiver withNegativeUpgradeLevel(int i){
        this.negativeLevel = Mth.clamp(i, 0, 3);
        return this;
    }

    public EssenceGiver withPositiveUpgradesLevel(int i){
        this.upgradeLevel = Mth.clamp(i, 0, 3);
        return this;
    }


    private static List<EssenceItem> validEssencesAboveLevel(int level){
        return validEssences().stream().filter(essenceItem -> LevelMechanic.getEssenceLevel(essenceItem.getEssenceData()) < level).collect(Collectors.toList());
    }
    private static List<EssenceItem> validEssences(){
        return allEssences().stream().filter(EssenceItem::isValid).collect(Collectors.toList());
    }

    private static List<EssenceItem> allEssences(){
        return EssenceDataHelper.ESSENCE_DATA_LIST.stream().map(EssenceDataHelper::getEssenceItemByEssenceData).collect(Collectors.toList());
    }

}
