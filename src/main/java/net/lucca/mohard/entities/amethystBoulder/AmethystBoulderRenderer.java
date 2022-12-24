package net.lucca.mohard.entities.amethystBoulder;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class AmethystBoulderRenderer extends EntityRenderer<AmethystBoulder> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ModMain.MODID, "textures/entity/amethyst_boulder.png");
    protected AmethystBoulderModel<AmethystBoulder> model;

    public AmethystBoulderRenderer(EntityRendererProvider.Context p_174368_) {
        super(p_174368_);
        this.model = new AmethystBoulderModel<>(p_174368_.bakeLayer(ModModelLayers.AMETHYST_BOULDER));
    }

    @Override
    public void render(AmethystBoulder p_114080_, float p_114081_, float p_114082_, PoseStack p_114083_, MultiBufferSource p_114084_, int p_114085_) {
        this.shadowRadius = 0.23F;
        p_114083_.pushPose();
        p_114083_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_114082_, p_114080_.yRotO, p_114080_.getYRot()) - 90.0F));
        p_114083_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_114082_, p_114080_.xRotO, p_114080_.getXRot())));
        p_114083_.scale(-1F, -1F, 1F);
        this.model.setupAnim(p_114080_, p_114082_, 0, 0, 0, p_114080_.getTicksFrozen());
        VertexConsumer vertexconsumer = p_114084_.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        this.model.renderToBuffer(p_114083_, vertexconsumer, p_114085_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_114083_.popPose();
        super.render(p_114080_, p_114081_, p_114081_, p_114083_, p_114084_, p_114085_);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(AmethystBoulder p_115860_) {
        return TEXTURE_LOCATION;
    }
}
