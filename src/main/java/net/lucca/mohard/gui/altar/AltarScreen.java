package net.lucca.mohard.gui.altar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.lucca.mohard.block.altar.GUI.AltarMenu;
import net.lucca.mohard.util.help.Methods;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Inventory;

public class AltarScreen extends AbstractContainerScreen<AltarMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation("mohard", "textures/gui/altar.png");
    private boolean showingStats = false;

    public AltarScreen(AltarMenu p_i51093_1_, Inventory p_i51093_2_, Component p_i51093_3_) {
        super(p_i51093_1_, p_i51093_2_, p_i51093_3_);
    }

    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        initButton();

    }

    public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        bind();
        RenderSystem.enableBlend();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(p_230450_1_, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if(this.showingStats) {
            for (int k = 0; k < 8; k++) {
                if(Minecraft.getInstance().player == null) break;
                bind();
                this.blit(p_230450_1_, i - 40, j + 1 + (k * 20), 0, 166, 40, 21);
                this.blit(p_230450_1_, i - 44, j + 4 + (k * 20), 41 + (k * 13), 166, 13, 13);
                Attribute att = Methods.getAttributes().get(k);
                double stat = Minecraft.getInstance().player.getAttributeBaseValue(att);
                Component message = Component.literal(""+(int)stat);
                this.font.draw(p_230450_1_, message.getVisualOrderText(), (float)(i - 18 - this.font.width(message.getVisualOrderText()) / 2), (float) j + 7 + (k * 20), 0);

            }
        }

    }

    public void bind(){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_LOCATION);
    }
    public void initButton(){
        AltarButton button = new AltarButton(this);
        button.setPosition(((this.width - this.imageWidth) / 2) + 5, ((this.height - this.imageHeight) / 2 ) + 5);
        this.addRenderableWidget(button);
    }
    public boolean isShowingStats() {
        return showingStats;
    }

    public void setShowingStats(boolean showingStats) {
        this.showingStats = showingStats;
    }

}