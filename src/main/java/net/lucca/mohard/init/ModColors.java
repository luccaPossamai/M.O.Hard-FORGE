package net.lucca.mohard.init;

import net.lucca.mohard.itens.essence.EssenceItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModColors {

    @SubscribeEvent
    public static void setupItemColors(RegisterColorHandlersEvent.Item event){
        event.register((p_210239_0_, p_210239_1_) -> p_210239_1_ > 0 ? -1 : ((DyeableLeatherItem)p_210239_0_.getItem()).getColor(p_210239_0_), ModItems.MAGIC_CHESTPLATE.get(), ModItems.MAGIC_HELMET.get());
        for(RegistryObject<Item> en: ModEssences.ESSENCE_ITEMS.getEntries()) {
            if (en.get() instanceof EssenceItem essence) {
                if(essence.hasOwnTexture()) continue;
                event.register((p_198141_1_, p_198141_2_) -> essence.getColor(p_198141_2_), essence);
            }
        }

    }

}
