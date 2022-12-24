package net.lucca.mohard.entities.coldfireball;

import net.lucca.mohard.init.ModDamageSources;
import net.lucca.mohard.init.ModEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class SoulFireball extends AbstractHurtingProjectile {


    protected double damage = 0;

    public SoulFireball(Level level, LivingEntity owner, double x, double y, double z, double damage) {
        super(ModEntityTypes.COLD_FIREBALL.get(), owner, x, y, z, level);
        this.damage = damage;
    }
    public SoulFireball(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level.isClientSide) {
            Entity entity = entityHitResult.getEntity();
            Entity entity1 = this.getOwner();
            entity.hurt(ModDamageSources.fireball(this, entity1), (float) this.damage);
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
        return ParticleTypes.SOUL_FIRE_FLAME;
    }
}
