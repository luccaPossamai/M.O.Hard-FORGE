package net.lucca.mohard.entities.villagers.nomad;

import net.lucca.mohard.init.ModModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;

public class SpiritualNomadRenderer extends MobRenderer<SpiritualNomadEntity, SpiritualNomadModel<SpiritualNomadEntity>> {
    private static final ResourceLocation NOMAD_BASE_SKIN = new ResourceLocation("mohard", "textures/entity/nomad.png");


    public SpiritualNomadRenderer(EntityRendererProvider.Context p_i50953_1_) {
        super(p_i50953_1_, new SpiritualNomadModel<>(p_i50953_1_.bakeLayer(ModModelLayers.NOMAD)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, p_i50953_1_.getModelSet(), p_i50953_1_.getItemInHandRenderer()));
        this.addLayer(new CrossedArmsItemLayer<>(this, p_i50953_1_.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(SpiritualNomadEntity p_110775_1_) {
        return NOMAD_BASE_SKIN;
    }

    protected void scale(SpiritualNomadEntity p_225620_1_, PoseStack p_225620_2_, float p_225620_3_) {
        float f = 0.9375F;
        p_225620_2_.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
