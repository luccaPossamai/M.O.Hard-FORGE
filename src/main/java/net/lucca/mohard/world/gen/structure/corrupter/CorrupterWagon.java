package net.lucca.mohard.world.gen.structure.corrupter;

import com.mojang.serialization.Codec;
import net.lucca.mohard.init.ModStructureType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class CorrupterWagon extends Structure{


    private final HeightProvider startHeight = UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90));
    public static final Codec<CorrupterWagon> CODEC = simpleCodec(CorrupterWagon::new);

    public CorrupterWagon(Structure.StructureSettings config){
        super(config);
    }

    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (p_227598_) -> {
            this.generatePieces(p_227598_, context);
        });
    }

    private void generatePieces(StructurePiecesBuilder p_227600_, Structure.GenerationContext p_227601_) {
        ChunkPos chunkpos = p_227601_.chunkPos();
        WorldgenRandom worldgenrandom = p_227601_.random();
        BlockPos blockpos = new BlockPos(chunkpos.getMinBlockX(), this.startHeight.sample(p_227601_.random(), new WorldGenerationContext(p_227601_.chunkGenerator(), p_227601_.heightAccessor())), chunkpos.getMinBlockZ());
        Rotation rotation = Rotation.getRandom(worldgenrandom);
        CorrupterWagonPieces.addPieces(p_227601_.structureTemplateManager(), blockpos, rotation, p_227600_);
    }


    @Override
    public StructureType<?> type() {
        return ModStructureType.CORRUPTER_WAGON_STRUCTURE.get();
    }

}
