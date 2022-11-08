package net.lucca.mohard.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ModGlowParticle extends TextureSheetParticle {
    static final Random RANDOM = new Random();
    private final SpriteSet sprites;

    public ModGlowParticle(ClientLevel p_172136_, double p_172137_, double p_172138_, double p_172139_, double p_172140_, double p_172141_, double p_172142_, SpriteSet p_172143_) {
        super(p_172136_, p_172137_, p_172138_, p_172139_, p_172140_, p_172141_, p_172142_);
        this.friction = 0.96F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = p_172143_;
        this.quadSize *= 0.75F;
        this.hasPhysics = false;
        this.setSpriteFromAge(p_172143_);
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public int getLightColor(float p_172146_) {
        float f = ((float)this.age + p_172146_) / (float)this.lifetime;
        f = Mth.clamp(f, 0.0F, 1.0F);
        int i = super.getLightColor(p_172146_);
        int j = i & 255;
        int k = i >> 16 & 255;
        j = j + (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @OnlyIn(Dist.CLIENT)
    public static class VilioProvider implements ParticleProvider<SimpleParticleType> {
        private final double SPEED_FACTOR = 0.01D;
        private final SpriteSet sprite;

        public VilioProvider(SpriteSet p_172238_) {
            this.sprite = p_172238_;
        }

        public Particle createParticle(SimpleParticleType p_172249_, ClientLevel p_172250_, double p_172251_, double p_172252_, double p_172253_, double p_172254_, double p_172255_, double p_172256_) {
            ModGlowParticle glowparticle = new ModGlowParticle(p_172250_, p_172251_, p_172252_, p_172253_, 0.0D, 0.0D, 0.0D, this.sprite);
            glowparticle.setColor( (136.3F / 255F), (70.5F / 255F), (140.1F / 255F));
            glowparticle.setParticleSpeed(p_172254_ * 0.01D / 2.0D, p_172255_ * 0.01D, p_172256_ * 0.01D / 2.0D);
            glowparticle.setLifetime(p_172250_.random.nextInt(30) + 10);
            return glowparticle;
        }
    }
    @OnlyIn(Dist.CLIENT)
    public static class FreezeProvider implements ParticleProvider<SimpleParticleType> {
        private final double SPEED_FACTOR = 0.001D;
        private final SpriteSet sprite;

        public FreezeProvider (SpriteSet p_172238_) {
            this.sprite = p_172238_;
        }

        public Particle createParticle(SimpleParticleType p_172249_, ClientLevel p_172250_, double p_172251_, double p_172252_, double p_172253_, double p_172254_, double p_172255_, double p_172256_) {
            ModGlowParticle glowparticle = new ModGlowParticle(p_172250_, p_172251_, p_172252_, p_172253_, 0.0D, 0.0D, 0.0D, this.sprite);
            glowparticle.setColor( (41F / 255F), (53F / 255F), (74F / 255F));
            glowparticle.setParticleSpeed(p_172254_ * SPEED_FACTOR / 2.0D, p_172255_ * SPEED_FACTOR, p_172256_ * SPEED_FACTOR / 2.0D);
            glowparticle.setLifetime(p_172250_.random.nextInt(30) + 10);
            return glowparticle;
        }
    }
}
