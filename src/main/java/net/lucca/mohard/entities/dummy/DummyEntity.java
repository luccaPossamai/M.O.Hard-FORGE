package net.lucca.mohard.entities.dummy;

import net.lucca.mohard.init.ModAttributes;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class DummyEntity extends LivingEntity {

    private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
    private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
    private static final EntityDataAccessor<String> DATA_TYPE = SynchedEntityData.defineId(DummyEntity.class, EntityDataSerializers.STRING);


    public DummyEntity(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
        this.setDummyType(DummyType.values()[(int) Math.round(Math.random() * (DummyType.values().length - 1))]);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return this.armorItems;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 100).add(Attributes.MOVEMENT_SPEED, 0.35D).add(ModAttributes.MAGIC_DAMAGE, 10);
    }


    @Override
    public ItemStack getItemBySlot(EquipmentSlot p_31612_) {
        switch(p_31612_.getType()) {
            case HAND:
                return this.handItems.get(p_31612_.getIndex());
            case ARMOR:
                return this.armorItems.get(p_31612_.getIndex());
            default:
                return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemSlot(EquipmentSlot p_31584_, ItemStack p_31585_) {
        this.verifyEquippedItem(p_31585_);
        switch(p_31584_.getType()) {
            case HAND:
                this.onEquipItem(p_31584_, this.handItems.set(p_31584_.getIndex(), p_31585_), p_31585_);
                break;
            case ARMOR:
                this.onEquipItem(p_31584_, this.armorItems.set(p_31584_.getIndex(), p_31585_), p_31585_);
        }

    }

    protected DummyType getDummyType(){
        return DummyType.byType(this.entityData.get(DATA_TYPE));
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        if (!this.level.isClientSide && !this.isRemoved()) {
            if (p_21016_ != null ? p_21016_.getEntity() instanceof Player : false) {
                return false;
            }
            return super.hurt(p_21016_, p_21017_);
        }
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_31619_) {
        super.addAdditionalSaveData(p_31619_);
        ListTag listtag = new ListTag();

        for(ItemStack itemstack : this.armorItems) {
            CompoundTag compoundtag = new CompoundTag();
            if (!itemstack.isEmpty()) {
                itemstack.save(compoundtag);
            }
            listtag.add(compoundtag);
        }

        p_31619_.put("ArmorItems", listtag);
        ListTag listtag1 = new ListTag();

        for(ItemStack itemstack1 : this.handItems) {
            CompoundTag compoundtag1 = new CompoundTag();
            if (!itemstack1.isEmpty()) {
                itemstack1.save(compoundtag1);
            }

            listtag1.add(compoundtag1);
        }

        p_31619_.put("HandItems", listtag1);
        p_31619_.putBoolean("Invisible", this.isInvisible());

        p_31619_.putInt("DummyType", this.getDummyType().id);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_31600_) {
        super.readAdditionalSaveData(p_31600_);
        if (p_31600_.contains("ArmorItems", 9)) {
            ListTag listtag = p_31600_.getList("ArmorItems", 10);

            for(int i = 0; i < this.armorItems.size(); ++i) {
                this.armorItems.set(i, ItemStack.of(listtag.getCompound(i)));
            }
        }

        if (p_31600_.contains("HandItems", 9)) {
            ListTag listtag1 = p_31600_.getList("HandItems", 10);

            for(int j = 0; j < this.handItems.size(); ++j) {
                this.handItems.set(j, ItemStack.of(listtag1.getCompound(j)));
            }
        }

        this.setInvisible(p_31600_.getBoolean("Invisible"));
    }

    @Override
    public boolean isCustomNameVisible() {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE, DummyType.WHEAT.type);
    }

    private void setDummyType(DummyType p_28929_) {
        this.entityData.set(DATA_TYPE, p_28929_.type);
    }

    public static enum DummyType {
        WHEAT("wheat", 0),
        PUMPKIN("pumpkin", 1);

        final String type;
        final int id;

        private DummyType(String p_28967_, int id){
            this.type = p_28967_;
            this.id = id;
        }

        static DummyType byType(String p_28977_) {
            for(DummyType dummyType : values()) {
                if (dummyType.type.equals(p_28977_)) {
                    return dummyType;
                }
            }

            return WHEAT;
        }
    }
}
