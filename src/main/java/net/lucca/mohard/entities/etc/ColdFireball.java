package net.lucca.mohard.entities.etc;

import net.lucca.mohard.init.ModDamageSources;
import net.lucca.mohard.init.ModEffects;
import net.lucca.mohard.init.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ColdFireball extends AbstractHurtingProjectile {


    protected double damage = 0;

    public ColdFireball(Level level, LivingEntity owner, double x, double y, double z, double damage) {
        super(ModEntityTypes.COLD_FIREBALL.get(), owner, x, y, z, level);
        this.damage = damage;
    }
    public ColdFireball(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }


    @Override
    protected void onHitBlock(@NotNull BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!this.level.isClientSide) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, entity)) {
                BlockPos blockpos = hitResult.getBlockPos().relative(hitResult.getDirection());
                this.setDeltaMovement(this.getDeltaMovement().multiply(-1, -1, -1));
            }

        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level.isClientSide) {
            Entity entity = entityHitResult.getEntity();
            Entity entity1 = this.getOwner();

            if(entity instanceof LivingEntity livingEntity){
                livingEntity.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 20));
            }
            entity.hurt(ModDamageSources.fireball(this, entity1), (float) this.damage);


        }
    }

    @Override
    protected void onHit(@NotNull HitResult p_37260_) {
        super.onHit(p_37260_);
        if (!this.level.isClientSide) {
            TargetingConditions alertableTargeting = TargetingConditions.forCombat().range(3.0D);
            for(LivingEntity livingEntity : this.level.getNearbyEntities(LivingEntity.class, alertableTargeting, null, this.getBoundingBox().inflate(3))){
                double xD = livingEntity.getX() - this.getX();
                double yD = livingEntity.getY() - this.getY();
                double zD = livingEntity.getZ() - this.getZ();
                Vec3 vec3 = new Vec3(xD, yD, zD).normalize();
                livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(vec3));
            }
            this.discard();
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource p_36839_, float p_36840_) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putDouble("Damage", this.damage);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.damage = tag.getDouble("Damage");
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BLUE_ICE.defaultBlockState());
    }
}
