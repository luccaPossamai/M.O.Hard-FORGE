package net.lucca.mohard.itens.essence.essenceEntails;

import net.lucca.mohard.init.ModEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MagicInspirationEntail extends EssenceEntail {

    @Override
    public void dealHit(float originalValue, float finalValue, DamageSource damageSource, Player player, LivingEntity entityHurted) {
        if(damageSource.isMagic()){
            int i = player.hasEffect(ModEffects.INSPIRATION.get()) ? player.getEffect(ModEffects.INSPIRATION.get()).getAmplifier() : -1;
            player.addEffect(new MobEffectInstance(ModEffects.INSPIRATION.get(), 200 + 100 * i, Math.min(i + 1, 4), false, true));
        }
    }
}
