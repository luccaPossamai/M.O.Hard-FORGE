package net.lucca.mohard.entities.amethystBoulder;

import net.lucca.mohard.entities.GenericHurtingProjectile;
import net.lucca.mohard.init.ModDamageSources;
import net.lucca.mohard.init.ModEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AmethystBoulder extends GenericHurtingProjectile {

    private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(AmethystBoulder.class, EntityDataSerializers.BYTE);


    private static final float ACELERATION = 1.1F;
    private double damage = 5D;
    private byte pierceCount = 0;

    public AmethystBoulder(EntityType<? extends GenericHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public AmethystBoulder(Level p_36834_, LivingEntity owner, Double damage, Vec3 mov, Vec3 pos) {
        super(ModEntityTypes.AMETHYST_BOULDER.get(), pos.x(), pos.y(), pos.z(), mov.x, mov.y, mov.z, p_36834_);
        this.damage = damage;
        this.setOwner(owner);
        this.setRot(owner.getYRot(), owner.getXRot());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PIERCE_LEVEL, (byte)0);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.tickCount % 2 == 0){
            this.xPower *= 0.25F;
            this.yPower *= 0.25F;
            this.zPower *= 0.25F;
        }
        if(this.getDeltaMovement().length() <= 1E-7 && this.tickCount >= 600){
            this.discard();
        }
    }

    public Vec3 getPowerVector(){
        return new Vec3(this.xPower, this.yPower, this.zPower);
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ParticleTypes.WAX_OFF;
    }

    @Override
    public List<LivingEntity> findEntities() {
        return this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.1D, 0.0D, 0.1D)).stream().filter(entity -> entity != this.getOwner()).toList();
    }

    private int remainingPierces(){
        return this.getPierceLevel() + 1 - this.getPierceCount();
    }

    public byte getPierceCount() {
        return pierceCount;
    }

    @Override
    public boolean dealDamage(DamageSource damageSource, LivingEntity livingEntity) {
        float plusDamage = (float) Math.max(0, this.getDamage() * this.getDeltaMovement().length() / 10);
        return livingEntity.hurt(damageSource, this.getDamage() + plusDamage);

    }

    @Override
    public void postDamage(LivingEntity livingEntity) {
        livingEntity.setDeltaMovement(this.getDeltaMovement());
        this.pierceCount++;
        if(this.remainingPierces() <= 0) {
            this.discard();
        }
    }

    public float getDamage(){
        return (float) this.damage;
    }
    @Override
    public DamageSource getDamageSource(Entity entity, Entity owner) {
        return ModDamageSources.amethystBoulder(entity, owner);
    }

    @Override
    public void applyDeltaMovement() {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();
        float f = this.getInertia();
        if (this.isInWater()) {
            for(int i = 0; i < 4; ++i) {
                float f1 = 0.25F;
                this.level.addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25D, d1 - vec3.y * 0.25D, d2 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
            }

            f = 0.8F;
        }

        this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale((double)f));
        this.level.addParticle(this.getTrailParticle(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
        this.setPos(d0, d1, d2);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    protected void updateRotation() {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = vec3.horizontalDistance();
        this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI))));
        this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI))));
    }

    public void setPierceLevel(byte p_36768_) {
        this.entityData.set(PIERCE_LEVEL, p_36768_);
    }
    public byte getPierceLevel() {
        return this.entityData.get(PIERCE_LEVEL);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_36848_) {
        super.addAdditionalSaveData(p_36848_);
        p_36848_.putDouble("MagicDamage", this.damage);
        p_36848_.putByte("PierceLevel", this.getPierceLevel());
        p_36848_.putByte("PierceCount", this.getPierceCount());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_36844_) {
        super.readAdditionalSaveData(p_36844_);
        this.damage = p_36844_.getDouble("MagicDamage");
        this.setPierceLevel(p_36844_.getByte("PierceLevel"));
        this.pierceCount = p_36844_.getByte("PierceCount");
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }
}
