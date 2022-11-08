package net.lucca.mohard.gui.screen;

import net.lucca.mohard.util.mechanics.evolution.PlayerEvolution;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerGUI{

    public static final ResourceLocation ICON_LOCATION = new ResourceLocation("mohard", "textures/gui/mod_icons.png");




    public static final IGuiOverlay MODDED_ARMOR_ELEMENT = (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
        if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements() && shouldRenderArmor())
        {
            gui.setupOverlayRenderState(true, false);
            RenderArmor.renderArmor(gui, mStack, screenWidth, screenHeight, Minecraft.getInstance().player);
            RenderArmor.renderMagicArmor(gui, mStack, screenWidth, screenHeight, Minecraft.getInstance().player);
        }
    };

    @SubscribeEvent
    public static void register(RegisterGuiOverlaysEvent event) {

        event.registerAbove(new ResourceLocation("minecraft", "player_health"),
                "mod_player_health",
                RenderHealth.MODDED_PLAYER_HEALTH_ELEMENT);

        event.registerAbove(new ResourceLocation("minecraft", "armor_level"),
                "mod_armor_level",
                MODDED_ARMOR_ELEMENT);

    }





    public static boolean shouldRenderArmor(){
        if(Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            return player.getArmorValue() != 0 || PlayerEvolution.magicImunne(player) != 0D;
        }
        return false;
    }
}
