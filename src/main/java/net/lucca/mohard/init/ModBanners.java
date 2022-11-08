package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class ModBanners {

    public static DeferredRegister<BannerPattern> BANNERS = DeferredRegister.create(Registry.BANNER_PATTERN_REGISTRY, ModMain.MODID);

    public static void register(IEventBus bus){
        BANNERS.register(bus);
    }

    public static final RegistryObject<BannerPattern> ESSENCE =  create("essence", new BannerPattern("ssnc"));
    public static final RegistryObject<BannerPattern> NOMADS =  create("nomads", new BannerPattern("nmds"));

    private static RegistryObject<BannerPattern> create(String p_222757_, BannerPattern bannerPattern) {
        return BANNERS.register(p_222757_,() -> bannerPattern);
    }



}
