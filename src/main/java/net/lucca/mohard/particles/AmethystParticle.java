package net.lucca.mohard.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AmethystParticle extends TextureSheetParticle {

    private final SpriteSet sprites;
    protected AmethystParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double p_108332_, double p_108333_, double p_108334_, SpriteSet sprites) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
        this.friction = 0.6F;
        this.sprites = sprites;
        this.quadSize *= 10F;
        this.gravity = 0;
        this.lifetime = 10;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(sprites);
        fadeOut();
    }

    private void fadeOut(){
        this.alpha = (-(1F / (4 * this.lifetime)) * this.age + 1);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_105793_) {
            this.sprite = p_105793_;
        }

        public Particle createParticle(SimpleParticleType p_105804_, ClientLevel p_105805_, double p_105806_, double p_105807_, double p_105808_, double p_105809_, double p_105810_, double p_105811_) {
            return new AmethystParticle(p_105805_, p_105806_, p_105807_, p_105808_, p_105809_, p_105810_, p_105811_, this.sprite);
        }
    }
}
