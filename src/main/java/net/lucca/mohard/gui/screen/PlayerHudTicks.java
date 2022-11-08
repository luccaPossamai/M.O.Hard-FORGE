package net.lucca.mohard.gui.screen;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class PlayerHudTicks {
    protected static Map<LocalPlayer, HudTickUpdates> PLAYER_HUD_LAST_VARIABLES = new HashMap<>();

    public static void updateLastHudVariables(LocalPlayer player){
        PLAYER_HUD_LAST_VARIABLES.put(player, new HudTickUpdates(player.getHealth(), 0L, 0L));
    }

    public static void updateHealth(LocalPlayer player, float lastHealth, long healthBlinkTime, long lastHealthTime){
        PLAYER_HUD_LAST_VARIABLES.put(player, new HudTickUpdates(lastHealth, healthBlinkTime, lastHealthTime));
    }

    protected static class HudTickUpdates{

        float lastHealth;
        long healthBlinkTime;
        long lastHealthTime;

        public HudTickUpdates(float lastHealth, long healthBlinkTime, long lastHealthTime){
            this.lastHealth = lastHealth;
            this.healthBlinkTime = healthBlinkTime;
            this.lastHealthTime = lastHealthTime;
        }
    }

}
