package net.lucca.mohard.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModClientConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> clientGuiIcons;
    public static final ForgeConfigSpec.ConfigValue<Boolean> hidenEssenceStats;
    public static final ForgeConfigSpec.ConfigValue<Boolean> showHealthAmount;
    public static final ForgeConfigSpec.ConfigValue<Integer> healthAmountDisplayColor;

    static {
        BUILDER.push("Client Configuration for M.O.Hard");

        clientGuiIcons = BUILDER.comment("Vanilla style of hearts and armor level.").define("VanillaStyle", true);
        hidenEssenceStats = BUILDER.comment("Hide the essence stats tootip. Hold [SHIFT] to show.").define("HiddenStats", false);
        showHealthAmount = BUILDER.comment("Show the amount of health above the health bar.").define("HealthAmountDisplay", false);
        healthAmountDisplayColor = BUILDER.comment("Sets the base color for the health display above the bar. Require the HealthAmountDisplay as true").define("HealthAmountColor", 865636);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
