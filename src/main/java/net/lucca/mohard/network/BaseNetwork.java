package net.lucca.mohard.network;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.network.packets.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class BaseNetwork {

    public static final String VERSION = "0.1";

    public static final SimpleChannel CHANNEL = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(ModMain.MODID, "network"),() -> VERSION,
                    version -> version.equals(VERSION), version -> version.equals(VERSION));

    public static void init(){

        int index = 0;

        CHANNEL.messageBuilder(ServerboundHurtItemPacket.class, index++ , NetworkDirection.PLAY_TO_SERVER)
                .consumerMainThread(ServerboundHurtItemPacket::handle)
                .encoder(ServerboundHurtItemPacket::encode)
                .decoder(ServerboundHurtItemPacket::new).add();
    }
}
