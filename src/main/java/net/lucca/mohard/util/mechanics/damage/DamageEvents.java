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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber()
public class DamageEvents {

    /**
     *  Calculate the damage to be reduced by the armor
     * <p>
     *  D(d, a) =  (d/0.45a + 1)  OLD EQUATION
     *  D(d, a, k) = d / (ak + 1)   -> k = 2 / d
     *  D(d, a) = d² / 2a + d    NEW EQUATION
     *
     */


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void danoRecebido(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        if(source.isMagic()){
            event.setAmount(event.getAmount() * (float) (1 - PlayerEvolution.magicImunne(entity)));
        }
        if(source.isBypassInvul()) return;
        float originalDamage = event.getAmount();
        if(BossDamage.isValidBossFight(entity)){
            if(source.equals(DamageSource.MAGIC)){
                event.setAmount(0);
                return;
            }
            double armorPen = getAttributeValue(source.getEntity(), ModAttributes.ARMOR_PENETRATION);
            float amount = BossDamage.getBossBattleTypeDamage(entity, source, originalDamage, armorPen);
            event.setAmount(amount);
        } else {
            if (isDamageValid(source)) {
                double rawArmor = getAttributeValue(entity, ModAttributes.RAW_ARMOR);
                double armorPen = getAttributeValue(source.getEntity(), ModAttributes.ARMOR_PENETRATION);
                double armorPenPer = getAttributeValue(source.getEntity(), ModAttributes.ARMOR_PENETRATION) * 0.008;
                double totalArmor = (rawArmor - armorPen) * (1 - armorPenPer);
                if (source.isBypassArmor()) {

                    totalArmor = 0;
                }
                originalDamage = originalDamage + (float) (Math.min(totalArmor, 0F) * (-1));
                totalArmor = Math.max(totalArmor, 0D);
                float k = 2F / (float) originalDamage;
                float amount =  (float) originalDamage / ((k * (float) totalArmor) + 1F);
                event.setAmount(amount);
            }
        }
        if(entity instanceof Player player && source.getEntity() instanceof LivingEntity) {
            player.getCapability(ModCapabilities.ENTAIL_CAPABILITY).orElse(null).getEntail().getHit(originalDamage, event.getAmount(), player, (LivingEntity) source.getEntity());
        }
        if(source.getEntity() instanceof Player player && !(source instanceof DirectEntityDamageSource damageSource && damageSource.getType().equals(DirectEntityDamageSource.Type.HEALTH_DISCHARGE))) {
            player.getCapability(ModCapabilities.ENTAIL_CAPABILITY).orElse(null).getEntail().dealHit(originalDamage, event.getAmount(), player, entity);
        }
    }


    /**
     *  Calculate
     */

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void danoGeral(LivingHurtEvent event){
        Entity causador = event.getSource() != null ? event.getSource().getEntity() : null;
        if(causador == null) return;
        double attributeValue =  (float) getAttributeValue(causador, getAttributeSource(event.getSource()));
        double dano = calculateDamage(causador, event.getAmount(), attributeValue);
        if(causador instanceof Player player){
            dano = player.getCapability(ModCapabilities.ENTAIL_CAPABILITY).orElse(null).getEntail().bonusOnHit(dano, player, event.getEntity());
        }
        event.setAmount((float)dano);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void danoExplosao(LivingHurtEvent event){
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
        DamageSource damageSource = event.getSource();
        if(damageSource.isBypassInvul()) return;
        boolean flag = entity != null && (entity instanceof LivingEntity || (entity instanceof Projectile projectile && projectile.getOwner() instanceof LivingEntity));
        double toDodge = (event.getAmount() / (Math.max(1, livingEntity.getAttributeValue(ModAttributes.AGILITY)) * 0.02 * (flag ? 1 : 10)));
        if(toDodge < 1){
            event.setCanceled(true);
            livingEntity.level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, livingEntity.getSoundSource(), 1.0F, 1.0F);
        }
    }
    @SubscribeEvent
    public static void damageLightning(EntityStruckByLightningEvent event) {
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

    @SuppressWarnings("DO NOT USE")
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

        return null;
    }
    private static double calculateDamage(@Nullable Entity damager, double originalDano, double dano) {
        double efic = getDamageEfficiency(damager, originalDano);
        return (dano * efic) + originalDano;
    }

    private static double getDamageEfficiency(@Nullable Entity damager, double originalDamage){
        double total = 1D;
        if(damager instanceof Player || (damager instanceof Projectile projectile && projectile.getOwner() instanceof  Player)){
            total = originalDamage / 10;
        }

        return total;
    }

}
