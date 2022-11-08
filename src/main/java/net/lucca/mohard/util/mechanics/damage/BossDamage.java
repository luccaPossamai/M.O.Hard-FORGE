package net.lucca.mohard.util.mechanics.damage;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.IExtensibleEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;

public class BossDamage {

    public static float getBossBattleTypeDamage(LivingEntity entity, DamageSource source, double damage, double penetration){
        EntityType<?> type = entity.getType();
        float finalDamage = 0;
        if(entity.getTags().contains("StrongMonster")){
            finalDamage = Type.MONSTER.apllyFight(source, damage, penetration);
        } else {
            Optional<Type> bossType = Arrays.stream(Type.values()).filter(a -> a.getType().equals(type)).findFirst();
            if (bossType.isPresent()) {
                finalDamage = bossType.get().apllyFight(source, damage, penetration);
            }
        }

        return finalDamage;
    }

    public static boolean isValidBossFight(LivingEntity entity){
        if(entity.getTags().contains("StrongMonster")) return true;
        EntityType<?> type = entity.getType();
        Optional<Type> bossType = Arrays.stream(Type.values()).filter(a -> a.getType().equals(type)).findFirst();
        return bossType.isPresent() && bossType.get().getType().equals(type);
    }


    public enum Type implements IExtensibleEnum {

        MONSTER(EntityType.EGG, DamageSource.ANVIL, value -> value, (value, penetration) -> value / (11 - (Math.log10(value + 1) / Math.log10(2 - (penetration / 722))))),
        DRAGON(EntityType.ENDER_DRAGON, "arrow", value -> value * 1.25F, (value, penetration) -> Math.log10(value + 1) / Math.log10(5 - (penetration / 125))),
        GUARDIAN(EntityType.ELDER_GUARDIAN, "trident", value -> value * 1.5F, (value, penetration) -> Math.log10(value + 1) / Math.log10(3 - (penetration / 100))),
        WITHER(EntityType.WITHER, DamageSource.MAGIC, value -> value * 1.1, (value, penetration) -> Math.log10(value + 1) / Math.log10(4 - (penetration / 50))),
        WARDEN(EntityType.WARDEN, "fireworks", value -> value * 1.75F, (value, penetration) -> Math.log10(value + 1) / Math.log10(4 - (penetration / 75)))
        ;

        private final EntityType<?> type;
        private final DoubleFunction<Double> weaknessMultiplier;
        private final List<String> weaknessId;

        private final DoubleBinaryOperator damageFunction;

        Type(EntityType<?> type, DamageSource weakness, double weaknessMultiplier, DoubleBinaryOperator damageFunction) {
            this(type, weakness, a -> a * weaknessMultiplier, damageFunction);
        }

        Type(EntityType<?> type, DamageSource weakness, DoubleFunction<Double> weaknessMultiplier, DoubleBinaryOperator damageFunction) {
            this(type, weakness.msgId, weaknessMultiplier, damageFunction);
        }

        Type(EntityType<?> type, String weakness, DoubleFunction<Double> weaknessMultiplier, DoubleBinaryOperator damageFunction) {
            this(type, List.of(weakness), weaknessMultiplier, damageFunction);
        }

        Type(EntityType<?> type, List<String> weaknessId, DoubleFunction<Double> weaknessMultiplier, DoubleBinaryOperator damageFunction) {
            this.type = type;
            this.weaknessMultiplier = weaknessMultiplier;
            this.weaknessId = weaknessId;
            this.damageFunction = damageFunction;
        }

        public EntityType<?> getType() {
            return type;
        }

        private float apllyFight(DamageSource source, double damage, double penetration){
            double finalDamage = this.damageFunction.applyAsDouble(damage, penetration);
            boolean flag = this.weaknessId.stream().anyMatch(s -> s.equals(source.msgId));
            if(flag){
                finalDamage = this.weaknessMultiplier.apply(finalDamage);
            }
            return Math.round(finalDamage);
        }
        public static BossDamage.Type create(String string, EntityType<?> type, List<String> weaknessId, DoubleFunction<Double> weaknessMultiplier, DoubleBinaryOperator damageFunction) {
            throw new IllegalStateException("Enum not extended");
        }
    }


}
