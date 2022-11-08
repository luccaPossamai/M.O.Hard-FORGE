package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.block.altar.AltarBlock;
import net.lucca.mohard.block.essenceExchanger.EssenceExchangerBlock;
import net.lucca.mohard.block.ore.EssenceOre;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCOS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MODID);

    public static final RegistryObject<AltarBlock> VILIO_ALTAR = register("vilio_altar", () ->
            new AltarBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
            .strength(22.5F,600)
            .sound(SoundType.NETHER_BRICKS)
            .requiresCorrectToolForDrops()
            ), ModItemGroups.ESSENCE_TAB);


    public static final RegistryObject<EssenceExchangerBlock> ESSENCE_EXHANGER_BLOCK = register("essence_exchanger", () ->
            new EssenceExchangerBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
            .strength(22.5F,600)
            .sound(SoundType.COPPER)), ModItemGroups.ESSENCE_TAB);


    public static final RegistryObject<Block> VILIO_BLOCK = register("vilio_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.COPPER)), ModItemGroups.MISC);

    public static final RegistryObject<Block> VILIO_ORE = register("vilio_ore", () ->
            new EssenceOre(EssenceOre.Type.STONE, BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), UniformInt.of(3, 7)), ModItemGroups.ESSENCE_TAB);

    public static final RegistryObject<Block> DEEPSLATE_VILIO_ORE = register("deepslate_vilio_ore", () ->
            new EssenceOre(EssenceOre.Type.DEEPSLATE, BlockBehaviour.Properties.of(Material.STONE).color(MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3.0F), UniformInt.of(3, 7)), ModItemGroups.ESSENCE_TAB);


    public static final RegistryObject<Block> TENDER_OBSIDIAN = register("tender_obsidian", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(30.0F, 720.0F)), CreativeModeTab.TAB_BUILDING_BLOCKS);




    private static <T extends Block> RegistryObject<T> registerNoItem(String nome, Supplier<T> bloco){

        return BLOCOS.register(nome, bloco);
    }

    private static <T extends Block> RegistryObject<T> register(String nome, Supplier<T> bloco, @Nullable CreativeModeTab group){
        RegistryObject<T> ret = registerNoItem(nome, bloco);
        Item.Properties prop = new Item.Properties();
        if(group != null) {
            prop.tab(group);
        }
        ModItems.ITEMS.register(nome, ()->
                new BlockItem(ret.get(), prop));

        return ret;
    }

}
