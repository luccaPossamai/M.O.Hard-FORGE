package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.world.gen.structure.corrupter.CorrupterWagonPieces;
import net.lucca.mohard.world.gen.structure.iceisolator.IceIsolatorHousePieces;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;

public class ModStructurePieces {

    public static final DeferredRegister<StructurePieceType> DEFERRED_REGISTRY_STRUCTURE_PIECE =
            DeferredRegister.create(Registry.STRUCTURE_PIECE_REGISTRY, ModMain.MODID);

    public static final RegistryObject<StructurePieceType> ICE_ISOLATOR_HOUSE_PIECE = setTemplatePieceId(IceIsolatorHousePieces.IceIsolatorHousePiece::new, "ICISHO");
    public static final RegistryObject<StructurePieceType> CORRUPTER_WAGON_PIECE = setTemplatePieceId(CorrupterWagonPieces.CorrupterWagonPiece::new, "CRRPTWGN");
    public static void register(IEventBus bus) {
        DEFERRED_REGISTRY_STRUCTURE_PIECE.register(bus);
    }

    private static RegistryObject<StructurePieceType> setTemplatePieceId(StructurePieceType.StructureTemplateType p_210156_, String p_210157_) {
        return setFullContextPieceId(p_210156_, p_210157_);
    }
    private static RegistryObject<StructurePieceType> setFullContextPieceId(StructurePieceType p_210159_, String p_210160_) {
        return DEFERRED_REGISTRY_STRUCTURE_PIECE.register(p_210160_.toLowerCase(Locale.ROOT), () -> p_210159_);
    }

}
