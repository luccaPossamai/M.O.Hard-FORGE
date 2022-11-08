package net.lucca.mohard.entities;

import net.lucca.mohard.entities.corrupter.Corrupter;
import net.lucca.mohard.entities.dummy.DummyEntity;
import net.lucca.mohard.entities.etc.corruptedPlayer.CorruptedPlayer;
import net.lucca.mohard.entities.illagers.iceIsolator.IceIsolator;
import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadEntity;
import net.lucca.mohard.init.ModEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitiesAttributesSetup {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.CORRUPTER.get(), Corrupter.createAttributes().build());
        event.put(ModEntityTypes.DUMMY.get(), DummyEntity.createAttributes().build());
        event.put(ModEntityTypes.CORRUPTED_PLAYER.get(), CorruptedPlayer.createAttributes().build());
        event.put(ModEntityTypes.ICE_ISOLATOR.get(), IceIsolator.createAttributes().build());
        event.put(ModEntityTypes.NOMAD.get(), SpiritualNomadEntity.createAttributes().build());

    }

}
