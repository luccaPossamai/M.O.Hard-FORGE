package net.lucca.mohard.data;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.data.advancementes.ModAdvancementProvider;
import net.lucca.mohard.data.client.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;


@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {


    private DataGenerators(){}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        gen.addProvider(true, new ModAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
        gen.addProvider(true, new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(true, new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(true, new ModLootTableProvider(packOutput));
        gen.addProvider(true, new ModRecipeProvider(packOutput));
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        gen.addProvider(true, blockTags);
        gen.getVanillaPack(true).addProvider(p_253851_ -> new ModBlockTagsProvider(p_253851_, event.getLookupProvider(), existingFileHelper));
        gen.addProvider(true, new ModItemTagsProvider(packOutput,lookupProvider, blockTags, existingFileHelper));
}



}
