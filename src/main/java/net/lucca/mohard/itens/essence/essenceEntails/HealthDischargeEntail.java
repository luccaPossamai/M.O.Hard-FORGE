package net.lucca.mohard.itens.essence.essenceEntails;

import net.lucca.mohard.init.ModDamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HealthDischargeEntail extends EssenceEntail {

    @Override
    public void dealHit(float originalValue, float finalValue, Player player, LivingEntity entityHurted) {
        if(player.getHealth() >= player.getMaxHealth() * 0.5F){
            float addDamage = player.getMaxHealth() * 0.1F;
            player.hurt(ModDamageSources.healthDischarge(player, player), addDamage);
        }
    }
}
