package net.lucca.mohard.entities.corrupter;

import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;

public class CorrupterModel<T extends Corrupter> extends HierarchicalModel<T> implements HeadedModel {

    protected ModelPart head;
    protected ModelPart hat;
    protected final ModelPart root;
    protected ModelPart jacket;
    protected final ModelPart rightLeg;
    protected final ModelPart leftLeg;
    protected final ModelPart rightArm;
    protected final ModelPart leftArm;


    public CorrupterModel(ModelPart modelPart) {
        this.root = modelPart;
        this.jacket = modelPart.getChild("body").getChild("jacket");
        this.jacket.visible = true;
        this.head = modelPart.getChild("head");
        this.hat = this.head.getChild("hat");
        this.rightLeg = modelPart.getChild("right_leg");
        this.leftLeg = modelPart.getChild("left_leg");
        this.rightArm = modelPart.getChild("right_arm");
        this.leftArm = modelPart.getChild("left_arm");


    }

    public static MeshDefinition createBodyModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        float f = 0.5F;

        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0 ,12.5F, 0));
        partdefinition1.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0 ,0F, 0));
        PartDefinition partdefinition3 = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 18).addBox(-4.5F, 10.5F, -4.5F, 9.0F, 9.0F, 9.0F), PartPose.ZERO);
        partdefinition3.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(0, 37).addBox(-4.5F, 10.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.ZERO);;
        partdefinition1.addOrReplaceChild("small_nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -3.0F, -5.0F, 2.0F, 1.0F, 1.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
        partdefinition3.addOrReplaceChild("bag", CubeListBuilder.create().texOffs(40, 39).addBox(-4F, 10.5F, 4.5F, 8.0F, 8.0F, 3.0F), PartPose.ZERO);


        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(41, 28).addBox(-3.0F, 0.0F, -2.0F, 3.0F, 6.0F, 4.0F), PartPose.offset(0.0F, 18.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(41, 28).mirror().addBox(-3.0F, 0.0F, -2.0F, 3.0F, 6.0F, 4.0F), PartPose.offset(3F, 18.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(41, 18).addBox(-4.5F, 0 , -2.0F, 3.0F, 6.0F, 4.0F), PartPose.offset(-3.0F, 12F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(41, 18).mirror().addBox(-0.5F, 0 , -2.0F, 3.0F, 6.0F, 4.0F), PartPose.offset(5.0F, 12F, 0.0F));



        return meshdefinition;
    }

    public void setupAnim(T entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        this.head.y = 12.5F;
        this.head.yRot = p_225597_5_ * ((float) Math.PI / 180F);
        this.head.xRot = p_225597_6_ * ((float) Math.PI / 180F);
        this.head.zRot = 0.0F;



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
            this.head.xRot = Mth.cos(p_225597_2_ * 0.6662F + (float)Math.PI) * (float)Math.PI / 18F * p_225597_3_;

            this.rightArm.xRot = Mth.cos(p_225597_2_ * 2.6662F + (float)Math.PI) * 2.0F * p_225597_3_ * 0.5F;
            this.rightArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = Mth.cos(p_225597_2_ * 2.6662F) * 2.0F * p_225597_3_ * 0.5F;
            this.leftArm.yRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightLeg.xRot = Mth.cos(p_225597_2_ * 2.6662F) * 1.4F * p_225597_3_ * 0.5F;
            this.rightLeg.yRot = 0.0F;
            this.rightLeg.zRot = 0.0F;
            this.leftLeg.xRot = Mth.cos(p_225597_2_ * 2.6662F + (float)Math.PI) * 1.4F * p_225597_3_ * 0.5F;
            this.leftLeg.yRot = 0.0F;
            this.leftLeg.zRot = 0.0F;
        }
        if(!entity.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()){
            this.rightArm.xRot = (-(float)Math.PI);
            this.rightArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = (-(float)Math.PI);
            this.leftArm.yRot = 0.0F;
            this.leftArm.zRot = 0.0F;
        } else if(entity.getPose().equals(Pose.CROUCHING)){
            this.rightArm.xRot = (-(float)Math.PI/4);
            this.rightArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = (-(float)Math.PI/4);
            this.leftArm.yRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.head.y = 13.5F;
        }
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
