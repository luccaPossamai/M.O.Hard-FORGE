package net.lucca.mohard.entities.corrupter;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CarryingItemInHead<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private final ItemInHandRenderer itemInHandRenderer;

    public CarryingItemInHead(RenderLayerParent<T, M> p_116686_, ItemInHandRenderer itemInHandRenderer) {
        super(p_116686_);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    public void render(PoseStack p_116699_, MultiBufferSource p_116700_, int p_116701_, T p_116702_, float p_116703_, float p_116704_, float p_116705_, float p_116706_, float p_116707_, float p_116708_) {
        p_116699_.pushPose();
        p_116699_.translate(0.0D, (double)0.08F, (double)0F);
        p_116699_.mulPose(Vector3f.XP.rotationDegrees(180.0F));
        ItemStack itemstack = p_116702_.getItemBySlot(EquipmentSlot.MAINHAND);

        this.itemInHandRenderer.renderItem(p_116702_, itemstack, ItemTransforms.TransformType.GROUND, false, p_116699_, p_116700_, p_116701_);
        p_116699_.popPose();
    }
}
