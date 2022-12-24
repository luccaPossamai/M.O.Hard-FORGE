package net.lucca.mohard.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;

import java.util.Random;


public class IncinerationEffect extends MobEffect {


    public IncinerationEffect() {
        super(MobEffectCategory.HARMFUL, 16167425);
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        int k = 30 / (p_76397_2_ + 1);
        return k == 0 || p_76397_1_ % k == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int level) {
        livingEntity.hurt(DamageSource.ON_FIRE, livingEntity.getMaxHealth() * (5 / 100F));
        generateParticle(livingEntity.level, new Random(), livingEntity);
    }

    private static void generateParticle(Level world, Random random, LivingEntity livingEntity) {
        int j = random.nextInt(2) * 2 - 1;
        int k = random.nextInt(2) * 2 - 1;
        double x = livingEntity.getX() - (Math.random() - 0.5);
        double y = livingEntity.getY()  + Math.random();
        double z = livingEntity.getZ() - (Math.random() - 0.5);
        double r0 = (double) (random.nextFloat() * (float) j);
        double r1 = ((double) random.nextFloat() - 0.5D) * 0.125D;
        double r2 = (double) (random.nextFloat() * (float) k);
        world.addParticle(ParticleTypes.LAVA, x, y, z, r0, r1, r2);

    }

}
