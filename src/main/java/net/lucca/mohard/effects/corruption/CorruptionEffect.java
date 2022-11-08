package net.lucca.mohard.effects.corruption;

import net.lucca.mohard.entities.CorruptedMob;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public class CorruptionEffect extends MobEffect {


    public CorruptionEffect() {
        super(MobEffectCategory.HARMFUL, 7484009);
    }



    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        int k = 30 / (p_76397_2_ + 1);
        return p_76397_1_ % k == 0;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amp) {
        super.applyEffectTick(entity, amp);
        if(!(entity instanceof CorruptedMob) && entity.isAlive()) {
            Vec3 vec = new Vec3((entity.getRandom().nextDouble() * 2) - 1D, (entity.getRandom().nextDouble() * 2) - 1D, (entity.getRandom().nextDouble() * 2) - 1D) ;
            entity.setDeltaMovement(vec.normalize());
            if(!entity.level.isClientSide) {
                if (CorruptionMechanics.convertible(entity.getType())) {
                    if (entity.getRandom().nextInt(100) < (amp + 1)) {
                        Entity entity1;
                        if (entity instanceof Player) {
                            entity1 = CorruptionMechanics.generateCorruptedPlayer((Player) entity);
                        } else {
                            entity1 = CorruptionMechanics.getCorruptedVariant(entity.getType()).create(entity.level);
                        }
                        entity.level.addFreshEntity(entity1);
                        entity.hurt(DamageSource.MAGIC.bypassMagic().bypassArmor(), entity.getMaxHealth() * 99999);
                    }
                }
            }
        }
    }

}
