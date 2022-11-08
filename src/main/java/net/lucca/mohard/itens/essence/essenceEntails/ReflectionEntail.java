package net.lucca.mohard.itens.essence.essenceEntails;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ReflectionEntail extends EssenceEntail {

    @Override
    public void getHit(float originalValue, float finalValue, Player player, LivingEntity entityHurt) {
        double damageBlocked = originalValue - finalValue;
        entityHurt.hurt(DamageSource.thorns(player).bypassArmor(), (float) damageBlocked);
    }
}
