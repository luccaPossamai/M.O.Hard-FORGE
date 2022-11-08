package net.lucca.mohard.data.client;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.init.ModBanners;
import net.lucca.mohard.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModBannerPatternTagsProvider extends BannerPatternTagsProvider {

    public ModBannerPatternTagsProvider(DataGenerator p_236411_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_236411_, ModMain.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        super.addTags();
        tag(ModTags.BannerPatterns.PATTERN_ITEM_ESSENCE).add(ModBanners.ESSENCE.get());
        tag(ModTags.BannerPatterns.PATTERN_ITEM_NOMADS).add(ModBanners.NOMADS.get());
    }
}
