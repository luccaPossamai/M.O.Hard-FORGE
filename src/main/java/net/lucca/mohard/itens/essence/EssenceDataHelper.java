package net.lucca.mohard.itens.essence;


import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.init.ModEssences;
import net.lucca.mohard.util.help.Methods;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.*;

public class EssenceDataHelper {

    public static final List<EssenceData> ESSENCE_DATA_LIST = new ArrayList<>();
    public static final List<MutableComponent> ESSENCE_DESCRIPTION = Arrays.asList(
                Component.translatable("essence.description.health"),
                Component.translatable("essence.description.raw_armor"),
                Component.translatable("essence.description.melee_damage"),
                Component.translatable("essence.description.ranged_damage"),
                Component.translatable("essence.description.magic_damage"),
                Component.translatable("essence.description.armor_penetration"),
                Component.translatable("essence.description.agility"),
                Component.translatable("essence.description.intellect"));

    private static Map<EssenceData, EssenceItem> ESSENCE_DATA_ESSENCE_ITEM_MAP;
    private static Map<EntityType<?>, List<EssenceItem>> ENTITY_ESSENCE_MAP;

    public static void setupEssenceMap(){
        Map<EssenceData, EssenceItem> map = new HashMap<>();
        ModEssences.ESSENCE_ITEMS.getEntries().stream().map(RegistryObject::get).toList().stream().filter(item -> item instanceof EssenceItem).toList().forEach(item -> map.put(((EssenceItem)item).getEssenceData(), (EssenceItem)item));
        ESSENCE_DATA_ESSENCE_ITEM_MAP = map;
    }

    public static void setupEntityMap(){
        Map<EntityType<?>, List<EssenceItem>> map = new HashMap<>();
        ModEssences.ESSENCE_ITEMS.getEntries().stream().map(RegistryObject::get).toList().stream().filter(item -> item instanceof EssenceItem).toList().forEach(item -> {
            List<EssenceItem> essenceItems = map.getOrDefault(((EssenceItem) item).getType(), new ArrayList<>());
            essenceItems.add((EssenceItem) item);
            map.put(((EssenceItem) item).getType(), essenceItems);
        });
        ENTITY_ESSENCE_MAP = map;
    }

    public static double calculateUpgrades(double value, int upgradeValue, int negativeUpgradeValue){
        if (upgradeValue > 0) {
            if (value > 0) {
                value = (value + ((double) (upgradeValue * upgradeValue + upgradeValue) / 2));
            }
        }
        if (negativeUpgradeValue > 0) {
            if (value < 0) {
                double newStat = (value + ((double) (negativeUpgradeValue * negativeUpgradeValue + negativeUpgradeValue) / 2));
                if (newStat > 0) newStat = 0;
                value = newStat;
            }
        }
        return value;
    }


    public static EssenceItem getEssenceItemByEssenceData(EssenceData essenceData){
        return ESSENCE_DATA_ESSENCE_ITEM_MAP.getOrDefault(essenceData, ModEssences.SQUID_ESSENCE.get());
    }
    @Nullable
    public static EssenceItem getEssenceItemByEntity(LivingEntity livingEntity){
        List<EssenceItem> essenceItems = ENTITY_ESSENCE_MAP.getOrDefault(livingEntity.getType(), new ArrayList<>()).stream().filter(essenceItem -> essenceItem.testCondition(livingEntity)).toList();
        if(!essenceItems.isEmpty()){
            if(essenceItems.size() > 1){
                return essenceItems.stream().filter(EssenceItem::isAlternative).toList().get(0);
            } else {
                return essenceItems.get(0);
            }
        } else {
            return null;
        }
    }

    public static int getEssenceLevel(ItemStack stack){
        return stack.getOrCreateTag().getInt("Level");
    }

    public static int getEssenceNegativeLevel(ItemStack stack){
        return stack.getOrCreateTag().getInt("NegativeLevel");
    }

    public static ItemStack upgradeLevel(ItemStack stack){
        if(canUpgradeEssence(stack)){
            ItemStack itemStack = stack.copy();
            CompoundTag tag = itemStack.getOrCreateTag();
            String levelS = "Level";
            int level = tag.getInt(levelS);
            tag.remove(levelS);
            tag.putInt(levelS, level + 1);
            itemStack.setTag(tag);
            return itemStack;
        }
        return stack;
    }

    public static ItemStack upgradeNegativeLevel(ItemStack stack){
        if(canUpgradeNegativeEssence(stack)){
            ItemStack itemStack = stack.copy();
            CompoundTag tag = itemStack.getOrCreateTag();
            String levelS = "NegativeLevel";
            int level = tag.getInt(levelS);
            tag.remove(levelS);
            tag.putInt(levelS, level + 1);
            itemStack.setTag(tag);
            return itemStack;
        }
        return stack;
    }

    public static boolean canUpgradeEssence(ItemStack stack){
        return getEssenceLevel(stack) < 3;
    }

    public static boolean canUpgradeNegativeEssence(ItemStack stack) {
        if (stack.getItem() instanceof EssenceItem essenceItem) {
            EssenceData data = essenceItem.getEssenceData();
            boolean flag = false;
            Map<Attribute, Double> stats = data.essenceStats().getStats(0, getEssenceNegativeLevel(stack));
            for (Attribute att : stats.keySet()) {
                if (stats.get(att) < 0) {
                    flag = true;
                    break;
                }
            }
            return getEssenceNegativeLevel(stack) < 3 && flag;
        }
        return false;
    }

    public static void printEssence(){
        System.out.println("     CreatingEssencePrint:");
        EssenceDataHelper.ESSENCE_DATA_LIST.forEach(essenceData -> {
            EntityType<?> entityType = essenceData.entityType();
            String category = entityType.getCategory().getName();
            category = Methods.capitalizeAndRemove(category, '_');
            String name = entityType.toString();
            name = Methods.capitalizeAndRemove(name, '.', '_');
            name = name.replace("EntityMinecraft", "");
            name = name.replace("EntityMohard", "");
            StringBuilder att = new StringBuilder("( ");
            Map<Attribute, Double> stats = essenceData.essenceStats().getStats(0, 0);
            for(Attribute attribute : Methods.getAttributes()){
                att.append(stats.get(attribute)).append(attribute.equals(ModAttributes.INTELLECT) ? "" : ", ");
            }
            att.append(" )");
            System.out.println("["+category+"] "+name+": "+att);
        });
    }

    public static boolean isUnactive(ItemStack stack){
        if(stack.getOrCreateTag().contains("Unactive")){
            return stack.getOrCreateTag().getBoolean("Unactive");
        }
        return false;
    }

    public static void unactivate(ItemStack stack, boolean unactive){
        stack.getOrCreateTag().putBoolean("Unactive", unactive);
    }


    public static boolean isBoss(EssenceItem essenceItem){
        return isBoss(essenceItem.getType());
    }

    public static boolean isBoss(EntityType<?> entityType){
        return entityType.equals(EntityType.WITHER) ||
                entityType.equals(EntityType.ELDER_GUARDIAN) ||
                entityType.equals(EntityType.ENDER_DRAGON) ||
                entityType.equals(EntityType.WARDEN);
    }

}
