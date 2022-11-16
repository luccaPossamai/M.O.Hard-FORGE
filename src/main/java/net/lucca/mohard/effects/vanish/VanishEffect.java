package net.lucca.mohard.effects.vanish;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.Random;

public class VanishEffect extends MobEffect {

    public VanishEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity p_19462_, @Nullable Entity p_19463_, LivingEntity p_19464_, int p_19465_, double p_19466_) {
        super.applyInstantenousEffect(p_19462_, p_19463_, p_19464_, p_19465_, p_19466_);
        Random rand = new Random();
        p_19464_.level.addParticle(ParticleTypes.SMOKE, p_19464_.getX(), p_19464_.getY() + 1, p_19464_.getZ(), rand.nextDouble(), rand.nextDouble(), rand.nextDouble());

    }
}
