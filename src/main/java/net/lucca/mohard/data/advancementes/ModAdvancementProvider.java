package net.lucca.mohard.data.advancementes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModAdvancementProvider extends AdvancementProvider {


    public ModAdvancementProvider(PackOutput p_256529_, CompletableFuture<HolderLookup.Provider> p_255722_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256529_, p_255722_, List.of(new EssentialStart()), existingFileHelper);
    }




}
