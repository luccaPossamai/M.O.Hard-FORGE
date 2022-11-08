package net.lucca.mohard.capabilities;

import net.lucca.mohard.capabilities.altar.AltarCapability;
import net.lucca.mohard.capabilities.entail.EssenceEntailCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModCapabilities {

    public static final Capability<AltarCapability> ALTAR_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<EssenceEntailCapability> ENTAIL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    @SubscribeEvent
    public void registerCap(RegisterCapabilitiesEvent event){
        event.register(AltarCapability.class);
        event.register(EssenceEntailCapability.class);
    }
}
