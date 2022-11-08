package net.lucca.mohard.gui.screen;

import net.lucca.mohard.config.ModClientConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.client.gui.GuiComponent.GUI_ICONS_LOCATION;

@Mod.EventBusSubscriber
public class Cancel {

    @SubscribeEvent
    public static void cancelRender(RenderGuiOverlayEvent.Pre event){
        if(ModClientConfig.SPEC.isLoaded()) {
            Player player = Minecraft.getInstance().player;
            NamedGuiOverlay guiOverlay = event.getOverlay();


            boolean isVanillaType = ModClientConfig.clientGuiIcons.get();

            if(!isVanillaType) {
                if (guiOverlay.equals(VanillaGuiOverlay.PLAYER_HEALTH.type())) {
                    event.setCanceled(true);

                }

                if (guiOverlay.equals(VanillaGuiOverlay.ARMOR_LEVEL.type())) {
                    event.setCanceled(true);
                }
            } else {
                if (guiOverlay.overlay().equals(RenderHealth.MODDED_PLAYER_HEALTH_ELEMENT)) {
                    event.setCanceled(true);
                }


                if (guiOverlay.overlay().equals(PlayerGUI.MODDED_ARMOR_ELEMENT)) {
                    event.setCanceled(true);
                }
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, GUI_ICONS_LOCATION);

        }
    }
}
