package net.lucca.mohard.itens.essence.essenceEntails;

import net.lucca.mohard.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;

public class GhostRecovery extends EssenceEntail{
    @Override
    public void getHit(float originalValue, float finalValue, Player player, LivingEntity livingEntity) {
        if(player.getHealth() >= 0.9 * player.getMaxHealth()){
            onHitEffects().forEach(player::addEffect);
        }
    }

    private List<MobEffectInstance> onHitEffects(){
        int duration = 100; // 5 - 10 SEC
        return Arrays.asList(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, duration, 0, true, true), new MobEffectInstance(MobEffects.REGENERATION, duration, 0, true, true), new MobEffectInstance(ModEffects.VANISH.get(), duration, 0, true, true), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration / 2, 0, true, true));
    }

    private void pushEnemy(Player player, LivingEntity enemy){
        enemy.setDeltaMovement(enemy.getDeltaMovement().add(new Vec3(enemy.getX() - player.getX(), enemy.getY() + 0.5F - player.getY(), enemy.getZ() - player.getZ())).normalize());
    }

}
