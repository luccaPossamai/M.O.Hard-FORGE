package net.lucca.mohard.entities.iceIsolator;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;

public class IceIsolatorRenderer extends IllagerRenderer<IceIsolator> {

    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("mohard", "textures/entity/illager/ice_isolator/ice_isolator.png");
    private static final ResourceLocation BRUNO_TEXTURE = new ResourceLocation("mohard", "textures/entity/illager/ice_isolator/bruno.png");

    public IceIsolatorRenderer(EntityRendererProvider.Context p_i50966_1_) {
        super(p_i50966_1_, new IllagerModel<>(p_i50966_1_.bakeLayer(ModelLayers.VINDICATOR)), 0.5F);
        this.model.getHat().visible = true;
        this.addLayer(new ItemInHandLayer<>(this, p_i50966_1_.getItemInHandRenderer()) {
            public void render(PoseStack p_116352_, MultiBufferSource p_116353_, int p_116354_, IceIsolator p_116355_, float p_116356_, float p_116357_, float p_116358_, float p_116359_, float p_116360_, float p_116361_) {
                if (!p_116355_.getArmPose().equals(AbstractIllager.IllagerArmPose.CROSSED)) {
                    super.render(p_116352_, p_116353_, p_116354_, p_116355_, p_116356_, p_116357_, p_116358_, p_116359_, p_116360_, p_116361_);
                }

            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(IceIsolator p_114482_) {
        String s = ChatFormatting.stripFormatting(p_114482_.getName().getString());
        if ("Bruno".equals(s)) {
            return BRUNO_TEXTURE;
        }
        return DEFAULT_TEXTURE;
    }

}
