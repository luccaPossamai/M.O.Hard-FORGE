package net.lucca.mohard.block.altar.appearence;

import net.lucca.mohard.block.altar.AltarBlock;
import net.lucca.mohard.block.altar.AltarTileEntity;
import net.lucca.mohard.util.mechanics.evolution.PlayerEvolution;
import net.lucca.mohard.init.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;

import java.util.List;

public class AltarTileEntityRenderer implements BlockEntityRenderer<AltarTileEntity> {

    private List<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);


    public AltarTileEntityRenderer(BlockEntityRendererProvider.Context p_173602_) {
    }

    @Override
    public void render(AltarTileEntity altar, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        setItems();
        renderItems(matrix, combinedLightIn, combinedOverlayIn, bufferIn, altar.getBlockState().getValue(AltarBlock.FACING));
        matrix.pushPose();
        matrix.translate(0D, 0D, 0D);
        matrix.scale(1F, 1F, 1F);
        matrix.popPose();
    }

    private void renderItems(PoseStack matrix, int j, int u, MultiBufferSource renderType, Direction direction){

        for(int i = 0; i < items.size(); ++i) {
            ItemStack itemstack = items.get(i);
            if (itemstack == ItemStack.EMPTY) {
                itemstack = new ItemStack(ModItems.GENERIC_ESSENCE.get());
            }
            matrix.pushPose();
            matrix.translate(0.0D, 0.70D, 0.0D);    
            matrix.mulPose(Vector3f.XP.rotationDegrees(90.0F));

            double rotationX = direction.getAxis().equals(Direction.Axis.Z) ?
                    (direction.getNormal().getZ() * -1) * (Math.ceil(i / 3) - 1) :
                    (direction.getNormal().getX() * -1) * (1  - Math.ceil(i / 3));

            double rotationZ = direction.getAxis().equals(Direction.Axis.Z) ?
                    (direction.getNormal().getZ() * -1) * ((i % 3) - 1):
                    (direction.getNormal().getX() * -1) * ((i % 3) - 1);


            matrix.translate(0.5D + ((0.25D) * rotationX), 0.5D + ((0.25D) * rotationZ), 0D);
            matrix.scale(0.275F, 0.275F, 0.275F);

            Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemTransforms.TransformType.FIXED, j, u, matrix, renderType, 1);
            matrix.popPose();
        }

    }

    private int getLightLevel(Level world, BlockPos pos){
        int bLight = world.getLightEmission(pos);
        return LightTexture.block(bLight);
    }

    private void setItems() {
        if(Minecraft.getInstance().player != null) {
            Player player= Minecraft.getInstance().player;
            items = PlayerEvolution.getInsideItems(player);
        }
    }

    private float getTime(){
        if(Minecraft.getInstance().player != null) {
            Player player= Minecraft.getInstance().player;
            Level world = player.level;
            return world.getDayTime();
        }
        return 0;
    }
}
