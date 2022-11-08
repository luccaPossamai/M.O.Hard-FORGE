package net.lucca.mohard.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerboundHurtItemPacket {

    private final ItemStack item;
    private final EquipmentSlot slot;
    public ServerboundHurtItemPacket(ItemStack item, EquipmentSlot slot){
        this.item = item;
        this.slot = slot;
    }

    public ServerboundHurtItemPacket(FriendlyByteBuf buffer){
        this(buffer.readItem(), buffer.readEnum(EquipmentSlot.class));
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeItemStack(this.item, false);
        buffer.writeEnum(this.slot);
    }

    public boolean handle(Supplier<NetworkEvent.Context> event) {
        final var success = new AtomicBoolean(false);
        event.get().enqueueWork(() -> {
            if(event.get().getSender() != null) {
                ItemStack item = event.get().getSender().getItemBySlot(this.slot);
                item.hurtAndBreak(1, event.get().getSender(), entity1 -> entity1.broadcastBreakEvent(this.slot));
                boolean result = false;
                success.set(result);
            }
        });
        event.get().setPacketHandled(true);
        return success.get();
    }
}
