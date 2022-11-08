package net.lucca.mohard.entities.dummy;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class DummyModel<T extends DummyEntity> extends HumanoidModel<T> {


    public DummyModel(ModelPart p_170821_) {
        super(p_170821_);
        this.hat.visible = false;
        this.rightArm.zRot = (float) Math.PI / 2;
        this.leftArm.zRot = (-1) * this.rightArm.zRot;
    }

    public static MeshDefinition createMesh(CubeDeformation p_170682_) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_170682_), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 23).addBox(-8.0F, 0.0F, -2.0F, 16.0F, 4.0F, 4.0F, p_170682_), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("second_body", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, 4.0F, -2.0F, 8.0F, 8.0F, 4.0F, p_170682_), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("fiapos", CubeListBuilder.create().texOffs(25, 32).addBox(-9.0F, 0.0F, -2.0F, 1.0F, 4.0F, 4.0F, p_170682_), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("fiapos2", CubeListBuilder.create().texOffs(35, 32).addBox(8.0F, 0.0F, -2.0F, 1.0F, 4.0F, 4.0F, p_170682_), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("fiapos3", CubeListBuilder.create().texOffs(25, 42).addBox(-4.0F, 12.0F, -2.0F, 8.0F, 2.0F, 4.0F, p_170682_), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_170682_.extend(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(10, 17).addBox(-1.0F, -16.0F, -1.0F, 1.0F, 3.0F, 1.0F, p_170682_), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(15, 17).mirror().addBox(-1.0F, -16.0F, -1.0F, 1.0F, 3.0F, 1.0F, p_170682_), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 45).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(-1.9F, 12.0F, 0.0F));
        rightLeg.addOrReplaceChild("right_leg_top", CubeListBuilder.create().texOffs(0, 45).addBox(0.0F, -10.0F, -2.0F, 4.0F, 4.0F, 4.0F, p_170682_), PartPose.offset(-1.9F, 12.0F, 0.0F));
        rightLeg.addOrReplaceChild("right_leg_stick", CubeListBuilder.create().texOffs(17, 52).addBox(1.0F, -12.0F, 0.0F, 1.0F, 8.0F, 1.0F, p_170682_), PartPose.offset(-1.9F, 12.0F, 0.0F));


        PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(1.9F, 12.0F, 0.0F));
        leftLeg.addOrReplaceChild("left_leg_top", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-4.0F, -10.0F, -2.0F, 4.0F, 4.0F, 4.0F, p_170682_), PartPose.offset(1.9F, 12.0F, 0.0F));
        leftLeg.addOrReplaceChild("left_leg_stick", CubeListBuilder.create().texOffs(17, 52).mirror().addBox(-2.0F, -12.0F, 0.0F, 1.0F, 8.0F, 1.0F, p_170682_), PartPose.offset(1.9F, 12.0F, 0.0F));

       return meshdefinition;
    }

    @Override
    public void setupAnim(T p_102866_, float p_102867_, float p_102868_, float p_102869_, float p_102870_, float p_102871_) {

    }
}
