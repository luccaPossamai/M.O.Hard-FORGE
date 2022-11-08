package net.lucca.mohard.entities.etc.corruptedPlayer;

import net.lucca.mohard.entities.CorruptedMob;
import net.lucca.mohard.util.help.Methods;
import net.lucca.mohard.init.ModAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CorruptedPlayer extends Monster implements CorruptedMob {

    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
        public void stop() {
            super.stop();
            CorruptedPlayer.this.setAggressive(false);
        }

        public void start() {
            super.start();
            CorruptedPlayer.this.setAggressive(true);
        }
    };
    private static final EntityDataAccessor<Optional<UUID>> PLAYER = SynchedEntityData.defineId(CorruptedPlayer.class, EntityDataSerializers.OPTIONAL_UUID);

    public CorruptedPlayer(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.reassessWeaponGoal();
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PLAYER, Optional.ofNullable(null));
    }

    public void setPlayer(@Nullable UUID uuid){
        this.entityData.set(PLAYER, Optional.ofNullable(uuid));
    }

    public UUID getPlayer(){
        return this.entityData.get(PLAYER).orElse(null);
    }
    public @Nullable Player getRealPlayer(){
        return this.entityData.get(PLAYER).orElse(null) == null ? null :
        this.level.getPlayerByUUID(Objects.requireNonNull(this.entityData.get(PLAYER).orElse(null)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(ModAttributes.RAW_ARMOR, 3)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(ModAttributes.PROJECTILE_DAMAGE, 10);
    }

    public void reassessWeaponGoal() {
        if (this.level != null && !this.level.isClientSide) {
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.addGoal(4, this.meleeGoal);
        }
    }

    @Override
    public void setItemSlot(EquipmentSlot p_21416_, ItemStack p_21417_) {
        super.setItemSlot(p_21416_, p_21417_);
        this.reassessWeaponGoal();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {
        this.reassessWeaponGoal();
        return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) {
        super.readAdditionalSaveData(p_21450_);
        this.reassessWeaponGoal();
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource p_33574_, int p_33575_, boolean p_33576_) {
        Entity entity = p_33574_.getEntity();
        for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
            ItemStack itemstack = this.getItemBySlot(equipmentslot);
            if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                this.spawnAtLocation(itemstack);
                this.setItemSlot(equipmentslot, ItemStack.EMPTY);
            }
        }
        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                creeper.increaseDroppedSkulls();
                ItemStack item = new ItemStack(Items.PLAYER_HEAD, 1);
                if(!item.getOrCreateTag().contains("SkullOwner") && this.getRealPlayer() != null){
                    item.getOrCreateTag().putString("SkullOwner", this.getRealPlayer().getName().getString());
                }
                this.spawnAtLocation(item);
            }
        }

    }

    public static void copyAttributes(Player player, CorruptedPlayer corruptedPlayer){
        for(Attribute att : Methods.getAttributes()){
            if(player.getAttribute(att) != null && corruptedPlayer.getAttribute(att) != null){
                double value = player.getAttributeValue(att);
                corruptedPlayer.getAttribute(att).setBaseValue(value);
            }
        }
    }

}
