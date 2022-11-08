package net.lucca.mohard.entities.etc.corruptedPlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.lucca.mohard.init.ModModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CorruptedPlayerLayer<T extends CorruptedPlayer, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation POWER_LOCATION = new ResourceLocation("mohard", "textures/entity/corrupted_player_layer.png");
    private final PlayerModel<T> model;

    public CorruptedPlayerLayer(RenderLayerParent<T, M> p_117346_, EntityModelSet p_174472_) {
        super(p_117346_);
        model = new PlayerModel<>(p_174472_.bakeLayer(ModModelLayers.CORRUPTED_PLAYER_LAYER), false);
    }

    protected float xOffset(float p_116683_) {
        return p_116683_ * 0.001F;
    }

    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

    protected EntityModel<T> model() {
        return this.model;
    }

    @Override
    public void render(PoseStack p_117349_, MultiBufferSource p_117350_, int p_117351_, T p_117352_, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        float f = (float)p_117352_.tickCount + p_117355_;
        EntityModel<T> entitymodel = this.model();
        entitymodel.prepareMobModel(p_117352_, p_117353_, p_117354_, p_117355_);
        this.getParentModel().copyPropertiesTo(entitymodel);
        VertexConsumer vertexconsumer = p_117350_.getBuffer(RenderType.energySwirl(this.getTextureLocation(), this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));
        entitymodel.setupAnim(p_117352_, p_117353_, p_117354_, p_117356_, p_117357_, p_117358_);
        entitymodel.renderToBuffer(p_117349_, vertexconsumer, p_117351_, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);

    }
}
