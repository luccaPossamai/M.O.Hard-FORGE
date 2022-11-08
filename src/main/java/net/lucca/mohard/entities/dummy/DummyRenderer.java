package net.lucca.mohard.entities.dummy;

import net.lucca.mohard.init.ModModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class DummyRenderer<T extends DummyEntity> extends LivingEntityRenderer<T, DummyModel<T>> {

    List<ResourceLocation> textures = Arrays.asList(new ResourceLocation("mohard", "textures/entity/dummy.png"),
            new ResourceLocation("mohard", "textures/entity/dummy_pump.png"));



    public DummyRenderer(EntityRendererProvider.Context p_174289_) {
        super(p_174289_, new DummyModel<>(p_174289_.bakeLayer(ModModelLayers.DUMMY)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel(p_174289_.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)), new HumanoidModel(p_174289_.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR))));
        this.addLayer(new ItemInHandLayer<>(this, p_174289_.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, p_174289_.getModelSet()));
        this.addLayer(new CustomHeadLayer<>(this, p_174289_.getModelSet(), p_174289_.getItemInHandRenderer()));

    }

    @Override
    public ResourceLocation getTextureLocation(DummyEntity p_114482_) {
        return this.textures.get(p_114482_.getDummyType().id);

    }
}
