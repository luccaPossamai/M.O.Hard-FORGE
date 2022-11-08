package net.lucca.mohard.init;

import net.lucca.mohard.groups.*;
import net.minecraft.world.item.CreativeModeTab;

public class ModItemGroups {

    public static void register(){}

    public static final CreativeModeTab ESSENCE_TAB = new EssenceTab();

    public static final CreativeModeTab ARTIFACTS = new ArtifactsTab();
    public static final CreativeModeTab EQUIPMENT = new EquipmentTab();

    public static final CreativeModeTab MISC = new MiscGroup();

}
