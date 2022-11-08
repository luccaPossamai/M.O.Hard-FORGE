package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.world.gen.VilioOreGenerationModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeModifiers {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS , ModMain.MODID);

    public static final RegistryObject<Codec<? extends VilioOreGenerationModifier>> VILIO_ORE_GENERATION_CODEC = BIOME_MODIFIERS.register("vilio_ore_generation", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(VilioOreGenerationModifier::biomes),
                    PlacedFeature.CODEC.fieldOf("feature").forGetter(VilioOreGenerationModifier::feature)
                    ).apply(builder, VilioOreGenerationModifier::new)));

}
