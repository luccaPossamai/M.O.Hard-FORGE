package net.lucca.mohard.util.mechanics.evolution;

import net.lucca.mohard.config.ModServerConfig;
import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.itens.essence.EssenceData;
import net.lucca.mohard.itens.essence.EssenceDataHelper;
import net.lucca.mohard.itens.essence.EssenceGiver;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.lucca.mohard.util.help.Methods;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber
public class ModMobEvolution {

    @SubscribeEvent
    public static void evolucao(EntityJoinLevelEvent event){
        if(event.getEntity() instanceof LivingEntity livingEntity) {
            if(!(livingEntity instanceof Player)) {
                if (!hasTag(livingEntity) && !ModServerConfig.freezeEvolution.get() && !livingEntity.level.isClientSide()) {
                    int plusDifficulty = 0;
                    if(livingEntity.getRandom().nextInt(100) < (LevelMechanic.getLevel(livingEntity.level) / 50) && livingEntity instanceof Monster monster){
                        plusDifficulty = 1;

                        livingEntity.getTags().add("StrongMonster");
                        generateStrongMonster(monster);
                    }
                    setNivel(livingEntity, plusDifficulty);
                }
            }
        }
    }



    @SubscribeEvent
    public static void dropEssence(LivingDropsEvent event){
        if(event.getEntity() != null) {
            LivingEntity livingEntity = event.getEntity();
            RandomSource randomSource = livingEntity.getRandom();
            Level world = livingEntity.level;
            EssenceItem essence = EssenceDataHelper.getEssenceItemByEntity(livingEntity);
            boolean hurtByPlayer = hurtByPlayer(livingEntity);
            modifyItem(hurtByPlayer, event);

            if (essence != null && !livingEntity.isBaby()) {
                int level = getEntityLevel(livingEntity);
                boolean flagStrongMonster = livingEntity.getTags().contains("StrongMonster");
                int plusUpgrade = flagStrongMonster ? 2 : 0;
                int negativeUpgrade = Math.min(3, Math.round(level / 50F)) + plusUpgrade;
                int positiveUpgrade = Math.min(3, Math.round(level / 75F)) + plusUpgrade;
                int lootLevel = event.getLootingLevel();
                boolean shouldDecreasePercentage = false;
                if (!essence.isValid() || flagStrongMonster) {
                    event.getDrops().add(new ItemEntity(world, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), new EssenceGiver(essence).withNegativeUpgradeLevel(negativeUpgrade).withPositiveUpgradesLevel(positiveUpgrade).randomBinding(flagStrongMonster).bindingWithChance(lootLevel).castToItemStack()));
                    shouldDecreasePercentage = true;
                }
                TargetingConditions player = TargetingConditions.forCombat().range(10D);
                AABB bb = livingEntity.getBoundingBox().inflate(10D);
                int j = world.getNearbyEntities(Player.class, player, livingEntity, bb).size();
                if (j > 0 || hurtByPlayer) {
                    int lootingLevel = event.getLootingLevel() + 1;
                    while (lootingLevel > 0) {
                        lootingLevel--;
                        if (randomSource.nextInt(100) < (30 - (shouldDecreasePercentage ? 15 : 0))) {
                            event.getDrops().add(new ItemEntity(world, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), new EssenceGiver(essence).withNegativeUpgradeLevel(negativeUpgrade).withPositiveUpgradesLevel(positiveUpgrade).randomBinding(flagStrongMonster).bindingWithChance(lootLevel).castToItemStack()));
                        }
                    }
                }
            }
        }
    }

    private static int getEntityLevel(LivingEntity livingEntity){
        if(hasTag(livingEntity)) {
            return Integer.parseInt(livingEntity.getTags().stream().filter(s -> s.split(":", 2)[0].equals("MohardLevel")).toList().get(0).split(":", 2)[1]);
        }
        return 0;
    }
    private static void modifyItem(boolean hurtByPlayer, LivingDropsEvent event) {
        if(!hurtByPlayer) return;
        Collection<ItemEntity> drops = event.getDrops();
        Collection<ItemEntity> toRemove = new ArrayList<>();
        Collection<ItemEntity> toAdd = new ArrayList<>();
        for (ItemEntity itemStack : drops) {
            ItemStack itemStack1 = itemStack.getItem().copy();
            ModifiedItem modifiedItem = new ModifiedItem(itemStack1);
            if (modifiedItem.isChanged()) {
                toAdd.add(new ItemEntity(itemStack.getLevel(), itemStack.getX(), itemStack.getY(), itemStack.getZ(), itemStack1));
                toRemove.add(itemStack);
            }
        }
        event.getDrops().removeAll(toRemove);
        event.getDrops().addAll(toAdd);
    }

    private static void setNivel(LivingEntity livingEntity, int plusDifficulty) {

        int difficulty = livingEntity.level.getDifficulty().getId() + plusDifficulty;

        Map<Attribute, Double> statsForEntity = new HashMap<>();
        List<Attribute> attributes = Methods.getAttributes();
        for (Attribute att : attributes) {
            statsForEntity.put(att, livingEntity.getAttribute(att) != null? livingEntity.getAttribute(att).getValue() :att.getDefaultValue());
        }
        double velocidade = livingEntity.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);
        EssenceItem essenceItem = EssenceDataHelper.getEssenceItemByEntity(livingEntity);
        double level = LevelMechanic.getLevel(livingEntity.level);


        if (statsForEntity.get(ModAttributes.RAW_ARMOR) == 0) {
            statsForEntity.put(ModAttributes.RAW_ARMOR, livingEntity.getAttributeBaseValue(Attributes.ARMOR));
        }

        boolean flag = EssenceDataHelper.isBoss(livingEntity.getType());
        if (essenceItem != null) {
            EssenceData essenceData = essenceItem.getEssenceData();
            Map<Attribute, Double> statsFromEssence = essenceData.essenceStats().getStats(0, 0);
            for (Attribute attribute : attributes) {
                boolean flag2 = attribute.equals(ModAttributes.ARMOR_PENETRATION) || attribute.equals(ModAttributes.MAGIC_DAMAGE) || attribute.equals(ModAttributes.PHYSICAL_DAMAGE) || attribute.equals(ModAttributes.PROJECTILE_DAMAGE);
                double essenceValue = statsFromEssence.get(attribute);
                double baseValue = statsForEntity.get(attribute) + (!flag ? essenceValue : 0);
                statsForEntity.put(attribute, attributeScale(attribute, baseValue, level, getDifficultyMultiplier(difficulty) - (flag ? flag2 ? 1 : -1 : 0)));
                if(flag) {
                    baseValue = statsForEntity.get(attribute);
                    statsForEntity.put(attribute, essenceValue + baseValue);
                }
            }
        }

        for (Attribute att : attributes) {
            if (livingEntity.getAttribute(att) == null) continue;
            double value = statsForEntity.get(att);
            livingEntity.getAttribute(att).setBaseValue(value);
            if (att.equals(ModAttributes.AGILITY)) {
                livingEntity.getAttribute(Attributes.MOVEMENT_SPEED)
                        .setBaseValue(Math.min(1.50D * velocidade, velocidade + (velocidade * value / 125)));
            }
        }

        livingEntity.setHealth(Math.round(statsForEntity.get(Attributes.MAX_HEALTH)));
        livingEntity.getTags().add("MohardLevel:"+(int)Math.round(level + (plusDifficulty * level / 3)));
    }

    private static double attributeScale(Attribute att, double value, double level, double difficulty){
        double scale = 0;
        double min = att instanceof RangedAttribute ? ((RangedAttribute) att).getMinValue() : value;
        double max = att instanceof RangedAttribute ? ((RangedAttribute) att).getMaxValue() : value;
        if(ModAttributes.RAW_ARMOR.equals(att)){
            scale = value + ((value * (level / 75)) + (level / 30)) * difficulty;
        } else if(Attributes.MAX_HEALTH.equals(att)) {
            scale = value + ((value * (level / 50)) + (level / 4)) * difficulty;

        } else if(ModAttributes.AGILITY.equals(att)) {
            scale = value + (level / 600) + (value * (level / 300));
        } else if(ModAttributes.ARMOR_PENETRATION.equals(att)) {
            scale = value + (level / 10) + (value * (level / 50));
        } else if(ModAttributes.PROJECTILE_DAMAGE.equals(att) ||
                ModAttributes.MAGIC_DAMAGE.equals(att) ||
                ModAttributes.PHYSICAL_DAMAGE.equals(att)) {
            scale = value + ((level / 20) + (value * (level / 200))) * difficulty;
        }

        return Mth.clamp(scale, min, max);
    }

    private static double getDifficultyMultiplier(int difficulty){
        return 1 + (0.5D * (difficulty - 1));
    }

    private static boolean hasTag(LivingEntity livingEntity) {
        return livingEntity.getTags().stream().anyMatch(s -> s.split(":", 2)[0].equals("MohardLevel"));
    }

    private static void generateStrongMonster(Monster monster){
        monster.setCustomName(Component.literal("").append(monster.getDisplayName()).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
        monster.setPersistenceRequired();
    }


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class BusEvents{
        @SubscribeEvent
        public static void addAttributes(EntityAttributeModificationEvent event){
            for(EntityType<? extends LivingEntity> entityType : event.getTypes()) {
                for(Attribute att : getSetupAttributes()) {

                    if(!event.has(entityType, att)){
                        event.add(entityType, att, att.getDefaultValue());
                    }
                }
            }
        }
        private static List<Attribute> getSetupAttributes(){
            return Arrays.asList(Attributes.MAX_HEALTH,
                    ModAttributes.PHYSICAL_DAMAGE,
                    ModAttributes.AGILITY,
                    ModAttributes.INTELLECT,
                    ModAttributes.ARMOR_PENETRATION,
                    ModAttributes.MAGIC_DAMAGE,
                    ModAttributes.RAW_ARMOR,
                    ModAttributes.PROJECTILE_DAMAGE,
                    ModAttributes.MAGIC_RESISTANCE);
        }

    }

    private static boolean hurtByPlayer(LivingEntity livingEntity){
        return (livingEntity.getKillCredit() instanceof Player);
    }

}
