package net.lucca.mohard.gui.altar;

import net.lucca.mohard.util.mechanics.evolution.PlayerEvolution;
import net.lucca.mohard.util.help.Methods;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class AltarButton extends AbstractWidget {

    private final ResourceLocation ALTAR_GUI = new ResourceLocation("mohard", "textures/gui/altar.png");
    private final AltarScreen screen;

    public AltarButton(AltarScreen altarScreen) {
        super( 0, 0, 11, 11, Methods.stringToText(""));
        this.screen = altarScreen;
    }

    @Override
    public void renderButton(PoseStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        Minecraft minecraft = Minecraft.getInstance(); RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ALTAR_GUI);
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(p_230431_1_, this.x, this.y, 0, 176 + (i * 11), 11, 11);
        this.renderBg(p_230431_1_, minecraft, p_230431_2_, p_230431_3_);
    }

    protected int getYImage(boolean p_230989_1_) {
        if (!this.active) {
            return 2;
        } else if (p_230989_1_) {
            return 1;
        }
        return 0;
    }

    public void setPosition(int xPos, int yPos){
        x = xPos;
        y = yPos;
    }

    @Override
    protected boolean isValidClickButton(int p_230987_1_) {
        return true;
    }

    @Override
    public void onClick(double p_230982_1_, double p_230982_3_) {
        NonNullList<ItemStack> lista = NonNullList.withSize(9, ItemStack.EMPTY);
        for(int i = 0; i < 9; i++){
            lista.set(i, this.screen.getMenu().slots.get(i).getItem());
        }
        this.screen.setShowingStats(!this.screen.isShowingStats());
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {

    }
}
