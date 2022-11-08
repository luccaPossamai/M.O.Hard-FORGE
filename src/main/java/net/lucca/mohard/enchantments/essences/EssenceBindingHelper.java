package net.lucca.mohard.enchantments.essences;

import net.lucca.mohard.capabilities.ModCapabilities;
import net.lucca.mohard.init.ModBindings;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EssenceBindingHelper {

    public static boolean canBind(ItemStack stack){
        return !stack.getOrCreateTag().contains("EntailBinding");
    }

    public static void bind(ItemStack stack, EssenceEntailBinding binding){
        stack.getOrCreateTag().putString("EntailBinding", binding.getDescription());
    }

    public static void updateEntail(NonNullList<ItemStack> lista, Player player) {
        EssenceEntailBinding essenceEnchantment = getListEntailBinding(lista);
        if(essenceEnchantment != null){
            player.getCapability(ModCapabilities.ENTAIL_CAPABILITY).ifPresent(essenceEntailCapability -> {
                essenceEntailCapability.setEntail(essenceEnchantment.getEssenceEntail());
            });
        }
    }
    public static EssenceEntailBinding getListEntailBinding(NonNullList<ItemStack> lista){
        for(ItemStack itemStack : lista){
            EssenceEntailBinding essenceEntailBinding = getItemEntailBinding(itemStack);
            if(essenceEntailBinding != ModBindings.EMPTY_BINDING) return essenceEntailBinding;
        }
        return ModBindings.EMPTY_BINDING;
    }

    public static EssenceEntailBinding getItemEntailBinding(ItemStack itemStack){
        CompoundTag tag = itemStack.getOrCreateTag();
        if(tag.contains("EntailBinding")){
            String description = tag.getString("EntailBinding");
            if(ModBindings.BINDINGS_MAP.containsKey(description)){
                return ModBindings.BINDINGS_MAP.get(description);
            }
        }
        return ModBindings.EMPTY_BINDING;
    }
}
