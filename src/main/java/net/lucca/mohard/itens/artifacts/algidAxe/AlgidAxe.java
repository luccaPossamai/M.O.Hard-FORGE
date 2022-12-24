package net.lucca.mohard.itens.artifacts.algidAxe;

import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.init.ModDamageSources;
import net.lucca.mohard.itens.artifacts.GenericArtifact;
import net.lucca.mohard.itens.artifacts.MeleeCrittableArtifact;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;


public class AlgidAxe extends GenericArtifact implements MeleeCrittableArtifact {

    private final TagKey<Block> blocks = BlockTags.MINEABLE_WITH_AXE;

    protected final float speed = 8;
    public AlgidAxe(Properties p_i48487_1_) {
        super(p_i48487_1_, 2.25, 8 * 20, true);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 200;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack p_41134_, ItemStack p_41135_) {
        return p_41135_.is(Items.BLUE_ICE);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack item, @NotNull Level world, @NotNull LivingEntity entity, int tick) {
        if (entity instanceof Player player) {
            int i = this.getUseDuration(item) - tick;
            if (i >= 10 && this.canCast(player, item)) {
                this.generateCooldown(player, item);
                if (!world.isClientSide) {
                    this.generateCooldown(player, item, this.getBaseCooldown() / 3);
                    this.hurtItem(player, item);
                    double dano = player.getAttributeValue(ModAttributes.MAGIC_DAMAGE);
                    ThrownAlgidAxe thrownAxe = new ThrownAlgidAxe(world, player, item, calculatePower(entity), dano * this.getMagicMultiplier(entity, item));
                    thrownAxe.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F , 1.0F);
                    if (player.getAbilities().instabuild) {
                        thrownAxe.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }
                    world.addFreshEntity(thrownAxe);
                    world.playSound((Player) null, thrownAxe, SoundEvents.ARMOR_EQUIP_IRON, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!player.getAbilities().instabuild) {
                        player.getInventory().removeItem(item);
                    }
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }

        }
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(canCast(player, itemstack)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        boolean flag0 = this.canCast(player, stack);
        if (flag0 && player instanceof ServerPlayer && this.isEnabled(stack)) {
            this.generateCooldown(player, stack);
            DamageSource damage = ModDamageSources.algidAxe(player, player);
            double dano = player.getAttributeValue(ModAttributes.MAGIC_DAMAGE);
            boolean flag = shouldCrit(player, entity);
            float crit = flag ? 0.5F : 0F;
            entity.hurt(damage, (float) (dano * (this.getMagicMultiplier(player, stack) + crit)));
            this.onHit(player, entity, flag);
            this.hurtItem(player, stack);
        }
        if(!flag0){
            player.level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, player.getSoundSource(), 1.0F, 1.0F);
        }
        return true;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.SPEAR;
    }


    public int calculatePower(LivingEntity entity){
        int power = 1;
        if(entity.getAttribute(ModAttributes.AGILITY) != null){
            power = power + (int)Math.floor(entity.getAttributeValue(ModAttributes.AGILITY) / 25);
        }
        return power;
    }

    @Override
    public boolean shouldCrit(Player player, Entity entity) {
        return !player.isSprinting() && player.fallDistance > 0.0F && !player.isOnGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) && !player.isPassenger() && entity instanceof LivingEntity;
    }

    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0F) {
            p_40998_.hurtAndBreak(1, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack p_41004_, BlockState p_41005_) {
        return p_41005_.is(this.blocks) ? this.speed : 1.0F;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(blocks);
    }

    @Override
    public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
        return !p_43294_.isCreative();
    }
}
