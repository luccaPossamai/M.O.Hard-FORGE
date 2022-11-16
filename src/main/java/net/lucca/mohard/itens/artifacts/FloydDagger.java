package net.lucca.mohard.itens.artifacts;

import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.init.ModDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeItem;

public class FloydDagger extends GenericArtifact implements IForgeItem, Wearable, MeleeCrittableArtifact {

    public FloydDagger(Properties p_i48487_1_) {
        super(p_i48487_1_, 1.5, 2 * 20, true);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 575;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        boolean flag0 = this.canCast(player, stack);
        if (this.canCast(player, stack) && player instanceof ServerPlayer && this.isEnabled(stack)) {
            this.generateCooldown(player, stack);
            DamageSource damage = ModDamageSources.dagger(player, player);
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
    public boolean isValidRepairItem(ItemStack p_41134_, ItemStack p_41135_) {
        return p_41135_.is(Items.PHANTOM_MEMBRANE);
    }


    @Override
    public boolean shouldCrit(Player player, Entity entity) {
        return !player.isSprinting() && player.fallDistance > 0.0F && !player.isOnGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) && !player.isPassenger() && entity instanceof LivingEntity;
    }

    @Override
    public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
        return !p_43294_.isCreative();
    }
}
