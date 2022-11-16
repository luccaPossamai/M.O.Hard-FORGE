package net.lucca.mohard.entities;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.entities.coldfireball.ColdFireballRenderer;
import net.lucca.mohard.entities.iceIsolator.IceIsolatorRenderer;
import net.lucca.mohard.entities.amethystBoulder.AmethystBoulderModel;
import net.lucca.mohard.entities.amethystBoulder.AmethystBoulderRenderer;
import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadModel;
import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadRenderer;
import net.lucca.mohard.init.ModEntityTypes;
import net.lucca.mohard.init.ModModelLayers;
import net.lucca.mohard.itens.artifacts.algidAxe.AlgidAxeRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitiesRenderer {

    @SubscribeEvent
    public static void register(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntityTypes.ICE_ISOLATOR.get(), IceIsolatorRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.NOMAD.get(), SpiritualNomadRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ALGID_AXE.get(), AlgidAxeRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.COLD_FIREBALL.get(), ColdFireballRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.AMETHYST_BOULDER.get(), AmethystBoulderRenderer::new);

    }

    public static void registryEntityRenderers(){
        registerRenderer(ModEntityTypes.ICE_ISOLATOR.get(), IceIsolatorRenderer::new);
        registerRenderer(ModEntityTypes.NOMAD.get(), SpiritualNomadRenderer::new);
        registerRenderer(ModEntityTypes.ALGID_AXE.get(), AlgidAxeRenderer::new);
        registerRenderer(ModEntityTypes.COLD_FIREBALL.get(), ColdFireballRenderer::new);
        registerRenderer(ModEntityTypes.AMETHYST_BOULDER.get(), AmethystBoulderRenderer::new);
    }

    public static void layersRegistry() {
        registerModel(ModModelLayers.NOMAD, () -> LayerDefinition.create(SpiritualNomadModel.createBodyModel(), 64, 64));
        registerModel(ModModelLayers.AMETHYST_BOULDER, AmethystBoulderModel::createBodyLayer);
    }

    private static<T extends Entity> void registerRenderer(EntityType<? extends T> entityType, EntityRendererProvider<T> entityRendererProvider) {
        EntityRenderers.register(entityType, entityRendererProvider);
    }
    private static void registerModel(ModelLayerLocation layerLocation, Supplier<LayerDefinition> supplier) {
        ForgeHooksClient.registerLayerDefinition(layerLocation, supplier);
    }


}
