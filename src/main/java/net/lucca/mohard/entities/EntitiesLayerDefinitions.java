package net.lucca.mohard.entities;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.entities.amethystBoulder.AmethystBoulderModel;
import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadModel;
import net.lucca.mohard.init.ModModelLayers;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModMain.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitiesLayerDefinitions {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.NOMAD, () -> LayerDefinition.create(SpiritualNomadModel.createBodyModel(), 64, 64));
        event.registerLayerDefinition(ModModelLayers.AMETHYST_BOULDER, AmethystBoulderModel::createBodyLayer);
    }

}
