package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.itens.custom.CustomItem;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ModCreativeTabs {

    public static void register(){}

    private static final List<RegistryObject<Item>> ARTIFACTS_LIST = List.of(
            ModItems.WHEEL_OF_THE_UNKNOWN_GOD,
            ModItems.TOTEM_OF_THE_DEAD,
            ModItems.AMETHYST_AMULET,
            ModItems.FLOYD_DAGGER,
            ModItems.ALGID_AXE,
            ModItems.SOUL_FIREBALL_SCEPTER,
            ModItems.DEEP_NETHER_PETRIFIED_TOME
    );

    public static final Consumer<CreativeModeTab.Builder> ESSENCES = builder -> {
        builder.icon(() -> new ItemStack(ModEssences.ZOMBIE_ESSENCE.get(), 1));
        builder.title(Component.translatable("itemGroup.essences"));
        builder.displayItems((featureFlagSet, output, hasOp) -> {
            Arrays.stream(ModEssences.class.getFields()).filter(field -> field.getType() == RegistryObject.class).forEach(field -> {
                try {
                    RegistryObject<EssenceItem> item = (RegistryObject)field.get(null);
                    output.accept(new ItemStack(item.get()));
                } catch (IllegalAccessException e) {
                    ModMain.LOGGER.error("Unable to add item to EssenceTab");
                }
            });
        });
    };

    public static final Consumer<CreativeModeTab.Builder> ARTIFACTS = builder -> {
        builder.icon(() -> new ItemStack(ModItems.WHEEL_OF_THE_UNKNOWN_GOD.get(), 1));
        builder.title(Component.translatable("itemGroup.artifacts"));
        builder.displayItems((featureFlagSet, output, hasOp) -> {
            ARTIFACTS_LIST.stream().map(RegistryObject::get).forEach(item -> output.accept(new ItemStack(item)));
            Arrays.stream(ModCustomItems.class.getFields()).filter(field -> field.getType() == CustomItem.class).forEach(field -> {
                try {
                    CustomItem<?> item = (CustomItem<?>) field.get(null);
                    output.accept(item.getItemStack());
                } catch (IllegalAccessException e) {
                    ModMain.LOGGER.error("Unable to add item to ArtifactsTab");
                }
            });

        });
    };
    @Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registry{

        @SubscribeEvent
        public static void register(CreativeModeTabEvent.Register event){
            event.registerCreativeModeTab(mcLoc("essences"), ESSENCES);
            event.registerCreativeModeTab(mcLoc("artifacts"), ARTIFACTS);
        }

        @SubscribeEvent
        public static void addContents(CreativeModeTabEvent.BuildContents event){
            if(event.getTab().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)){
                event.accept(ModBlocks.ENDER_ALTAR);
                event.accept(ModBlocks.ESSENCE_EXHANGER_BLOCK);
            }
            if(event.getTab().equals(CreativeModeTabs.COMBAT)){
                event.accept(ModItems.MAGIC_HELMET);
                event.accept(ModItems.MAGIC_CHESTPLATE);
            }
            if(event.getTab().equals(CreativeModeTabs.INGREDIENTS)){
                event.accept(ModItems.SOUL_FIRE_CHARGE);
            }
        }

        private static ResourceLocation mcLoc(String s){
            return new ResourceLocation(ModMain.MODID, s);
        }


    }
}
