package net.lucca.mohard.enchantments.essences;

import net.lucca.mohard.capabilities.ModCapabilities;
import net.lucca.mohard.init.ModBindings;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EssenceBindingHelper {

    public static boolean canBind(ItemStack stack){
        return !stack.getOrCreateTag().contains("EntailBinding");
    }

    public static void bind(ItemStack stack, EssenceEntailBinding binding){
        stack.getOrCreateTag().putString("EntailBinding", binding.getDescription());
    }

    public static void updateEntail(NonNullList<ItemStack> lista, Player player) {
        EssenceEntailBinding entailBinding = getListEntailBinding(lista);
        if(!entailBinding.equals(ModBindings.EMPTY_BINDING)){
            player.getCapability(ModCapabilities.ENTAIL_CAPABILITY).ifPresent(essenceEntailCapability -> {
                essenceEntailCapability.setEntail(entailBinding.getEssenceEntail());
            });
        }
    }
    public static EssenceEntailBinding getListEntailBinding(NonNullList<ItemStack> lista){
        List<EssenceEntailBinding> entailBindings = lista.stream().map(EssenceBindingHelper::getItemEntailBinding).filter(essenceEntailBinding -> essenceEntailBinding != ModBindings.EMPTY_BINDING).toList();
        EssenceEntailBinding essenceEntailBinding = ModBindings.EMPTY_BINDING;
        if(!entailBindings.isEmpty()){
            essenceEntailBinding = entailBindings.get(0);
        }
        return essenceEntailBinding;
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
