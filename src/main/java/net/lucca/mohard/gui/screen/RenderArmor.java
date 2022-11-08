package net.lucca.mohard.gui.screen;

import net.lucca.mohard.util.mechanics.evolution.PlayerEvolution;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;

import static net.lucca.mohard.gui.screen.PlayerGUI.ICON_LOCATION;

public class RenderArmor {



    public static void renderArmor(Gui gui, PoseStack matrixStack, int screenWidth, int screenHeight, Player player) {
        int x = (screenWidth / 2) - 91;
        int y = (screenHeight) - 49;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ICON_LOCATION);
        int value = player.getArmorValue();
        boolean negative = false;
        if (value < 0) {
            value = value * (-1);
            negative = true;
        }
        int contagem = 0;
        while (contagem < 10) {
            if (value >= 2) {
                value = value - 2;
                gui.blit(matrixStack, x + (contagem * 8), y, 19 + (negative ? 18 : 0), 55, 9, 9);
            } else if (value == 1) {
                value = 0;
                gui.blit(matrixStack, x + (contagem * 8), y, 10 + (negative ? 18 : 0), 55, 9, 9);
            } else if (value == 0) {
                gui.blit(matrixStack, x + (contagem * 8), y, 1, 55, 9, 9);
            }
            contagem++;
        }
        contagem = 0;
        while (contagem < 5) {
            if (value >= 2) {
                value = value - 2;
                gui.blit(matrixStack, x + (contagem * 8), y - 10, 19 + (negative ? 18 : 0), 55, 9, 9);
            } else if (value == 1) {
                value = 0;
                gui.blit(matrixStack, x + (contagem * 8), y - 10, 10 + (negative ? 18 : 0), 55, 9, 9);
            } else if (value == 0) {
                gui.blit(matrixStack, x + (contagem * 8), y - 10, 1, 55, 9, 9);
            }
            contagem++;
        }
    }

    public static void renderMagicArmor(Gui gui, PoseStack matrixStack, int screenWidth, int screenHeight, Player player) {
        int x = (screenWidth / 2) - 19;
        int y = (screenHeight) - 59;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ICON_LOCATION);
        int value = (int) Math.round(PlayerEvolution.magicImunne(player) * 10);
        boolean negative = false;
        if (value < 0) {
            value = value * (-1);
            negative = true;
        }
        int contagem = 0;
        while (contagem < 5) {
            if (value >= 2) {
                value = value - 2;
                gui.blit(matrixStack, x - (contagem * 8), y, 19 + (negative ? 18 : 0), 65, 9, 9);
            } else if (value == 1) {
                value = 0;
                gui.blit(matrixStack, x - (contagem * 8), y, 10 + (negative ? 18 : 0), 65, 9, 9);
            } else if (value == 0) {
                gui.blit(matrixStack, x - (contagem * 8), y, 1, 65, 9, 9);
            }
            contagem++;
        }


    }
}
