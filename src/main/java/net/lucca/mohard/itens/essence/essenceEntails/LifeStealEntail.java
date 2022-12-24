package net.lucca.mohard.itens.essence.essenceEntails;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class LifeStealEntail extends EssenceEntail{

    @Override
    public void dealHit(float originalValue, float finalValue, DamageSource source, Player player, LivingEntity entityHurted) {
        player.heal(Math.min(1, Math.abs(finalValue) / entityHurted.getMaxHealth()) * player.getMaxHealth() / 5);
    }
}
