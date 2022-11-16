package net.lucca.mohard.entities.amethystBoulder;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class AmethystBoulderModel<T extends AmethystBoulder> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart main;

    public AmethystBoulderModel(ModelPart p_170916_) {
        this.root = p_170916_;
        this.main = p_170916_.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        //-8, -16, -8
        partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), PartPose.offset(0,-8,0));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public ModelPart root() {
        return this.root;
    }


    private double log(float tick){
        double base = 1.1D;
        return Math.log(tick + 1) / Math.log(base);
    }

    @Override
    public void setupAnim(T p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float tick) {
        if(!p_102618_.isNoGravity() && !p_102618_.inGround()) {
            this.main.zRot = (float) (-5 * (log(tick)) * (Math.PI / 180F));
        }
    }
}
