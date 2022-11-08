package net.lucca.mohard.entities;

import net.lucca.mohard.entities.corrupter.CorrupterModel;
import net.lucca.mohard.entities.corrupter.CorrupterRenderer;
import net.lucca.mohard.entities.dummy.DummyModel;
import net.lucca.mohard.entities.dummy.DummyRenderer;
import net.lucca.mohard.entities.etc.ColdFireballRenderer;
import net.lucca.mohard.entities.etc.corruptedPlayer.CorruptedPlayerRenderer;
import net.lucca.mohard.entities.illagers.iceIsolator.IceIsolatorRenderer;
import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadModel;
import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadRenderer;
import net.lucca.mohard.init.ModEntityTypes;
import net.lucca.mohard.init.ModModelLayers;
import net.lucca.mohard.itens.artifacts.algidAxe.AlgidAxeRenderer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.ForgeHooksClient;

import java.util.function.Supplier;


public class EntitiesRenderer {

    public static void registryEntityRenderers(){
        registerRenderer(ModEntityTypes.ICE_ISOLATOR.get(), IceIsolatorRenderer::new);
        registerRenderer(ModEntityTypes.CORRUPTED_PLAYER.get(), CorruptedPlayerRenderer::new);
        registerRenderer(ModEntityTypes.NOMAD.get(), SpiritualNomadRenderer::new);
        registerRenderer(ModEntityTypes.CORRUPTER.get(), CorrupterRenderer::new);
        registerRenderer(ModEntityTypes.ALGID_AXE.get(), AlgidAxeRenderer::new);
        registerRenderer(ModEntityTypes.COLD_FIREBALL.get(), ColdFireballRenderer::new);
         registerRenderer(ModEntityTypes.DUMMY.get(), DummyRenderer::new);


    }

    public static void layersRegistry() {
        registerModel(ModModelLayers.NOMAD, () -> LayerDefinition.create(SpiritualNomadModel.createBodyModel(), 64, 64));
        registerModel(ModModelLayers.CORRUPTER, () -> LayerDefinition.create(CorrupterModel.createBodyModel(), 64, 64));
        registerModel(ModModelLayers.DUMMY, () -> LayerDefinition.create(DummyModel.createMesh(CubeDeformation.NONE), 64, 64));
        registerModel(ModModelLayers.CORRUPTED_PLAYER_LAYER, () -> LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.001F), false), 64, 64));

    }

    private static<T extends Entity> void registerRenderer(EntityType<? extends T> entityType, EntityRendererProvider<T> entityRendererProvider) {
        EntityRenderers.register(entityType, entityRendererProvider);
    }
    private static void registerModel(ModelLayerLocation layerLocation, Supplier<LayerDefinition> supplier) {
        ForgeHooksClient.registerLayerDefinition(layerLocation, supplier);
    }


}
