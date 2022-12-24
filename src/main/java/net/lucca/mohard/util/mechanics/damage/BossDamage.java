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
        double finalDamage = damage;
        Optional<Type> bossType = Arrays.stream(Type.values()).filter(a -> a.getType().equals(type)).findFirst();
        if (bossType.isPresent()) {
            finalDamage = bossType.get().apllyFight(source, finalDamage, penetration);
        }
        if(entity.getTags().contains("StrongMonster")){
            finalDamage = Type.MONSTER.apllyFight(source, finalDamage, penetration);
        }

        return (float) finalDamage;
    }

    public static boolean isValidBossFight(LivingEntity entity){
        if(entity.getTags().contains("StrongMonster")) return true;
        EntityType<?> type = entity.getType();
        Optional<Type> bossType = Arrays.stream(Type.values()).filter(a -> a.getType().equals(type)).findFirst();
        return bossType.isPresent() && bossType.get().getType().equals(type);
    }

    /**
     * Variables :
     *  -> 'c' is responsible to adjust the penetration 'sensitive'
     *      when it gets higher, the damage decreases;
     *  -> 'k' is the angle curve adjustment, it takes care of the
     *      angle between the curve and the x line. When it gets
     *      higher, the damage also decreases;
     */
    private static DoubleBinaryOperator baseLogFunc(){
        return logFunc(1, 400);
    }
    protected static DoubleBinaryOperator logFunc(float k, float c){
        return (amount, penetration) -> (amount * Math.log(amount + 1)) / (k * Math.log(amount + 1 + (amount * Math.log(amount + 1) * (c + 1 - (c / 1E2F) * (penetration)))));
    }


    public enum Type implements IExtensibleEnum {

        //DRAGON(EntityType.ENDER_DRAGON, "arrow", value -> value * 1.25F, (value, penetration) -> Math.log10(value + 1) / Math.log10(5 - (penetration / 125))),
        //        GUARDIAN(EntityType.ELDER_GUARDIAN, "trident", value -> value * 1.5F, (value, penetration) -> Math.log10(value + 1) / Math.log10(3 - (penetration / 100))),
        //        WITHER(EntityType.WITHER, DamageSource.MAGIC, value -> value * 1.1, (value, penetration) -> Math.log10(value + 1) / Math.log10(4 - (penetration / 50))),
        //        WARDEN(EntityType.WARDEN, "fireworks", value -> value * 1.75F, (value, penetration) -> Math.log10(value + 1) / Math.log10(4 - (penetration / 75)))
        //

        MONSTER(EntityType.EGG, DamageSource.CACTUS, value -> value, (value, penetration) -> value / (11 - (Math.log10(value + 1) / Math.log10(2 - (penetration / 722))))),
        DRAGON(EntityType.ENDER_DRAGON, "arrow", value -> value * 1.25F, BossDamage.logFunc(2F, 1000)),
        GUARDIAN(EntityType.ELDER_GUARDIAN, "trident", value -> value * 1.5F, BossDamage.baseLogFunc()),
        WITHER(EntityType.WITHER, DamageSource.MAGIC, value -> value * 1.1, BossDamage.logFunc(1.4F, 100)),
        WARDEN(EntityType.WARDEN, "fireworks", value -> value * 1.75F, BossDamage.logFunc(1.7F, 700))
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
