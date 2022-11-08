package net.lucca.mohard.entities.corrupter;

import net.lucca.mohard.effects.corruption.CorruptionMechanics;
import net.lucca.mohard.entities.CorruptedMob;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.lucca.mohard.itens.essenceBundle.EssenceBundleItem;
import net.lucca.mohard.init.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Predicate;


public class Corrupter extends Monster implements RangedAttackMob, CorruptedMob {

    public Corrupter(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CorrupterAvoidEntityGoal<>(this, Player.class, 16.0F, 0.8D, 1.1D));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 60, 10.0F));
        this.goalSelector.addGoal(3, new CorrupterTemptGoal(this, 0.6D, Ingredient.of(ModTags.Items.ESSENCE), false));
        this.goalSelector.addGoal(3, new CorrupterTemptGoal(this, 0.6D, Ingredient.of(ModItems.ESSENCE_BUNDLE.get()), false));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.35D));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 100).add(Attributes.MOVEMENT_SPEED, 0.25D).add(ModAttributes.MAGIC_DAMAGE, 10);
    }

    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            double d0 = this.getMoveControl().getSpeedModifier();
            if (d0 == 0.6D) {
                this.setPose(Pose.CROUCHING);
                this.setSprinting(false);
            } else if (d0 == 1.33D) {
                this.setPose(Pose.STANDING);
                this.setSprinting(true);
            } else {
                this.setPose(Pose.STANDING);
                this.setSprinting(false);
            }
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
        }

    }

    @Override
    public void aiStep() {
        super.aiStep();
        if(!this.level.isClientSide) {
            LivingEntity target = this.getTarget();
            if (target == null) {
                this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            } else {
                this.setItemInHand(InteractionHand.MAIN_HAND, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.CORRUPTION_POTION.get()));
            }
        }
    }

    @Override
    public void performRangedAttack(LivingEntity p_34143_, float p_34144_) {
        if(this.getTarget() != null) {
            if (!(this.getTarget().getActiveEffectsMap().containsKey(ModEffects.CORRUPTION.get()))) {
                Vec3 vec3 = p_34143_.getDeltaMovement();
                double d0 = p_34143_.getX() + vec3.x - this.getX();
                double d1 = p_34143_.getEyeY() - (double) 1.1F - this.getY();
                double d2 = p_34143_.getZ() + vec3.z - this.getZ();

                double d3 = Math.sqrt(d0 * d0 + d2 * d2);

                Potion potion = ModPotions.CORRUPTION_POTION.get();

                ThrownPotion thrownpotion = new ThrownPotion(this.level, this);
                thrownpotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
                thrownpotion.setXRot(thrownpotion.getXRot() - -20.0F);
                thrownpotion.shoot(d0, d1 + d3 * 0.2D, d2, 0.75F, 8.0F);
                if (!this.isSilent()) {
                    this.level.playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW, this.getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
                }

                this.level.addFreshEntity(thrownpotion);
                this.setTarget(null);
            }
        }
    }

    @Override
    public void setTarget(@Nullable LivingEntity p_21544_) {
        if(p_21544_ != null) {
            if (p_21544_.hasEffect(ModEffects.CORRUPTION.get()) || CorruptionMechanics.converted(p_21544_)) {
                super.setTarget(null);
                return;
            }
        }
        super.setTarget(p_21544_);
    }


    protected boolean stoleEssence(){
        ItemStack item = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return item.getItem() instanceof EssenceItem || item.getItem() instanceof EssenceBundleItem;

    }

    @Override
    protected void dropCustomDeathLoot(DamageSource p_21385_, int p_21386_, boolean p_21387_) {
        super.dropCustomDeathLoot(p_21385_, p_21386_, p_21387_);
        ItemStack stack = this.getItemInHand(InteractionHand.MAIN_HAND);
        if(this.stoleEssence()){
            this.spawnAtLocation(stack);
        }

    }

}
