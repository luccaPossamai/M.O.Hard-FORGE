package net.lucca.mohard.entities.illagers.iceIsolator;

import net.lucca.mohard.itens.artifacts.algidAxe.AlgidAxe;
import net.lucca.mohard.itens.artifacts.algidAxe.ThrownAlgidAxe;
import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class IceIsolator extends AbstractIllager implements RangedAttackMob {

    private static final String TAG_BRUNO = "Bruno";
    private static final EntityDataAccessor<Boolean> IS_BRUNO = SynchedEntityData.defineId(IceIsolator.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> RANGED_ATTACK_COOLDOWN = SynchedEntityData.defineId(IceIsolator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MELEE_ATTACK_COOLDOWN = SynchedEntityData.defineId(IceIsolator.class, EntityDataSerializers.INT);

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    public IceIsolator(EntityType<IceIsolator> iceIsolatorEntityType, Level level) {
        super(iceIsolatorEntityType, level);
        this.setCanJoinRaid(true);
        this.setWave(4);

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(3, new AlgidAxeRangedAttackGoal<>(this, 1.0D, 60, 20F));
        this.goalSelector.addGoal(3, new AlgidAxeMeleeAttackGoal<>(this, 1.5D, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableIceIsolatorTargetGoal(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableIceIsolatorTargetGoal(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableIceIsolatorTargetGoal(this, IronGolem.class, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_37870_) {
        super.addAdditionalSaveData(p_37870_);
        p_37870_.putBoolean("Bruno", isBruno());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_37862_) {
        super.readAdditionalSaveData(p_37862_);
        if (p_37862_.contains("Bruno")) {
            setBruno(p_37862_.getBoolean("Bruno"));
        }
    }

    public void setCustomName(@Nullable Component p_34096_) {
        super.setCustomName(p_34096_);
        if (!this.isBruno() && p_34096_ != null && p_34096_.getString().equals(TAG_BRUNO)) {
            setBruno(true);
            this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.FLINT_AND_STEEL, 1));
        }

    }

    public boolean isBruno() {
        return this.entityData.get(IS_BRUNO);
    }

    protected void setBruno(boolean bool){
        this.entityData.set(IS_BRUNO, bool);
    }

    @Override
    public void applyRaidBuffs(int p_37844_, boolean p_37845_) {

    }

    @Override
    public IllagerArmPose getArmPose() {
        if(this.isBruno()) {
            return IllagerArmPose.NEUTRAL;
        } else {
            if (this.isAggressive()) {
                return IllagerArmPose.ATTACKING;
            } else {
                return this.isCelebrating() ? IllagerArmPose.CELEBRATING : IllagerArmPose.CROSSED;
            }
        }
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }

    @Override
    public void setTarget(@Nullable LivingEntity p_21544_) {
        super.setTarget(p_21544_);
        this.setAggressive(p_21544_ != null);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, (double)0.35F)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(ModAttributes.PHYSICAL_DAMAGE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ARMOR, 10)
                .add(ModAttributes.RAW_ARMOR, 10)
                .add(ModAttributes.PROJECTILE_DAMAGE, 0)
                .add(ModAttributes.AGILITY, 10)
                .add(ModAttributes.ARMOR_PENETRATION, 10)
                .add(ModAttributes.MAGIC_DAMAGE, 15);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RANGED_ATTACK_COOLDOWN, 0);
        this.entityData.define(MELEE_ATTACK_COOLDOWN, 0);
        this.entityData.define(IS_BRUNO, false);
    }

    @Override
    public void tick() {
        super.tick();
        if(isRangedAttackOnCooldown()){
            setRangedAttackCooldown(getRangedAttackCooldown() - 1);
        }
        if(isMeleeAttackOnCooldown()){
            setMeleeAttackCooldown(getMeleeAttackCooldown() - 1);
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33282_, DifficultyInstance p_33283_, MobSpawnType p_33284_, @Nullable SpawnGroupData p_33285_, @Nullable CompoundTag p_33286_) {
        RandomSource randomSource = p_33282_.getRandom();
        this.populateDefaultEquipmentSlots(randomSource, p_33283_);
        this.populateDefaultEquipmentEnchantments(randomSource, p_33283_);
        return super.finalizeSpawn(p_33282_, p_33283_, p_33284_, p_33285_, p_33286_);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource source,DifficultyInstance p_34084_) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.ALGID_AXE.get()));
    }

    public boolean isRangedAttackOnCooldown(){
        return getRangedAttackCooldown() > 0;
    }

    public int getRangedAttackCooldown(){
        return this.entityData.get(RANGED_ATTACK_COOLDOWN);
    }

    public void setRangedAttackCooldown(int value){
        this.entityData.set(RANGED_ATTACK_COOLDOWN, value);
    }

    public boolean isMeleeAttackOnCooldown(){
        return getMeleeAttackCooldown() > 0;
    }

    public int getMeleeAttackCooldown(){
        return this.entityData.get(MELEE_ATTACK_COOLDOWN);
    }

    public void setMeleeAttackCooldown(int value){
        this.entityData.set(MELEE_ATTACK_COOLDOWN, value);
    }

    @Override
    public void performRangedAttack(LivingEntity p_32356_, float p_32357_) {
        ItemStack algidAxeStack = this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof AlgidAxe ? this.getItemBySlot(EquipmentSlot.MAINHAND) : new ItemStack(ModItems.ALGID_AXE.get());
        AlgidAxe algidAxe = (AlgidAxe) algidAxeStack.getItem();
        ThrownAlgidAxe thrownAlgidAxe = new ThrownAlgidAxe(this.level, this, algidAxeStack, algidAxe.calculatePower(this), algidAxe.getMagicMultiplier(p_32356_, algidAxeStack));
        double d0 = p_32356_.getX() - this.getX();
        double d1 = p_32356_.getY(0.3333333333333333D) - thrownAlgidAxe.getY();
        double d2 = p_32356_.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        thrownAlgidAxe.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(thrownAlgidAxe);
        setRangedAttackCooldown(200);
        setTarget(null);
    }


}
