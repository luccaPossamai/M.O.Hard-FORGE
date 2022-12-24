package net.lucca.mohard.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ClientboundSetEntityDeltaMovementPacket {

    private final int id;
    private final Vec3 vec3;
    private final Vec3 pos;

    public ClientboundSetEntityDeltaMovementPacket(int id, Vec3 vec3, Vec3 pos){
        this.id = id;
        this.vec3 = vec3;
        this.pos = pos;
    }

    public ClientboundSetEntityDeltaMovementPacket(FriendlyByteBuf byteBuf){
        this(byteBuf.readVarInt(), new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble()), new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble()));
    }

    public void encode(FriendlyByteBuf byteBuf){
        byteBuf.writeVarInt(this.id);
        byteBuf.writeDouble(this.vec3.x);
        byteBuf.writeDouble(this.vec3.y);
        byteBuf.writeDouble(this.vec3.z);
        byteBuf.writeDouble(this.pos.x);
        byteBuf.writeDouble(this.pos.y);
        byteBuf.writeDouble(this.pos.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> event) {
        final var success = new AtomicBoolean(false);
        event.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                if(Minecraft.getInstance().player != null){
                    Entity entity = Minecraft.getInstance().player.getLevel().getEntity(this.id);
                    if(entity != null) {
                        entity.lerpMotion(this.vec3.x, this.vec3.y, this.vec3.z);
                        entity.setPos(this.pos);
                    }
                }
            });
        });
        event.get().setPacketHandled(true);
        return success.get();
    }

}
