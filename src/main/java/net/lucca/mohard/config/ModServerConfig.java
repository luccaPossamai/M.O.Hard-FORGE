package net.lucca.mohard.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> freezeEvolution;

    static {
        BUILDER.push("Server configuration to moh");

        freezeEvolution = BUILDER.comment("Mobs couldn't evolve based on player's level.").define("FreezeEvolution", false);



        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
