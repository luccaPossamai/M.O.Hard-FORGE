package net.lucca.mohard.block.essenceExchanger;

import net.lucca.mohard.init.ModBlockStateProperties;
import net.lucca.mohard.init.ModItems;
import net.lucca.mohard.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


public class EssenceExchangerBlock extends BaseEntityBlock {


    public static final IntegerProperty CHARGE = ModBlockStateProperties.ESSENCE_EXCHANGER_CHARGES;
    private static EssenceExchangerTileEntity tileEntity;

    public EssenceExchangerBlock(Properties p_49224_) {
        super(p_49224_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(CHARGE, Integer.valueOf(0)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        tileEntity = new EssenceExchangerTileEntity(p_153215_, p_153216_);
        return tileEntity;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152694_, BlockState p_152695_, BlockEntityType<T> p_152696_) {
        return p_152694_.isClientSide ? null : createTickerHelper(p_152696_, ModTileEntityTypes.ESSENCE_EXCHANGER.get(), EssenceExchangerTileEntity::serverTick);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level world, BlockPos pos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {

        if(world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof EssenceExchangerTileEntity essenceExchangerTileEntity && player instanceof ServerPlayer) {
                if(player.getItemInHand(p_60507_).is(ModItems.SOUL_FIRE_CHARGE.get()) &&
                canBeCharged(p_60503_)) {
                    charge(world, pos);
                    player.getItemInHand(p_60507_).shrink(1);

                } else {
                    player.openMenu(essenceExchangerTileEntity);
                }
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_);
        p_49915_.add(ModBlockStateProperties.ESSENCE_EXCHANGER_CHARGES);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_54296_) {
        return RenderShape.MODEL;
    }

    public void charge(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        level.setBlock(pos, state.setValue(CHARGE, state.getValue(CHARGE) + 1), 3);
        if (!level.isClientSide) {
            level.levelEvent(1003, pos, 0);
        }
    }

    public static void uncharge(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        level.setBlock(pos, state.setValue(CHARGE, state.getValue(CHARGE) - 1), 3);
        if (!level.isClientSide) {
            level.levelEvent(1003, pos, 0);
        }
    }

    public boolean canBeCharged(BlockState state){
        return state.getValue(CHARGE) < 3;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean bool) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = world.getBlockEntity(pos);
            if (blockentity instanceof EssenceExchangerTileEntity) {
                Containers.dropContents(world, pos, (EssenceExchangerTileEntity)blockentity);
            }
        }
        super.onRemove(state, world, pos, newState, bool);

    }
}