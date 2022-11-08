package net.lucca.mohard.itens;

import net.lucca.mohard.block.essenceExchanger.EssenceExchangerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class VilioCharge extends Item {
    public VilioCharge(Properties p_41383_) {
        super(p_41383_);
        DispenserBlock.registerBehavior(this, new OptionalDispenseItemBehavior() {
            protected @NotNull ItemStack execute(@NotNull BlockSource p_123416_, @NotNull ItemStack p_123417_) {
                this.setSuccess(false);
                Level level = p_123416_.getLevel();
                BlockPos blockpos = p_123416_.getPos().relative(p_123416_.getBlockState().getValue(DispenserBlock.FACING));
                BlockState state = level.getBlockState(blockpos);
                if(state.getBlock() instanceof EssenceExchangerBlock essenceExchangerBlock){
                    if(essenceExchangerBlock.canBeCharged(state)){
                        essenceExchangerBlock.charge(level, blockpos);
                        p_123417_.shrink(1);
                        this.setSuccess(true);
                    }
                }
                return p_123417_;
            }
        });
    }
}
