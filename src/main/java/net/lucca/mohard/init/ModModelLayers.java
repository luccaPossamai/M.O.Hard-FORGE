package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;


public class ModModelLayers {

    public static void register(){}

    public static final ModelLayerLocation NOMAD = new ModelLayerLocation(new ResourceLocation(ModMain.MODID, "nomad"), "main");

    public static final ModelLayerLocation AMETHYST_BOULDER = new ModelLayerLocation(new ResourceLocation(ModMain.MODID, "amethyst_boulder"), "main");

    public static final LayerDefinition PLAYER_FIT_OUTER_ARMOR = LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.5F), 0.0F), 64, 32);

    public static final LayerDefinition PLAYER_FIT_INNER_ARMOR = LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.4F), 0.0F), 64, 32);


}
