package net.lucca.mohard.itens.artifacts;

import net.lucca.mohard.init.ModAttributes;
import net.lucca.mohard.init.ModDamageSources;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class DeepNetherPetrifiedTome extends GenericArtifact implements IForgeItem {

    public DeepNetherPetrifiedTome(Properties p_i48487_1_) {
        super(p_i48487_1_, 1, 15 * 20, true);
    }



    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(this.canCast(player, itemStack) && player instanceof ServerPlayer && this.isEnabled(itemStack)) {
            TargetingConditions pred = TargetingConditions.forCombat().range(5);
            List<Entity> entities = world.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(5D), entity -> true);
            entities = entities.stream().filter(entity -> player.distanceTo(entity) <= 5D && entity != player).collect(Collectors.toList());
            double baseDamage = player.getAttributeValue(ModAttributes.MAGIC_DAMAGE) * this.getMagicMultiplier(player, itemStack);
            if(entities.size() > 0){
                entities.forEach(entity -> {
                    if(entity instanceof LivingEntity livingEntity){
                        livingEntity.hurt(ModDamageSources.ancientBook(player, player), (float) baseDamage);
                    }
                    this.makeParticle(player, entity);
                    this.knockBack(player, entity);
                });
                generateCooldown(player, itemStack);
                this.hurtItem(player, itemStack);
                return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide);
            }

        }
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }
    private void makeParticle(Player player, Entity livingEntity){
        Vec3 playerCenterVec = this.getCenterVec(player.getBoundingBox());
        Vec3 entityCenterVec = this.getCenterVec(livingEntity.getBoundingBox());
        double x = entityCenterVec.x() - playerCenterVec.x();
        double y = entityCenterVec.y() - playerCenterVec.y();
        double z = entityCenterVec.z() - playerCenterVec.z();
        for(int i = 1; i < 11; i++){
            ((ServerLevel)player.getLevel()).sendParticles(ParticleTypes.FLAME, playerCenterVec.x + (x * i / 10), playerCenterVec.y + (y * i / 10), playerCenterVec.z + (z * i / 10),1, player.getRandom().nextFloat() * 0.1F, player.getRandom().nextFloat() * 0.1F, player.getRandom().nextFloat() * 0.1F, 0);
        }
    }

    private void knockBack(Player player, Entity entity){
        double d0 = 1.4 * (-Mth.sin((player.getYRot()) * ((float) Math.PI / 180F)));
        double d1 = 1.4 * Mth.cos((player.getYRot()) * ((float) Math.PI / 180F));
        Vec3 vec3 = new Vec3(d0, 0, d1).normalize().scale(1.5F);
        entity.setDeltaMovement(entity.getDeltaMovement().scale(0.1F).add(vec3));
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack p_41402_, ItemStack p_41403_) {
        return p_41403_.is(Blocks.MAGMA_BLOCK.asItem());
    }

    private Vec3 getCenterVec(AABB aabb){
        double x = (aabb.minX + aabb.maxX) / 2D;
        double y = (aabb.minY + aabb.maxY) / 2D;
        double z = (aabb.minZ + aabb.maxZ) / 2D;
        return new Vec3( x, y, z);
    }
}
