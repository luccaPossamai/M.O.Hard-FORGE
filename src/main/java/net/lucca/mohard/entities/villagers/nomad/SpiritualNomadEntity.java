package net.lucca.mohard.entities.villagers.nomad;

import net.lucca.mohard.entities.villagers.nomad.goals.ArmsGoal;
import net.lucca.mohard.entities.villagers.nomad.goals.HealGoal;
import net.lucca.mohard.entities.villagers.nomad.goals.SpellInEnemy;
import net.lucca.mohard.entities.villagers.nomad.goals.VanishGoal;
import net.lucca.mohard.entities.villagers.nomad.spells.NomadSpells;
import net.lucca.mohard.init.ModAttributes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;

public class SpiritualNomadEntity extends AbstractVillager {

    private static final EntityDataAccessor<Boolean> DATA_IS_DENYING = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> DATA_IS_CROSSING = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_CASTING = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_VANISHING = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> THUNDER_COOLDOWN = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FIREBALL_COOLDOWN = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ARROWS_COOLDOWN = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FIELD_COOLDOWN = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HEAL_COOLDOWN = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> CASTING_SPELL = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.INT);


    private static final EntityDataAccessor<Integer> TIRED_COOLDOWN = SynchedEntityData.defineId(SpiritualNomadEntity.class, EntityDataSerializers.INT);

    public SpiritualNomadEntity(EntityType<? extends AbstractVillager> p_i50185_1_, Level p_i50185_2_) {
        super(p_i50185_1_, p_i50185_2_);

    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0 , new VanishGoal(this, 100));
        this.goalSelector.addGoal(1, new SpellInEnemy<>(this, Player.class, 16.0F));
        this.goalSelector.addGoal(2, new SpellInEnemy<>(this, Zombie.class, 16.0F));
        this.goalSelector.addGoal(2, new SpellInEnemy<>(this, AbstractIllager.class, 16.0F));
        this.goalSelector.addGoal(2, new SpellInEnemy<>(this, Zoglin.class, 16.0F));
        this.goalSelector.addGoal(3, new HealGoal<>(this));
        this.goalSelector.addGoal(5, new ArmsGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.MAX_HEALTH, 500.0D)
                .add(Attributes.ATTACK_DAMAGE, 50.0D)
                .add(ModAttributes.PHYSICAL_DAMAGE, 80.0D)
                .add(Attributes.FOLLOW_RANGE, 100.0D)
                .add(ModAttributes.RAW_ARMOR, 100)
                .add(ModAttributes.PROJECTILE_DAMAGE, 100)
                .add(ModAttributes.AGILITY, 100)
                .add(ModAttributes.ARMOR_PENETRATION, 100)
                .add(ModAttributes.MAGIC_DAMAGE, 100)
                .add(ModAttributes.INTELLECT, 100);
    }


    @Override
    protected void rewardTradeXp(MerchantOffer p_213713_1_) {

    }

    @Override
    protected void updateTrades() {

    }

    public boolean isCasting(){
        return this.entityData.get(DATA_IS_CASTING);
    }

    public boolean isCrossing(){
        return this.entityData.get(DATA_IS_CROSSING);
    }

    public boolean isDenying(){
        return this.entityData.get(DATA_IS_DENYING);
    }
    public boolean isVanishing(){
        return this.entityData.get(DATA_IS_VANISHING);
    }

    public void setVanishing(boolean bool){
        resetArmStates();
        this.entityData.set(DATA_IS_VANISHING, bool);
    }

    public void setCasting(boolean bool){
        resetArmStates();
        this.entityData.set(DATA_IS_CASTING, bool);
    }

    public void setCrossing(boolean bool){
        resetArmStates();
        this.entityData.set(DATA_IS_CROSSING, bool);
    }

    public void setDenying(boolean bool){
        this.entityData.set(DATA_IS_DENYING, bool);
    }

    public void resetArmStates(){
        this.entityData.set(DATA_IS_CASTING, false);
        this.entityData.set(DATA_IS_CROSSING, false);
        this.entityData.set(DATA_IS_VANISHING, false);
    }


    public SpiritualNomadState getArmState(){
        return this.isCasting() ? SpiritualNomadState.CASTING :
               this.isVanishing() ? SpiritualNomadState.VANISHING :
               this.isCrossing() ? SpiritualNomadState.CROSSED :
               SpiritualNomadState.NORMAL;
    }


    @Override
    public void tick() {
        super.tick();
        for(NomadSpells spells : this.getSpellsInCooldown()){
            reduceCooldown(getDataBySpell(spells), 1);
        }
        if(isTired()) reduceCooldown(TIRED_COOLDOWN, 1);
    }

    public void reduceCooldown(EntityDataAccessor<Integer> data, int count){
        this.getEntityData().set(data, this.getEntityData().get(data) - count);
    }

    public void setCooldown(EntityDataAccessor<Integer> data){
        this.getEntityData().set(data, this.getSpellByData(data) != null ? this.getSpellByData(data).getCooldown() : 500);
    }

    public void setTiredCooldown(){
        this.getEntityData().set(TIRED_COOLDOWN, 50);
    }

    @Nullable
    public NomadSpells getSpellByData(EntityDataAccessor<Integer> data){
        if(data.equals(THUNDER_COOLDOWN)) return NomadSpells.THUNDER;
        if(data.equals(FIREBALL_COOLDOWN)) return NomadSpells.FIREBALL;
        if(data.equals(ARROWS_COOLDOWN)) return NomadSpells.ARROWS;
        if(data.equals(FIELD_COOLDOWN)) return NomadSpells.FIELD;
        if(data.equals(HEAL_COOLDOWN)) return NomadSpells.HEAL;

        return null;
    }

    @Nullable
    public EntityDataAccessor<Integer> getDataBySpell(NomadSpells spells){
        if(spells.equals(NomadSpells.THUNDER)) return THUNDER_COOLDOWN;
        if(spells.equals(NomadSpells.FIREBALL)) return FIREBALL_COOLDOWN;
        if(spells.equals(NomadSpells.ARROWS)) return ARROWS_COOLDOWN;
        if(spells.equals(NomadSpells.FIELD)) return FIELD_COOLDOWN;
        if(spells.equals(NomadSpells.HEAL)) return HEAL_COOLDOWN;

        return null;
    }


    public List<NomadSpells> getFreeSpells(boolean aggressive){
        List<NomadSpells> nomadSpells = new ArrayList<>();
        for(NomadSpells spells : NomadSpells.values()){
            if(isSpellFree(spells) || spells.isAggressive() == aggressive) nomadSpells.add(spells);
        }

        return nomadSpells;
    }

    public List<NomadSpells> getSpellsInCooldown(){
        List<NomadSpells> nomadSpells = new ArrayList<>();
        for(NomadSpells spells : NomadSpells.values()){
            if(!isSpellFree(spells)) nomadSpells.add(spells);
        }

        return nomadSpells;
    }

    

    public boolean isTired(){
        return this.getEntityData().get(TIRED_COOLDOWN) > 0;
    }

    public boolean isSpellFree(NomadSpells spells){
        return this.getEntityData().get(getDataBySpell(spells)) <= 0;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_IS_CASTING, false);
        this.getEntityData().define(DATA_IS_VANISHING, false);
        this.getEntityData().define(DATA_IS_DENYING, false);
        this.getEntityData().define(DATA_IS_CROSSING, false);
        for(NomadSpells spells : NomadSpells.values()){
            this.getEntityData().define(getDataBySpell(spells), 0);
        }
        this.getEntityData().define(CASTING_SPELL, 0);
        this.getEntityData().define(TIRED_COOLDOWN, 0);
    }

    public NomadSpells getCastingSpell() {
        return this.getById(getEntityData().get(CASTING_SPELL));
    }

    public void setCastingSpell(NomadSpells spell) {
        if(spell != null) {
            getEntityData().set(CASTING_SPELL, spell.getId());
        } else {
            getEntityData().set(CASTING_SPELL, 0);

        }
    }


    @Nullable
    public NomadSpells getById(int idInteger){
        if(idInteger > NomadSpells.FIELD.getSpells().size() || idInteger <= 0) return null;
        return NomadSpells.FIELD.getSpells().get(idInteger - 1);
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide) {
            for(int u = 0; u < 2; ++u) {
                this.level.addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
                if(this.getCastingSpell() != null) {
                    Random random = new Random();
                    if(this.getCastingSpell().equals(NomadSpells.FIELD)) {
                        for(int i = -2; i <= 2; ++i) {
                            for (int j = -2; j <= 2; ++j) {
                                for (int k = -2; k <= 2; ++k) {
                                    if (i > -2 && i < 2 && j == -1) {
                                        j = 2;
                                    }

                                    if (random.nextInt(16) == 0) {
                                        this.level.addParticle(ParticleTypes.ENCHANT, this.getX() + 0.5D, this.getY() + 2.0D, this.getZ() + 0.5D, (double) ((float) i + random.nextFloat()) - 0.5D, (double) ((float) k - random.nextFloat() - 0.5F), (double) ((float) j + random.nextFloat()) - 0.5D);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        super.aiStep();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_, MobSpawnType p_213386_3_, @Nullable SpawnGroupData p_213386_4_, @Nullable CompoundTag p_213386_5_) {
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void addParticlesAroundSelf(ParticleOptions p_213718_1_) {
        super.addParticlesAroundSelf(p_213718_1_);
    }
}

