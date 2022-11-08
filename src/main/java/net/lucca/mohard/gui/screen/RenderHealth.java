package net.lucca.mohard.gui.screen;

import net.lucca.mohard.config.ModClientConfig;
import net.lucca.mohard.util.help.Methods;
import net.lucca.mohard.init.ModEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

@OnlyIn(Dist.CLIENT)
public class RenderHealth {

    private static final ResourceLocation ICON_LOCATION = new ResourceLocation("mohard", "textures/gui/mod_icons.png");
    private float lastHealth;
    private int tickCount;
    private long lastHealthTime = 0;
    private long healthBlinkTime = 0;

    public static final IGuiOverlay MODDED_PLAYER_HEALTH_ELEMENT = (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
        if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements())
        {
            gui.setupOverlayRenderState(true, false);
            boolean showDigits = false;
            int digitsColor = 865636;
            if(ModClientConfig.SPEC.isLoaded()) {
                showDigits = ModClientConfig.showHealthAmount.get();
                digitsColor = ModClientConfig.healthAmountDisplayColor.get();
            }
            new RenderHealth().renderModHealth(gui, mStack, screenWidth, screenHeight, (Player) Minecraft.getInstance().getCameraEntity(), showDigits, digitsColor);
        }
    };

    public void renderModHealth(Gui gui, PoseStack matrixStack, int screenWidth, int screenHeight, Player player, boolean showDigits, int digitsColor) {
        Minecraft.getInstance().getProfiler().push("health");
        this.tickCount = gui.getGuiTicks();
        if(PlayerHudTicks.PLAYER_HUD_LAST_VARIABLES.containsKey((LocalPlayer) player)){
            PlayerHudTicks.HudTickUpdates hudTickUpdates = PlayerHudTicks.PLAYER_HUD_LAST_VARIABLES.get((LocalPlayer) player);
            this.lastHealth = hudTickUpdates.lastHealth;
            this.healthBlinkTime = hudTickUpdates.healthBlinkTime;
            this.lastHealthTime = hudTickUpdates.lastHealthTime;
        } else {
            PlayerHudTicks.updateLastHudVariables((LocalPlayer) player);
        }

        int health = Mth.ceil(player.getHealth());
        double maxHealth = player.getMaxHealth();
        boolean highlight = healthBlinkTime > (long) tickCount && (healthBlinkTime - (long) tickCount) / 3L % 2L == 1L;

        if (health < this.lastHealth && player.invulnerableTime > 0)
        {
            this.lastHealthTime = Util.getMillis();
            this.healthBlinkTime = (long) (this.tickCount + 20);
        }
        else if (health > this.lastHealth && player.invulnerableTime > 0)
        {
            this.lastHealthTime = Util.getMillis();
            this.healthBlinkTime = (long) (this.tickCount + 10);
        }

        if (Util.getMillis() - this.lastHealthTime > 1000L)
        {
            this.lastHealth = health;
            this.lastHealthTime = Util.getMillis();
        }

        int x = (screenWidth / 2) - 91;
        int y = (screenHeight) - 39;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ICON_LOCATION);
        int percentual = (int) Math.ceil((health / maxHealth) * 20);
        if (percentual > 20) {
            percentual = 20;
        }
        Component healthString = Methods.stringToText((int) Math.ceil((health)) + "");
        if (player.hasEffect(MobEffects.ABSORPTION)) {
            healthString = Methods.stringToText((int) Math.ceil((health)) + " + " + (int) Math.ceil((player.getAbsorptionAmount())));
        }
        int t = getHealthType(player);
        for (int u = 0; u < 10; u++) {
            gui.blit(matrixStack, x + (u * 8), y, 1 + (getX(highlight, true, false) * 10), (t * 9), 9, 9);
        }
        int hardCore = player.getLevel().getLevelData().isHardcore() ? 54 : 0;
        for (int i = 0; i < 10; i++) {
            int j2 = i * 2;
            boolean flag = (highlight) && j2 < lastHealth ? j2 + 1 == lastHealth : j2 < health && j2 + 1 == health;
            int wave = ( flag && (health < maxHealth) && ((tickCount % 35) - 1) == i) ? 2 : 0;

            if (percentual >= 2) {
                percentual = percentual - 2;
                gui.blit(matrixStack, x + 1 + (i * 8), y + 1 - wave, 32 + hardCore + (getX( highlight, false, flag) * 9), 1 + (t * 9), 7, 7);

            } else if (percentual == 1) {
                percentual--;
                gui.blit(matrixStack, x + 1 + (i * 8), y + 1 - wave, 59 + hardCore + (getX(highlight, false, flag) * 9), 1 + (t * 9), 7, 7);

            }
        }
        if(showDigits) {
            float xM = (float) (x + 41 - gui.getFont().width(healthString.getVisualOrderText()) / 2);
            float yM = (float) y - 1;
            RenderSystem.defaultBlendFunc();
            gui.getFont().draw(matrixStack, healthString.getVisualOrderText(), xM - 1, yM, 723723);
            gui.getFont().draw(matrixStack, healthString.getVisualOrderText(), xM + 1, yM, 723723);
            gui.getFont().draw(matrixStack, healthString.getVisualOrderText(), xM, yM - 1, 723723);
            gui.getFont().draw(matrixStack, healthString.getVisualOrderText(), xM, yM + 1, 723723);
            gui.getFont().draw(matrixStack, healthString.getVisualOrderText(), xM, yM, getHealthTypeColor(player, digitsColor));
        }
        lastHealth = health;

        Minecraft.getInstance().getProfiler().pop();
        if(player.isAlive()) {
            PlayerHudTicks.updateHealth((LocalPlayer) player, this.lastHealth, this.healthBlinkTime, this.lastHealthTime);
        }
    }

    private int getX(boolean blinking, boolean container, boolean p){
        int i;
        if(container){
            i = blinking ? p ? 2 : 0 : 1;
        } else {
            int k = blinking ? 2 : 1;
            int j = p ? 2 - k : 0;
            i = j + k;
        }

        return i;
    }

    private int getHealthType(Player player){
        return  player.isOnFire() || player.hasEffect(ModEffects.INCINERATION.get()) ? 5 :
                player.isFullyFrozen() ? 4 :
                player.hasEffect(MobEffects.WITHER) ? 3 :
                player.hasEffect(MobEffects.POISON) ? 1 :
                        player.hasEffect(MobEffects.ABSORPTION) ? 2 :
                                0;
    }

    private int getHealthTypeColor(Player player, int digitsColor){
        int count = getHealthType(player);
        return switch (count) {
            case 1 -> 9144082;
            case 2 -> 14596157;
            case 3 -> 3871507;
            case 4 -> 4571118;
            case 5 -> 12077327;
            default -> digitsColor;
        };
    }


}
