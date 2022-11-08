package net.lucca.mohard.block.ore;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class EssenceOre extends DropExperienceBlock {

    protected final Type type;

    public EssenceOre(Properties properties){
        this(Type.STONE, properties, ConstantInt.of(0));
    }

    public EssenceOre(Type type, Properties properties, IntProvider intProvider) {
        super(properties, intProvider);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity block, ItemStack stack) {
        super.playerDestroy(level, player, pos, state, block, stack);
        if (!level.isClientSide) {
            if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) != 0){ return; }
            if(new Random().nextFloat() <= 0.001F){
                /*
                ItemStack stack1 = new ItemStack(ModEssences..get(), 1);
                EssenceItem.unactivate(stack1, true);
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack1));
                level.updateNeighbourForOutputSignal(pos, this);

                 */
            }
        }
    }

    public enum Type{
        STONE(MobCategory.CREATURE),
        DEEPSLATE(null)
        ;

        protected final MobCategory mobCategory;
        Type(@Nullable MobCategory mobCategory){
            this.mobCategory = mobCategory;
        }

        public MobCategory getMobCategory() {
            return mobCategory;
        }
    }
}
