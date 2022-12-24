package net.lucca.mohard.util.mechanics.damage;

import net.lucca.mohard.capabilities.ModCapabilities;
import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.util.mechanics.evolution.LevelMechanic;
import net.lucca.mohard.util.mechanics.evolution.PlayerEvolution;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

/**
 *  Sequence of event methods to process the game damage.
 *  Vanilla DamageSources will be processed based on the
 *  source nature(physical, ranged, magical).
 *
 *  For damages with no scaling in these events, use {@link DirectEntityDamageSource}.
 *
 *  The damage is process by the vanilla armor system, then,
 *  it will be scaled with the attribute of the entity that
 *  is causing the damage.
 */


@Mod.EventBusSubscriber()
public class DamageEvents {

    /**
     *  Calculate the damage to be reduced by the armor
     *
     *  FinalDamage(D) have two processes:
     *   1° a >= 0 :
     *      D(k, damage(d), armor(a)) = d / ((ka) + 1);
     *      k = 2 / d -> D(d, a) = d² / (2a + d);
     *   2º a < 0 :
     *      D(k, d, a) = 2d - (d² / ((k|a|) + 1));
     *      D(d, a) = (d² + 4|a|d) / (2|a| + d);
     *
     *  Summarizing, when the armor(raw armor) is greater
     *  than or equal to zero, the damage is reduced based
     *  on the armor of the entity. But, when the armor is
     *  less than zero, the portion reduced by the armor is
     *  added to the damage.
     *  Example: d = 2, a' = 1, a'' = -1
     *  D'(k, 2, 1) = D'(1, 2, 1) = 2² / (2 * 1 + 2) = 4 / 4 = 1
     *  D''(k, 2, -1) = D''(1, 2, -1) = (2² + 4 * |-1| * 2) / (2 * |-1| + 2)
     *  D'' = 4 + 8 / 4 = 12 / 4 = 3
     *  The absolute value of the difference between the
     *  FinalDamage and the InitialDamage in both processes
     *  is the same.
     *
     */


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void receivedDamage(LivingHurtEvent event) {

        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        if (source.isMagic()) {
            event.setAmount(event.getAmount() * (float) (1 - PlayerEvolution.magicImunne(entity)));
        }
        if (source.isBypassInvul()) return;
        float originalDamage = event.getAmount();
        if(originalDamage != 0) {
            if (BossDamage.isValidBossFight(entity)) {
                if (!source.equals(DamageSource.MAGIC)) {
                    double armorPen = getAttributeValue(source.getEntity(), ModAttributes.ARMOR_PENETRATION);
                    float amount = BossDamage.getBossBattleTypeDamage(entity, source, originalDamage, armorPen);
                    event.setAmount(amount);
                } else {
                    event.setAmount(0);
                }
            } else {
                if (isDamageValid(source)) {
                    double rawArmor = getAttributeValue(entity, ModAttributes.RAW_ARMOR);
                    double armorPen = getAttributeValue(source.getEntity(), ModAttributes.ARMOR_PENETRATION);
                    double a = rawArmor - armorPen / 1.5;
                    double totalArmor = a == 0 ? 0 : (1 - ((a/Math.abs(a))* armorPen * 0.005)) * a;
                    if(rawArmor <= 0) {
                        totalArmor = rawArmor;
                    }
                    if (source.isBypassArmor() && totalArmor > 0) {
                        totalArmor = 0;
                    }

                    float k = 9F;
                    if(totalArmor < 0) {
                        float damageByNegativeArmor = originalDamage * originalDamage / (k * (float) Math.abs(totalArmor) + originalDamage);
                        originalDamage = 2 * originalDamage - damageByNegativeArmor;
                    }
                    totalArmor = Math.max(totalArmor, 0D);
                    float amount = originalDamage * originalDamage / (k * (float) totalArmor + originalDamage);
                    event.setAmount(amount);
                }
            }
        }
        float originalDamage1 = originalDamage;
        if(entity instanceof Player player && source.getEntity() instanceof LivingEntity) {
            player.getCapability(ModCapabilities.ENTAIL_CAPABILITY).orElse(null).getEntail().getHit(originalDamage1, event.getAmount(), player, (LivingEntity) source.getEntity());
        }
        if(source.getEntity() instanceof Player player && !(source instanceof DirectEntityDamageSource damageSource && damageSource.getType().equals(DirectEntityDamageSource.Type.HEALTH_DISCHARGE))) {
            player.getCapability(ModCapabilities.ENTAIL_CAPABILITY).orElse(null).getEntail().dealHit(originalDamage1, event.getAmount(), event.getSource(), player, entity);
        }
    }


    /**
     *  Process the damage
     */

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void generalDamage(LivingHurtEvent event){
        Entity causador = event.getSource() != null ? event.getSource().getEntity() : null;
        if(causador == null) return;
        double attributeValue =  (float) getAttributeValue(causador, getAttributeSource(event.getSource()));
        double dano = calculateDamage(causador, event.getAmount(), attributeValue);
        if(causador instanceof Player player){
            dano = player.getCapability(ModCapabilities.ENTAIL_CAPABILITY).orElse(null).getEntail().bonusOnHit(dano,player,event.getEntity());
        }
        event.setAmount((float)dano);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void explosionDamage(LivingHurtEvent event){
        Entity causador = event.getSource().getEntity();
        float dano = event.getAmount();
        if(event.getSource().isExplosion()) {
            if(!(causador instanceof LivingEntity) && !(causador instanceof FireworkRocketEntity)){
                dano = dano + dano * (LevelMechanic.SERVER_LEVEL / 200F);
            }
            event.setAmount(dano);
        }
    }

    @SubscribeEvent
    public static void damageDodge(LivingAttackEvent event){
        LivingEntity livingEntity = event.getEntity();
        Entity entity = event.getSource().getEntity();
        if(!livingEntity.level.isClientSide) {
            DamageSource damageSource = event.getSource();
            if (damageSource.isBypassInvul()) return;
            boolean flag = entity != null && (entity instanceof LivingEntity || (entity instanceof Projectile projectile && projectile.getOwner() instanceof LivingEntity));
            double toDodge = (event.getAmount() / (Math.max(1, livingEntity.getAttributeValue(ModAttributes.AGILITY)) * 0.02 * (flag ? 1 : 10)));
            if (toDodge < 1) {
                event.setCanceled(true);
                livingEntity.level.playSound((Player) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, livingEntity.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }
    @SubscribeEvent
    public static void lightningDamage(EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            event.getEntity().hurt(DamageSource.LIGHTNING_BOLT, entity.getMaxHealth() / 5);
        }
    }


    private static double getAttributeValue(Entity entity, Attribute attribute){
        if(attribute != null) {
            if (entity instanceof Projectile tiro) {
                if (tiro.getOwner() instanceof LivingEntity livingEntity) {
                    return getAttributeEspecificValue(livingEntity, attribute);
                }
            }
            if (entity instanceof LivingEntity livingEntity) {
                return getAttributeEspecificValue(livingEntity, attribute);
            }
        }
        return 0;
    }

    private static double getAttributeEspecificValue(LivingEntity livingEntity, Attribute attribute){
        if(attribute != null) {
            if (livingEntity.getAttribute(attribute) != null) {
                return livingEntity.getAttributeValue(attribute);
            }
        }
        return 0.0;
    }
    private static boolean isDamageValid(DamageSource damageSource){
        return (damageSource != null && damageSource.getEntity() != null);
    }

    @Nullable
    private static Attribute getAttributeSource(DamageSource damageSource) {
        Attribute attribute = bossSource(damageSource);
        if(attribute != null) return attribute;
        if (damageSource instanceof DirectEntityDamageSource){
            return null;
        }
        if (damageSource.isMagic() || damageSource.isExplosion()) {
            return ModAttributes.MAGIC_DAMAGE;
        }
        if (damageSource.isProjectile()) {
            return ModAttributes.PROJECTILE_DAMAGE;
        }
        if (damageSource.getEntity() != null) {
            return ModAttributes.PHYSICAL_DAMAGE;
        }

        return attribute;
    }

    private static Attribute bossSource(DamageSource damageSource){
        if(damageSource.getEntity() != null && damageSource.getEntity() instanceof WitherSkull || damageSource.getEntity() instanceof WitherBoss) return ModAttributes.PROJECTILE_DAMAGE;
        if(damageSource.getMsgId().equals("dragonBreath")) return ModAttributes.MAGIC_DAMAGE;
        return null;
    }
    private static double calculateDamage(@Nullable Entity damager, double originalDano, double dano) {
        double efic = Math.min(1.5, (originalDano / 10) + ((!(damager instanceof Player) && !(damager instanceof Projectile projectile && projectile.getOwner() instanceof Player) && originalDano >= 0) ? 0.4 : 0));
        return (dano * efic) + originalDano;
    }

}
