package net.lucca.mohard.capabilities.altar;


import net.lucca.mohard.ModMain;
import net.lucca.mohard.capabilities.ModCapabilities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AltarCapabilitySetup {

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof  Player) {
            Player player = (Player) event.getObject();
            event.addCapability(new ResourceLocation(ModMain.MODID, "altar_inventory"), new AltarCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void resetStats(PlayerEvent.Clone event) {
        Player playerOriginal = event.getOriginal();
        Player player = event.getEntity();
        playerOriginal.reviveCaps();
        playerOriginal.getCapability(ModCapabilities.ALTAR_CAPABILITY).ifPresent(inv -> {
            player.getCapability(ModCapabilities.ALTAR_CAPABILITY).ifPresent(inv2 -> {
                for (int i = 0; i < 9; i++) {
                    inv2.setStackInSlot(i, inv.getStackInSlot(i));
                }
            });
        });
        event.getOriginal().invalidateCaps();
    }

}
