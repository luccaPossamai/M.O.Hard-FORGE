package net.lucca.mohard;

import com.mojang.logging.LogUtils;
import net.lucca.mohard.block.altar.appearence.AltarTileEntityRenderer;
import net.lucca.mohard.config.ModClientConfig;
import net.lucca.mohard.config.ModServerConfig;
import net.lucca.mohard.enchantments.ModEnchantmentCategory;
import net.lucca.mohard.gui.altar.AltarScreen;
import net.lucca.mohard.gui.essenceExchanger.EssenceExchangerScreen;
import net.lucca.mohard.init.*;
import net.lucca.mohard.itens.artifacts.AmethystAmulet;
import net.lucca.mohard.itens.essence.EssenceDataHelper;
import net.lucca.mohard.itens.essenceBundle.EssenceBundleItem;
import net.lucca.mohard.network.BaseNetwork;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ModMain.MODID)
public class ModMain
{
    public static final String MODID = "mohard";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModMain() {
        ModEnchantmentCategory.register();
        ModBindings.register();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModClientConfig.SPEC, "mohard-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ModServerConfig.SPEC, "mohard-server.toml");
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEssences.ESSENCE_ITEMS.register(eventBus);
        ModEntityTypes.ENTITIES.register(eventBus);
        ModBlocks.BLOCOS.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModBlockStateProperties.register();
        ModModelLayers.register();
        ModParticles.register(eventBus);
        ModContainers.register(eventBus);
        ModAttributes.register(eventBus);
        ModEffects.register(eventBus);
        ModEnchantments.register(eventBus);
        ModTileEntityTypes.register(eventBus);
        ModCreativeTabs.register();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        EssenceDataHelper.setupEssenceMap();
        EssenceDataHelper.setupEntityMap();
        EssenceDataHelper.printEssence();

        ItemProperties.register(ModItems.AMETHYST_AMULET.get(), new ResourceLocation(MODID, "binding"), (p_239427_0_, p_239427_1_, p_239427_2_, p_239427_3_) -> p_239427_2_ == null ? 0 : p_239427_2_.getUseItem() != p_239427_0_ ? 0.0F : p_239427_0_.getItem() instanceof AmethystAmulet amethystAmulet ? amethystAmulet.getPower(p_239427_0_.getUseDuration() - p_239427_2_.getUseItemRemainingTicks()) : 0);

        ItemProperties.register(ModItems.ESSENCE_BUNDLE.get(), new ResourceLocation(MODID, "filled"), (p_239427_0_, p_239427_1_, p_239427_2_, p_239427_3_) -> EssenceBundleItem.getFullnessDisplay(p_239427_0_));
        MenuScreens.register(ModContainers.ALTAR.get(), AltarScreen::new);
        MenuScreens.register(ModContainers.ESSENCE_EXCHANGER.get(), EssenceExchangerScreen::new);
        BlockEntityRenderers.register(ModTileEntityTypes.ALTAR.get(), AltarTileEntityRenderer::new);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        BaseNetwork.init();
        //EntitiesRenderer.layersRegistry();
        //EntitiesRenderer.registryEntityRenderers();
    }


}
