package net.lucca.mohard.capabilities.entail;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.capabilities.ModCapabilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EssenceEntailCapabilitySetup {

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ModMain.MODID, "essence_entail"), new EssenceEntailCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void resetStats(PlayerEvent.Clone event) {
        Player playerOriginal = event.getOriginal();
        Player player = event.getEntity();
        playerOriginal.reviveCaps();
        playerOriginal.getCapability(ModCapabilities.ENTAIL_CAPABILITY).ifPresent(entail -> {
            player.getCapability(ModCapabilities.ENTAIL_CAPABILITY).ifPresent(entail1 -> {
                entail1.setEntail(entail.getEntail());
            });
        });
        event.getOriginal().invalidateCaps();
    }

}
