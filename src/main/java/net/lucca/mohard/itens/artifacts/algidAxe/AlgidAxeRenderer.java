package net.lucca.mohard.itens.artifacts.algidAxe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ItemSupplier;
import org.jetbrains.annotations.NotNull;

public class AlgidAxeRenderer extends EntityRenderer<ThrownAlgidAxe> {

    private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
    private final ItemRenderer itemRenderer;
    private final float scale = 1F;
    private final boolean fullBright = true;

    public AlgidAxeRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.itemRenderer = p_174008_.getItemRenderer();
    }

    @Override
    public void render(ThrownAlgidAxe p_116085_, float p_116086_, float p_116087_, PoseStack p_116088_, MultiBufferSource p_116089_, int p_116090_) {
        if (p_116085_.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(p_116085_) < MIN_CAMERA_DISTANCE_SQUARED)) {
            int rotation = (p_116085_.tickCount * 9) * p_116085_.getPower();
            p_116088_.pushPose();
            p_116088_.scale(this.scale, this.scale, this.scale);
            p_116088_.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(p_116087_, p_116085_.yRotO, p_116085_.getYRot()) - 90.0F));
            if(!p_116085_.inGround()){
                p_116088_.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(p_116087_, p_116085_.xRotO, p_116085_.getXRot()) - rotation));
                p_116085_.setLastRotation(rotation);
            } else {
                p_116088_.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(p_116087_, p_116085_.xRotO, p_116085_.getXRot()) - p_116085_.getLastRotation()));
            }

            this.itemRenderer.renderStatic(p_116085_.getPickupItem(), ItemTransforms.TransformType.FIXED, p_116090_, OverlayTexture.NO_OVERLAY, p_116088_, p_116089_, p_116085_.getId());
            p_116088_.popPose();
            super.render(p_116085_, p_116086_, p_116087_, p_116088_, p_116089_, p_116090_);
        }

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ThrownAlgidAxe p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
