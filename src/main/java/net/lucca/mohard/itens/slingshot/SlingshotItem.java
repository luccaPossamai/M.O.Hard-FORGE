package net.lucca.mohard.itens.slingshot;

import net.lucca.mohard.init.ModTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class SlingshotItem extends ProjectileWeaponItem {

    public static final int MAX_DRAW_DURATION = 20;
    public static final int DEFAULT_RANGE = 15;


    public SlingshotItem(Properties p_43009_) {
        super(p_43009_);
    }

    public static float getPowerForTime(int p_40662_) {
        float f = (float)p_40662_ / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int p_40670_) {
        if (livingEntity instanceof Player player) {
            ItemStack itemstack = player.getProjectile(stack);

            int i = this.getUseDuration(stack) - p_40670_;
            if (!itemstack.isEmpty()) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.SNOWBALL);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {

                    if (!level.isClientSide) {
                        ThrowableItemProjectile projectile = getThrowableItem(level, itemstack, player);
                        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            //TODO
                        }

                        stack.hurtAndBreak(1, player, (p_40665_) -> {
                            p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                        });

                        level.addFreshEntity(projectile);
                    }

                    level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        player.getInventory().removeItem(itemstack);
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level p_40672_, Player p_40673_, @NotNull InteractionHand p_40674_) {
        ItemStack itemstack = p_40673_.getItemInHand(p_40674_);
        boolean flag = !p_40673_.getProjectile(itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, p_40672_, p_40673_, p_40674_, flag);
        if (ret != null) return ret;

        if (!p_40673_.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            p_40673_.startUsingItem(p_40674_);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack p_40680_) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    public @NotNull ThrowableItemProjectile getThrowableItem(Level level, ItemStack itemStack, Player player){
        if(itemStack.is(Items.ENDER_PEARL)){
            return new ThrownEnderpearl(level, player);
        } else if(itemStack.is(Items.EGG)){
            return new ThrownEgg(level, player);
        } else if (itemStack.is(Items.SPLASH_POTION)){
            ThrownPotion thrownpotion = new ThrownPotion(level, player);
            thrownpotion.setItem(itemStack);
            return thrownpotion;
        } else if (itemStack.is(Items.EXPERIENCE_BOTTLE)){
            return new ThrownExperienceBottle(level, player);
        } else {
            return new Snowball(level, player);
        }
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.is(ModTags.Items.THROWABLE_ITEMS);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 20;
    }
}
