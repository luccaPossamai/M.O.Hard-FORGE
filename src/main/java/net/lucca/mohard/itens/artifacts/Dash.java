package net.lucca.mohard.itens.artifacts;

import net.lucca.mohard.init.ModParticles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeItem;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;


public class Dash extends GenericArtifact implements IForgeItem {

    private double oldMultiplier = 0;

    public Dash(Properties p_i48487_1_) {
        super(p_i48487_1_, 0, 10 * 20);
    }

    @Override
    public void releaseUsing(ItemStack item, Level world, LivingEntity entity, int tick) {
        if(entity instanceof Player) {
            Player player = (Player) entity;
            changeState(item, 0);
            this.generateCooldown(player, item);
            entity.level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, SoundSource.PLAYERS, 2F, 1F, false);
            double multiplier = calculateBuff(tick, player);
            Vec3 multiplierV = new Vec3(multiplier * multiplier, multiplier * multiplier + 0.2F, multiplier * multiplier);

            Vec3 vector = player.getViewVector(1.0F);

            entity.setDeltaMovement(player.getDeltaMovement().add(vector.multiply(multiplierV)));

        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.SPEAR;


    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        double multiplier = calculateBuff(count, player);

        if(count == getUseDuration(stack) - 20){
            changeState(stack, 2);
        }

        for (int i = 1; i <= 12; i++) {
            double r = 0.4 + 1.5 - multiplier;
            double sin = Math.sin(i * Math.PI / 6);
            double cos = Math.cos(i * Math.PI / 6);
            double x = (player.getX()) + (sin * r);
            double y = player.getY()- 0.7 + (multiplier * 2);
            double z = (player.getZ()) + (cos * r);

            player.level.addParticle(ModParticles.VILIO_PARTICLE.get(), x, y, z, 0, 0, 0);
        }
        if(multiplier != oldMultiplier){
            oldMultiplier = multiplier;
            player.level.playLocalSound(player.getX(),player.getY(),player.getZ() , SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.PLAYERS, 2F, (float) multiplier + 0.4F, false);
        }
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(this.canCast(player, itemstack) && player instanceof ServerPlayer) {
            player.startUsingItem(hand);
            changeState(itemstack, 1);
            this.generateCooldown(player, itemstack);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.fail(itemstack);
    }

    private void changeState(ItemStack itemStack, int amount){
        if(itemStack.hasTag()) {
            if (itemStack.getTag().contains("State")) {
                itemStack.getTag().remove("State");
            }
            itemStack.getTag().putInt("State", amount);
        } else {
            CompoundTag tag = new CompoundTag();
            tag.putInt("State", amount);
            itemStack.setTag(tag);
        }
    }

    public static int getState(ItemStack itemStack){
        if(itemStack.getItem() instanceof Dash) {
            if (itemStack.hasTag()) {
                if (itemStack.getTag().contains("State")) {
                    return itemStack.getTag().getInt("State");
                }
            }
        }
        return 0;
    }

    private double calculateBuff(int tick, LivingEntity player){
        double multiplier = 1;
        if(player.isOnGround()){
            double t = ((72000F - tick) / 20) * 2;
            if(t >= 5){
                t = 5;
            }
            multiplier = multiplier + (t / 10);

        }
        return multiplier;
    }
}
