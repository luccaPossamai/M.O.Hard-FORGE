package net.lucca.mohard.gui.essenceExchanger;

import net.lucca.mohard.block.essenceExchanger.EssenceExchangerMenu;
import net.lucca.mohard.block.essenceExchanger.EssenceExchangerTileEntity;
import net.lucca.mohard.util.help.Methods;
import net.lucca.mohard.itens.essence.EssenceItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EssenceExchangerScreen extends AbstractContainerScreen<EssenceExchangerMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation("mohard", "textures/gui/essence_exchanger.png");

    public EssenceExchangerScreen(EssenceExchangerMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(p_97787_, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int binds = this.menu.getBinds();
        if(binds > 0) {
            this.blit(p_97787_, i + 43, j + 53, 178, 20, 6 * binds, 4);

        }
        if(this.menu.isActive()){
            int time = (int) Math.floor((this.menu.getTime() % 60) / 3);
            this.blit(p_97787_, i + 66, j + 35, 179, 0, 0 + time, 16);
        }
        renderBodyParts(p_97787_, i, j, this.menu.getTime());
        renderEntityName(p_97787_, (this.width / 2), j);

    }
    public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    private void renderBodyParts(PoseStack p_97787_, int i, int j, int time){

        int timeMult = time;
        double singlePartTime = EssenceExchangerTileEntity.TIME_TO_EXCHANGE / 6D;

        double pixels12 = 12 / singlePartTime;
        double pixels8 = 8 / singlePartTime;

        //left leg
        if(time >= singlePartTime * 0) {
            timeMult = (int) (time - singlePartTime * 0);
            timeMult = (int) Math.floor(timeMult * pixels12);
            if(timeMult > 12) timeMult = 12;
            this.blit(p_97787_, i + 87, j + 47, 187, 40, timeMult, 4);
        }

        //left arm
        if(time >= singlePartTime * 1) {
            timeMult = (int) (time - singlePartTime * 1);
            timeMult = (int) Math.floor(timeMult * pixels12);
            if(timeMult > 12) timeMult = 12;
            this.blit(p_97787_, i + 87, j + 39, 187, 30, timeMult, 4);
        }

        //BODY
        if(time >= singlePartTime * 2) {
            timeMult = (int) (time - singlePartTime * 2);
            timeMult = (int) Math.floor(timeMult * pixels12);
            if(timeMult > 12) timeMult = 12;
            this.blit(p_97787_, i + 102, j + 39 + (12 - timeMult), 178, 34 + (12 - timeMult), 8, timeMult);
        }

        //right leg
        if(time >= singlePartTime * 3) {
            timeMult = (int) (time - singlePartTime * 3);
            timeMult = (int) Math.floor(timeMult * pixels12);
            if(timeMult > 12) timeMult = 12;
            this.blit(p_97787_, i + 113, j + 47, 187, 35, timeMult, 4);
        }

        //right arm
        if(time >= singlePartTime * 4) {
            timeMult = (int) (time - singlePartTime * 4);
            timeMult = (int) Math.floor(timeMult * pixels12);
            if(timeMult > 12) timeMult = 12;
            this.blit(p_97787_, i + 113, j + 39, 187, 25, timeMult, 4);
        }

        //HEAD
        if(time >= singlePartTime * 5) {
            timeMult = (int) (time - singlePartTime * 5);
            timeMult = (int) Math.floor(timeMult * pixels8);
            if(timeMult > 8) timeMult = 8;
            this.blit(p_97787_, i + 102, j + 28 + (8 - timeMult), 178, 25 + (8 - timeMult), 8, timeMult);
        }
    }

    private void renderEntityName(PoseStack p_98889_, int i, int j) {
        Component name = getEntityName();
        this.font.draw(p_98889_, name, (float)i - (this.font.width(name) / 2), (float)j + 17, 6893635);
    }

    private Component getEntityName(){
        EssenceItem essenceItem = this.menu.getEssenceItem();
        Component name;
        if(essenceItem != null){
            name = Methods.formatEntity(essenceItem.getType());
        } else {
            name = Methods.stringToText("Empty");
        }
        return name;
    }
}
