package net.lucca.mohard.block.altar;

import net.lucca.mohard.init.ModParticles;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;


import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

public class AltarBlock extends BaseEntityBlock {

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final List<BlockPos> BOOKSHELF_OFFSETS = BlockPos.betweenClosedStream(-1, 0, -1, 1, 1, 1).map(BlockPos::immutable).toList();
    public AltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState p_220827_, Level p_220828_, BlockPos p_220829_, RandomSource p_220830_) {
        super.animateTick(p_220827_, p_220828_, p_220829_, p_220830_);
        for(BlockPos blockpos : BOOKSHELF_OFFSETS) {
            if (p_220830_.nextInt(16) == 0) {
                p_220828_.addParticle(ModParticles.VILIO_PARTICLE.get(), (double)p_220829_.getX() + 0.5D, (double)p_220829_.getY() + 2.0D, (double)p_220829_.getZ() + 0.5D, (double)((float)blockpos.getX() + p_220830_.nextFloat()) - 0.5D, (double)((float)blockpos.getY() - p_220830_.nextFloat() - 1.0F), (double)((float)blockpos.getZ() + p_220830_.nextFloat()) - 0.5D);
            }
        }
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState p_51104_, @NotNull BlockGetter p_51105_, @NotNull BlockPos p_51106_, @NotNull CollisionContext p_51107_) {
        return SHAPE;
    }
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_149645_1_) {
        return RenderShape.MODEL;
    }
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult rayTrace) {
        if(world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof AltarTileEntity && player instanceof ServerPlayer) {
                player.openMenu((AltarTileEntity) tileentity);

            }
            return InteractionResult.CONSUME;
        }
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    @Override
    public void onRemove(BlockState state, @NotNull Level world, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if(!state.is(newState.getBlock())){
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof Container){
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos p_153215_, @NotNull BlockState p_153216_) {
        return new AltarTileEntity(p_153215_, p_153216_);
    }


}
