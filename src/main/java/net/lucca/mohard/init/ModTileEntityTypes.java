package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.block.altar.AltarTileEntity;
import net.lucca.mohard.block.essenceExchanger.EssenceExchangerTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModTileEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModMain.MODID);

    public static final RegistryObject<BlockEntityType<AltarTileEntity>> ALTAR = TILE_ENTITIES.register("altar", () ->
            BlockEntityType.Builder.of(AltarTileEntity::new, ModBlocks.VILIO_ALTAR.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<EssenceExchangerTileEntity>> ESSENCE_EXCHANGER = TILE_ENTITIES.register("essence_exchanger", () ->
            BlockEntityType.Builder.of(EssenceExchangerTileEntity::new, ModBlocks.ESSENCE_EXHANGER_BLOCK.get()).build(null)
    );



    public static void register(IEventBus bus){
        TILE_ENTITIES.register(bus);
    }


}
