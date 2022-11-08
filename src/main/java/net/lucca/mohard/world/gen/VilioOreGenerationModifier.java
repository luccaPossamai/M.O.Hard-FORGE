package net.lucca.mohard.world.gen;

import net.lucca.mohard.init.ModBiomeModifiers;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public record VilioOreGenerationModifier(HolderSet<Biome> biomes, Holder<PlacedFeature> feature) implements net.minecraftforge.common.world.BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if(biomes.contains(biome)) {
            if (phase.equals(Phase.BEFORE_EVERYTHING)) {

            }
            if (phase.equals(Phase.ADD)) {
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature);
            }
            if (phase.equals(Phase.REMOVE)) {

            }
            if (phase.equals(Phase.MODIFY)) {

            }
            if (phase.equals(Phase.AFTER_EVERYTHING)) {

            }
        }
    }

    @Override
    public Codec<? extends net.minecraftforge.common.world.BiomeModifier> codec() {
        return ModBiomeModifiers.VILIO_ORE_GENERATION_CODEC.get();
    }
}
