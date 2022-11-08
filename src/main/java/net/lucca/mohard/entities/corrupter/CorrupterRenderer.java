package net.lucca.mohard.entities.corrupter;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;

public class CorrupterRenderer extends MobRenderer<Corrupter, CorrupterModel<Corrupter>> {

    public CorrupterRenderer(EntityRendererProvider.Context p_i50953_1_) {
        super(p_i50953_1_, new CorrupterModel<>(p_i50953_1_.bakeLayer(ModModelLayers.CORRUPTER)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, p_i50953_1_.getModelSet(), p_i50953_1_.getItemInHandRenderer()));
        this.addLayer(new CarryingItemInHead<>(this, p_i50953_1_.getItemInHandRenderer()));

    }


    @Override
    public ResourceLocation getTextureLocation(Corrupter p_114482_) {
        return new ResourceLocation(ModMain.MODID, "textures/entity/corrupter/corrupter.png");
    }

}
