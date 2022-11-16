package net.lucca.mohard.itens.artifacts.algidAxe;

import net.lucca.mohard.init.ModDamageSources;
import net.lucca.mohard.init.ModEntityTypes;
import net.lucca.mohard.init.ModItems;
import net.lucca.mohard.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class ThrownAlgidAxe extends AbstractArrow {

    private ItemStack axe = new ItemStack(ModItems.ALGID_AXE.get());
    private boolean dealtDamage = false;
    private boolean areaFreeze = false;
    private final int power;
    private double damage = 8D;
    private int lastRotation = 0;

    public ThrownAlgidAxe(EntityType<? extends ThrownAlgidAxe> p_37561_, Level p_37562_) {
        super(p_37561_, p_37562_);
        this.power = 1;

    }

    public ThrownAlgidAxe(Level p_37569_, LivingEntity p_37570_, ItemStack p_37571_, int power, double damage) {
        super(ModEntityTypes.ALGID_AXE.get(), p_37570_, p_37569_);
        this.axe = p_37571_.copy();
        this.power = power;
        this.damage = this.damage + damage;

    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
            if(!this.areaFreeze) {
                areaFreeze = true;
                for (int i = -5; i <= 5; i++) {
                    for (int j = -5; j <= 5; j++) {
                        for (int k = -5; k <= 5; k++) {
                            BlockPos pos = new BlockPos(this.getBlockX() + i, this.getBlockY() + j, this.getBlockZ() + k);
                            if (!this.level.getBlockState(pos).is(Blocks.AIR)) {
                                if (this.level.getBlockState(pos.south()).is(Blocks.AIR) ||
                                        this.level.getBlockState(pos.north()).is(Blocks.AIR) ||
                                        this.level.getBlockState(pos.west()).is(Blocks.AIR) ||
                                        this.level.getBlockState(pos.east()).is(Blocks.AIR) ||
                                        this.level.getBlockState(pos.above()).is(Blocks.AIR) ||
                                        this.level.getBlockState(pos.below()).is(Blocks.AIR)) {
                                    ParticleUtils.spawnParticlesOnBlockFaces(this.level, pos, ModParticles.FREEZE_PARTICLE.get(), UniformInt.of(3, 5));

                                }
                            }
                        }
                    }
                }
                for (LivingEntity mob : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5))) {
                    if(mob != this.getOwner()) {
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
                    }
                }

            }
        }
        if(!this.inGround){
            Vec3 vec3 = this.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;
            for (int i = 0; i < 4; ++i) {
                this.level.addParticle(ModParticles.FREEZE_PARTICLE.get(), this.getX() + d5 * (double) i / 4.0D, this.getY() + d6 * (double) i / 4.0D, this.getZ() + d1 * (double) i / 4.0D, -d5, -d6 + 0.2D, -d1);
            }
        }



        super.tick();
    }



    protected ItemStack getPickupItem() {
        return this.axe.copy();
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 p_37575_, Vec3 p_37576_) {
        return this.dealtDamage ? null : super.findHitEntity(p_37575_, p_37576_);
    }

    public void setLastRotation(int lastRotation) {
        this.lastRotation = lastRotation;
    }

    public int getLastRotation() {
        return lastRotation;
    }

    protected void onHitEntity(EntityHitResult p_37573_) {
        Entity entity = p_37573_.getEntity();
        float f = (float) this.damage;


        Entity entity1 = this.getOwner();
        DamageSource damagesource = ModDamageSources.algidAxe(this, this.getOwner());
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.METAL_HIT;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;

        this.playSound(soundevent, f1, 1.0F);
    }


    protected boolean tryPickup(Player p_150196_) {
        return super.tryPickup(p_150196_) || this.isNoPhysics() && this.ownedBy(p_150196_) && p_150196_.getInventory().add(this.getPickupItem());
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.METAL_HIT;
    }

    public boolean inGround(){
        return this.inGround;
    }

    public void playerTouch(Player p_37580_) {
        if (this.ownedBy(p_37580_) || this.getOwner() == null) {
            super.playerTouch(p_37580_);
            if(p_37580_.getCooldowns().isOnCooldown(ModItems.ALGID_AXE.get())){
                p_37580_.getCooldowns().removeCooldown(ModItems.ALGID_AXE.get());
            }
        }

    }

    public void readAdditionalSaveData(CompoundTag p_37578_) {
        super.readAdditionalSaveData(p_37578_);
        if (p_37578_.contains("AlgidAxe", 10)) {
            this.axe = ItemStack.of(p_37578_.getCompound("AlgidAxe"));
        }

        this.areaFreeze = p_37578_.getBoolean("Freeze");

        this.dealtDamage = p_37578_.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(CompoundTag p_37582_) {
        super.addAdditionalSaveData(p_37582_);
        p_37582_.put("AlgidAxe", this.axe.save(new CompoundTag()));
        p_37582_.putBoolean("DealtDamage", this.dealtDamage);
        p_37582_.putBoolean("Freeze", this.areaFreeze);
    }

    public void tickDespawn() {
        if (this.pickup != Pickup.ALLOWED) {
            super.tickDespawn();
        }

    }

    public boolean shouldRender(double p_37588_, double p_37589_, double p_37590_) {
        return true;
    }

    public int getPower() {
        return power;
    }
}
