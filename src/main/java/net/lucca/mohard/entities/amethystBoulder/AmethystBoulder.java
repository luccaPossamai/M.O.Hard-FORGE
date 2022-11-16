package net.lucca.mohard.entities.amethystBoulder;

import net.lucca.mohard.init.ModDamageSources;
import net.lucca.mohard.init.ModEntityTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class AmethystBoulder extends AbstractArrow {

    private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(AmethystBoulder.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ID_KICKS = SynchedEntityData.defineId(AmethystBoulder.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ID_LAST_KICK_TICK = SynchedEntityData.defineId(AmethystBoulder.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> ID_TICK_OF_LAST_DAMAGE = SynchedEntityData.defineId(AmethystBoulder.class, EntityDataSerializers.INT);
    private final int DAMAGE_TIME = 5;
    private final int MAX_SIZE = 3;
    private final int MIN_SIZE = 1;
    private double damage = 2D;
    private final int MAX_KICKS = 6;

    public AmethystBoulder(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public AmethystBoulder(Level p_36722_, LivingEntity owner, double damage) {
        super(ModEntityTypes.AMETHYST_BOULDER.get(), owner, p_36722_);
        this.damage = this.damage + damage;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_SIZE, 1);
        this.entityData.define(ID_KICKS, 0);
        this.entityData.define(ID_LAST_KICK_TICK, 0);
        this.entityData.define(ID_TICK_OF_LAST_DAMAGE, 0);
    }

    public int getRemainingKicks() {
        return (MAX_KICKS / this.getSize())- this.getKicks();
    }

    private boolean canDamage(){
        return this.tickCount - this.entityData.get(ID_TICK_OF_LAST_DAMAGE) >= DAMAGE_TIME;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.tickCount >= 1200 / this.getSize()){
            if(!this.shrink()) {
                this.discard();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        if (this.getRemainingKicks() <= 0) {
            if (!this.shrink()) {
                super.onHitBlock(p_37258_);
                return;
            }
        }
        this.setKicks(this.getKicks() + 1);
        this.setLastKickTick(this.tickCount);
        this.setDeltaMovement(reflect(p_37258_.getDirection().getAxis(), this.getDeltaMovement()));
        this.playSound(SoundEvents.AMETHYST_BLOCK_PLACE, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        Vec3 vec3 = this.getDeltaMovement();
        this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AMETHYST_BLOCK.defaultBlockState()).setPos(this.blockPosition()), this.getX() + (this.random.nextDouble() - 0.5D) * (double) this.getDimensions(Pose.LONG_JUMPING).width, this.getY() + 0.1D, this.getZ() + (this.random.nextDouble() - 0.5D) * (double) this.getDimensions(Pose.LONG_JUMPING).width, vec3.x * -4.0D, 1.5D, vec3.z * -4.0D);
    }

    public int getKicks() {
        return this.entityData.get(ID_KICKS);
    }

    public void setKicks(int i){
        this.entityData.set(ID_KICKS, Mth.clamp(i, 0, this.MAX_KICKS));
    }


    public void setLastDamageTick(int tick) {
        this.entityData.set(ID_LAST_KICK_TICK, tick);
    }


    public int getLastKickTick() {
        return this.entityData.get(ID_LAST_KICK_TICK);
    }

    public void setLastKickTick(int i){
        this.entityData.set(ID_LAST_KICK_TICK, i);
    }

    public int getTicksSinceLastKick(){
        return this.getRemainingKicks() <= 0 ? 0 : this.tickCount - this.getLastKickTick();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_36761_) {
        super.readAdditionalSaveData(p_36761_);
        this.setKicks(p_36761_.getInt("CurrentKicks"));
        this.setLastKickTick(p_36761_.getInt("LastKickTick"));
        this.setSize(p_36761_.getInt("Size") + 1);
    }



    @Override
    public void addAdditionalSaveData(CompoundTag p_36772_) {
        super.addAdditionalSaveData(p_36772_);
        p_36772_.putInt("Size", this.getSize() - 1);
        p_36772_.putInt("CurrentKicks", this.getKicks());
        p_36772_.putInt("LastKickTick", this.getLastKickTick());
    }

    public int getSize() {
        return this.entityData.get(ID_SIZE);
    }

    public void setSize(int size) {
        this.entityData.set(ID_SIZE, Mth.clamp(size, MIN_SIZE, MAX_SIZE));
        this.reapplyPosition();
        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDimensions(Pose p_33597_) {
        return super.getDimensions(p_33597_).scale((float)this.getSize());
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.SLIME_BALL, 1);
    }

    @Override
    public void shootFromRotation(Entity p_37252_, float p_37253_, float p_37254_, float p_37255_, float p_37256_, float p_37257_) {
        float f = -Mth.sin(p_37254_ * ((float) Math.PI / 180F)) * Mth.cos(p_37253_ * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((p_37253_ + p_37255_) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(p_37254_ * ((float) Math.PI / 180F)) * Mth.cos(p_37253_ * ((float) Math.PI / 180F));
        this.shoot((double) 3 * f, (double) f1, (double) 3 * f2, p_37256_, p_37257_);
        Vec3 vec3 = p_37252_.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, p_37252_.isOnGround() ? 0.0D : vec3.y, vec3.z));
    }

    @Override
    public void shoot(double x, double y, double z, float p_36778_, float p_36779_) {
        Vec3 vec3 = (new Vec3(x, y, z)).add(this.random.triangle(0.0D, 0.0172275D * (double) p_36779_), this.random.triangle(0.0D, 0.0172275D * (double) p_36779_), this.random.triangle(0.0D, 0.0172275D * (double) p_36779_)).scale((double) p_36778_);
        this.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
        this.setXRot((float) (Mth.atan2(vec3.y, d0) * (double) (180F / (float) Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {
        if(this.canDamage()) {
            this.setKicks(this.getKicks() + 1);
            this.setLastKickTick(this.tickCount);
            this.setLastDamageTick(this.tickCount);
            Vec3 vec3 = this.getDeltaMovement();
            Entity entity = p_37259_.getEntity();
            entity.hurt(ModDamageSources.fireball(this, this.getOwner()), (float) this.damage);
            entity.setDeltaMovement(entity.getDeltaMovement().add(vec3));
            if (!this.shrink()) {
                this.discard();
            }
            this.setDeltaMovement(reflectAndKick());
        }
    }

    private boolean shrink(){
        int size = this.getSize();
        Vec3 vec3 = this.getDeltaMovement();
        this.playSound(SoundEvents.AMETHYST_BLOCK_BREAK, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        for(int i = 0; i < 10; i++) {
            this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AMETHYST_BLOCK.defaultBlockState()).setPos(this.blockPosition()), this.getX() + (this.random.nextDouble() - 0.5D) * (double) this.getDimensions(Pose.LONG_JUMPING).width, this.getY() + 0.1D, this.getZ() + (this.random.nextDouble() - 0.5D) * (double) this.getDimensions(Pose.LONG_JUMPING).width, vec3.x * -4.0D, 1.5D, vec3.z * -4.0D);
        }
        if(size - 1 > 0){
            this.setKicks(0);
            this.setSize(size - 1);
            return true;
        }
        return false;
    }


    private Vec3 reflect(Direction.Axis axis, Vec3 vec3){
        double d0 = axis == Direction.Axis.X ? -vec3.x : vec3.x;
        double d1 = axis == Direction.Axis.Y ? -vec3.y : vec3.y;
        double d2 = axis == Direction.Axis.Z ? -vec3.z : vec3.z;
        return new Vec3(d0, d1, d2);
    }

    private Vec3 reflectAndKick(){
        return new Vec3(-this.getX(), Math.abs(this.getY()), -this.getZ());
    }

    @Override
    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_33609_) {
        if (ID_SIZE.equals(p_33609_)) {
            this.refreshDimensions();
            this.setYRot(this.yRotO);
            if (this.isInWater() && this.random.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.onSyncedDataUpdated(p_33609_);
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.AMETHYST_BLOCK_BREAK;
    }

    public boolean inGround(){
        return this.inGround;
    }

}
