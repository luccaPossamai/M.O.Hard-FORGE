package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.block.altar.AltarBlock;
import net.lucca.mohard.block.essenceExchanger.EssenceExchangerBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCOS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MODID);

    public static final RegistryObject<AltarBlock> ENDER_ALTAR = register("ender_altar", () ->
            new AltarBlock(BlockBehaviour.Properties.of(Material.STONE)
            .strength(22.5F,600)
                    .lightLevel(value -> 5)
            .requiresCorrectToolForDrops()
            ));


    public static final RegistryObject<EssenceExchangerBlock> ESSENCE_EXHANGER_BLOCK = register("essence_exchanger", () ->
            new EssenceExchangerBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
            .strength(22.5F,600)
            .sound(SoundType.COPPER)));




    private static <T extends Block> RegistryObject<T> registerNoItem(String nome, Supplier<T> bloco){

        return BLOCOS.register(nome, bloco);
    }

    private static <T extends Block> RegistryObject<T> register(String nome, Supplier<T> bloco){
        RegistryObject<T> ret = registerNoItem(nome, bloco);
        Item.Properties prop = new Item.Properties();
        ModItems.ITEMS.register(nome, ()->
                new BlockItem(ret.get(), prop));

        return ret;
    }

}
