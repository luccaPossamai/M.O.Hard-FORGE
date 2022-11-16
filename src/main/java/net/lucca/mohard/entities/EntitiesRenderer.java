package net.lucca.mohard.entities;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.entities.amethystBoulder.AmethystBoulderRenderer;
import net.lucca.mohard.entities.coldfireball.ColdFireballRenderer;
import net.lucca.mohard.entities.iceIsolator.IceIsolatorRenderer;
import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadRenderer;
import net.lucca.mohard.init.ModEntityTypes;
import net.lucca.mohard.itens.artifacts.algidAxe.AlgidAxeRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

}
