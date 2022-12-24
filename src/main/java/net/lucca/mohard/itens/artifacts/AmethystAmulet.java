package net.lucca.mohard.itens.artifacts;

import net.lucca.mohard.entities.amethystBoulder.AmethystBoulder;
import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.init.ModParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class AmethystAmulet extends GenericArtifact {

    private static final Map<Integer, List<Float>> POWER_TO_ANGLES = Map.of(1, List.of(0F, 30F, -30F), 2, List.of(0F, 15F, 30F, 45F, -15F, -30F, -45F),3, List.of(0F, 15F, 30F, 45F, 60F, 75F, 90F, -15F, -30F, -45F, -60F, -75F, -90F));

    public AmethystAmulet(Properties properties) {
        super(properties, 1, 20 * 8, true);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 15;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity entity, int tick) {

        int power = getPower(this.getUseDuration(itemStack) - tick);
        if (power <= 0) return;
        if(entity instanceof Player player) {
            this.generateCooldown(player, itemStack);
            this.hurtItem(player, itemStack);
        }
        double damage = ((1 + ((power - 1) / 10F)) * entity.getAttributeValue(ModAttributes.MAGIC_DAMAGE)) * ((GenericArtifact) itemStack.getItem()).getMagicMultiplier(entity, itemStack);
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, itemStack);
        if(!level.isClientSide && POWER_TO_ANGLES.containsKey(power)) {
            POWER_TO_ANGLES.get(power).forEach(aFloat -> {
                double d0 = power * 1.4 * (-Mth.sin((aFloat + entity.getYRot()) * ((float) Math.PI / 180F)));
                double d1 = power * 1.4 * Mth.cos((aFloat + entity.getYRot()) * ((float) Math.PI / 180F));
                this.addBoulderWithAngle(level,entity, damage, d0, d1, (byte)i);
            });
            ((ServerLevel) entity.level).sendParticles(ModParticles.AMETHYST_PARTICLE.get(), entity.getX(), entity.getY(2.5), entity.getZ(), 0, 0, 0, 0, 0);

        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level p_40672_, Player p_40673_, @NotNull InteractionHand p_40674_) {
        ItemStack itemstack = p_40673_.getItemInHand(p_40674_);
        if (this.canCast(p_40673_, itemstack)) {
            p_40673_.startUsingItem(p_40674_);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public int getPower(int tick){
        int i = (int) Math.floor(Math.min(1, (tick / 10F))) + (int) Math.floor(Math.min(1, (tick / 25F))) + (int) Math.floor(Math.min(1, (tick / 45F)));
        return Mth.clamp(i, 0, 3);
    }

    private void addBoulderWithAngle(Level level, LivingEntity entity, double damage, double xPower, double zPower, byte pierceLevel){
        AmethystBoulder amethystBoulder = new AmethystBoulder(level, entity, damage, new Vec3(xPower, 0, zPower), this.getCenterVec(entity.getBoundingBox()));
        amethystBoulder.setPierceLevel(pierceLevel);
        entity.level.addFreshEntity(amethystBoulder);
    }

    private Vec3 getCenterVec(AABB aabb){
        double x = (aabb.minX + aabb.maxX) / 2D;
        double y = (aabb.minY + aabb.maxY) / 2D;
        double z = (aabb.minZ + aabb.maxZ) / 2D;
        return new Vec3( x, y, z);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.equals(Enchantments.PIERCING);
    }

}
