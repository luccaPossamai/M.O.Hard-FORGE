package net.lucca.mohard.effects;

import net.lucca.mohard.init.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class StunningEffect extends MobEffect {


    public StunningEffect() {
        super(MobEffectCategory.HARMFUL, 4411786);
    }


    @SubscribeEvent
    public static void naoPula(LivingEvent.LivingJumpEvent event){
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.hasEffect(ModEffects.STUN.get())) {
            double y = livingEntity.getDeltaMovement().y();
            livingEntity.setDeltaMovement(0, y - (livingEntity.getJumpBoostPower() + getJumpPower(livingEntity)), 0);

        }
    }

    public static float getJumpPower(LivingEntity entity) {
        return 0.42F * getBlockJumpFactor(entity);
    }

    public static float getBlockJumpFactor(LivingEntity entity) {
        float f = entity.level.getBlockState(entity.blockPosition()).getBlock().getJumpFactor();
        float f1 = entity.level.getBlockState(getBlockPosBelowThatAffectsMyMovement(entity)).getBlock().getJumpFactor();
        return (double)f == 1.0D ? f1 : f;
    }

    public static BlockPos getBlockPosBelowThatAffectsMyMovement(LivingEntity entity) {
        return new BlockPos(entity.position().x, entity.getBoundingBox().minY - 0.5000001D, entity.position().z);
    }
}
