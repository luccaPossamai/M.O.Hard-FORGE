package net.lucca.mohard.world.gen.structure.iceisolator;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModEntityTypes;
import net.lucca.mohard.init.ModLootTables;
import net.lucca.mohard.init.ModStructurePieces;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;


public class IceIsolatorHousePieces {

    public static final int GENERATION_HEIGHT = 90;

    static final ResourceLocation STRUCTURE_LOCATION = new ResourceLocation(ModMain.MODID, "ice_isolator_house");

    public static void addPieces(StructureTemplateManager p_227549_, BlockPos p_227550_, Rotation p_227551_, StructurePieceAccessor p_227552_) {
        p_227552_.addPiece(new IceIsolatorHousePiece(p_227549_, STRUCTURE_LOCATION, p_227550_, p_227551_));
    }

    public static class IceIsolatorHousePiece extends TemplateStructurePiece {
        public IceIsolatorHousePiece(StructureTemplateManager p_227555_, ResourceLocation p_227556_, BlockPos p_227557_, Rotation p_227558_) {
            super(ModStructurePieces.ICE_ISOLATOR_HOUSE_PIECE.get(), 0, p_227555_, p_227556_, p_227556_.toString(), makeSettings(p_227558_, p_227556_), p_227557_);
        }

        public IceIsolatorHousePiece(StructureTemplateManager p_227561_, CompoundTag p_227562_) {
            super(ModStructurePieces.ICE_ISOLATOR_HOUSE_PIECE.get(), p_227562_, p_227561_, (p_227589_) -> makeSettings(Rotation.valueOf(p_227562_.getString("Rot")), p_227589_));
        }


        private static StructurePlaceSettings makeSettings(Rotation p_227576_, ResourceLocation p_227577_) {
            return (new StructurePlaceSettings()).setRotation(p_227576_).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext p_227579_, CompoundTag p_227580_) {
            super.addAdditionalSaveData(p_227579_, p_227580_);
            p_227580_.putString("Rot", this.placeSettings.getRotation().name());
        }

        @Override
        protected void handleDataMarker(String p_227582_, BlockPos p_227583_, ServerLevelAccessor p_227584_, RandomSource p_227585_, BoundingBox p_227586_) {
            if ("chest".equals(p_227582_)) {
                p_227584_.setBlock(p_227583_, Blocks.WHITE_CARPET.defaultBlockState(), 3);
                BlockEntity blockentity = p_227584_.getBlockEntity(p_227583_.below());
                if (blockentity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity)blockentity).setLootTable(ModLootTables.ICE_ISOLATOR_HOUSE, p_227585_.nextLong());
                }
            }
            if("isolator".equals(p_227582_)){
                Mob mob = ModEntityTypes.ICE_ISOLATOR.get().create(p_227584_.getLevel());
                mob.setPersistenceRequired();
                mob.moveTo(p_227583_, 0.0F, 0.0F);
                mob.finalizeSpawn(p_227584_, p_227584_.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.STRUCTURE, (SpawnGroupData)null, (CompoundTag)null);
                p_227584_.addFreshEntityWithPassengers(mob);
                p_227584_.setBlock(p_227583_, Blocks.AIR.defaultBlockState(), 2);

            }
        }

        @Override
        public void postProcess(WorldGenLevel p_227568_, StructureManager p_227569_, ChunkGenerator p_227570_, RandomSource p_227571_, BoundingBox p_227572_, ChunkPos p_227573_, BlockPos p_227574_) {
            BlockPos blockpos2 = this.templatePosition;
            super.postProcess(p_227568_, p_227569_, p_227570_, p_227571_, p_227572_, p_227573_, p_227574_);
            this.templatePosition = blockpos2;
        }
    }
}
