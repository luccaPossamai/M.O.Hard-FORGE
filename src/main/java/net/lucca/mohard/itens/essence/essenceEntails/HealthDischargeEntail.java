package net.lucca.mohard.itens.essence.essenceEntails;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HealthDischargeEntail extends EssenceEntail {

    @Override
    public double bonusOnHit(double value, Player player, LivingEntity entityHurt) {
        return value + player.getMaxHealth() * 0.2F;
    }
}
