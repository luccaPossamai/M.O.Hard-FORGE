package net.lucca.mohard.entities.villagers.nomad;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class SpiritualNomadModel<T extends SpiritualNomadEntity> extends HierarchicalModel<T> implements HeadedModel, VillagerHeadModel {
    protected ModelPart head;
    protected ModelPart hat;
    protected final ModelPart root;
    protected final ModelPart rightLeg;
    protected final ModelPart leftLeg;
    protected final ModelPart arms;
    protected final ModelPart nose;
    protected final ModelPart rightArm;
    protected final ModelPart leftArm;

    public SpiritualNomadModel(ModelPart modelPart) {
        this.root = modelPart;
        this.head = modelPart.getChild("head");
        this.hat = this.head.getChild("hat");
        this.nose = this.head.getChild("nose");
        this.rightLeg = modelPart.getChild("right_leg");
        this.leftLeg = modelPart.getChild("left_leg");
        this.rightArm = modelPart.getChild("right_arm");
        this.leftArm = modelPart.getChild("left_arm");
        this.arms = modelPart.getChild("arms");
    }

    public static MeshDefinition createBodyModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        float f = 0.5F;
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.ZERO);
        PartDefinition partdefinition2 = partdefinition1.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        partdefinition1.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition partdefinition3 = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F), PartPose.ZERO);
        partdefinition3.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        partdefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(44, 22).addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, true).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));

        return meshdefinition;
    }

    public void setupAnim(T entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        SpiritualNomadState state = SpiritualNomadState.NORMAL;
        boolean denying = false;
        if (entity instanceof SpiritualNomadEntity) {
            state = entity.getArmState();
            denying = entity.isDenying();
        }

        this.head.yRot = p_225597_5_ * ((float) Math.PI / 180F);
        this.head.xRot = p_225597_6_ * ((float) Math.PI / 180F);
        this.head.zRot = 0.0F;

        this.arms.y = 3.0F;
        this.arms.z = -1.0F;
        this.arms.xRot = -0.75F;
        this.rightLeg.xRot = Mth.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_ * 0.5F;
        this.leftLeg.xRot = Mth.cos(p_225597_2_ * 0.6662F + (float) Math.PI) * 1.4F * p_225597_3_ * 0.5F;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
        if (this.riding) {
            this.rightArm.xRot = (-(float)Math.PI / 5F);
            this.rightArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = (-(float)Math.PI / 5F);
            this.leftArm.yRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
        } else {
            this.rightArm.xRot = Mth.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 2.0F * p_225597_3_ * 0.5F;
            this.rightArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = Mth.cos(p_225597_2_ * 0.6662F) * 2.0F * p_225597_3_ * 0.5F;
            this.leftArm.yRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightLeg.xRot = Mth.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_ * 0.5F;
            this.rightLeg.yRot = 0.0F;
            this.rightLeg.zRot = 0.0F;
            this.leftLeg.xRot = Mth.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_225597_3_ * 0.5F;
            this.leftLeg.yRot = 0.0F;
            this.leftLeg.zRot = 0.0F;
        }

        if (denying) {

            this.head.yRot = 0.3F * Mth.sin(0.45F * p_225597_4_ / 2);
            this.head.xRot = 0.4F;

        }
        if(state.isCasting()){

            this.rightArm.z = 0.0F;
            this.rightArm.x = -5.0F;
            this.leftArm.z = 0.0F;
            this.leftArm.x = 5.0F;
            this.rightArm.xRot = (float) (-(Math.PI / 12) + (Mth.cos(p_225597_4_ * 1 / 6) * -0.7894));
            this.leftArm.xRot = this.rightArm.xRot;
            this.rightArm.zRot = (float) ((2 * Math.PI / 3) - (Mth.sin(p_225597_4_ * 1 / 6) * -0.7894));
            this.leftArm.zRot = (float) (-(2 * Math.PI / 3) + (Mth.sin(p_225597_4_ * 1 / 6) * -0.7894));
            this.rightArm.yRot = 0.0F;
            this.leftArm.yRot = 0.0F;

        } else if(state.isVanishing()){

            this.rightArm.z = 0.0F;
            this.rightArm.x = -5.0F;
            this.leftArm.z = 0.0F;
            this.leftArm.x = 5.0F;
            this.rightArm.xRot = Mth.cos(p_225597_4_ * 0.6662F) * 0.25F;
            this.leftArm.xRot = Mth.cos(p_225597_4_ * 0.6662F) * 0.25F;
            this.rightArm.zRot = 2.3561945F;
            this.leftArm.zRot = -2.3561945F;
            this.rightArm.yRot = 0.0F;
            this.leftArm.yRot = 0.0F;
        }

        boolean flag = state.isCrossed();
        this.arms.visible = flag;
        this.leftArm.visible = !flag;
        this.rightArm.visible = !flag;
    }

    public ModelPart getHead() {
        return this.head;
    }

    public void hatVisible(boolean p_217146_1_) {
        this.head.visible = p_217146_1_;
        this.hat.visible = p_217146_1_;
    }


    @Override
    public ModelPart root() {
        return this.root;
    }
}
