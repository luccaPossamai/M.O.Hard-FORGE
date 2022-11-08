package net.lucca.mohard.util.mechanics;

import com.google.common.collect.ImmutableList;
import net.lucca.mohard.enchantments.essences.EssenceBindingHelper;
import net.lucca.mohard.init.ModBindings;
import net.lucca.mohard.itens.artifacts.GenericArtifact;
import net.lucca.mohard.itens.essence.EssenceDataHelper;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.function.UnaryOperator;

@Mod.EventBusSubscriber
public class CustomTooltips {

    private static final List<ChatFormatting> CHAT_FORMATTINGS = ImmutableList.of(ChatFormatting.YELLOW, ChatFormatting.GREEN, ChatFormatting.BLUE, ChatFormatting.DARK_PURPLE, ChatFormatting.GOLD, ChatFormatting.RED);

    @SubscribeEvent
    public static void customTooltips(ItemTooltipEvent event){
        List<Component> componentList = event.getToolTip();
        if(componentList.isEmpty()) return;
        ItemStack stack = event.getItemStack();
        MutableComponent name = Component.empty().append(stack.getHoverName());
        if(stack.hasCustomHoverName()){
            name.getStyle().applyFormat(ChatFormatting.UNDERLINE);
        }
        String string = getStringByItem(stack.getItem());
        name = Component.literal(string).withStyle(getRarityColor(stack).apply(Style.EMPTY)).append(name.withStyle(ChatFormatting.GRAY));
        componentList.set(0, name);
    }

    private static String getStringByItem(Item item){
        //⛏🔱🪓🛡🗡🏹🎣⚗🧪🔥⛄🌧⛈❌⏭⏯⏮⏸⏹⏺⅐
        if(item instanceof SwordItem){
            return "\uD83D\uDDE1 ";
        }
        if(item instanceof PickaxeItem){
            return "⛏ ";
        }
        if(item instanceof AxeItem){
            return "\uD83E\uDE93 ";
        }
        if(item instanceof ShieldItem){
            return "\uD83D\uDEE1 ";
        }
        if(item instanceof SplashPotionItem){
            return "⚗ ";
        }
        if(item instanceof PotionItem){
            return "\uD83E\uDDEA ";
        }
        if(item instanceof FishingRodItem){
            return "\uD83C\uDFA3 ";
        }
        if(item instanceof GenericArtifact){
            return "✧ ";
        }
        if(item instanceof EssenceItem){
            return "● ";
        }
        return "♦ ";

    }
    private static UnaryOperator<Style> getRarityColor(ItemStack itemStack){
        if(itemStack.getOrCreateTag().contains("CustomLevel")){
            int i = itemStack.getOrCreateTag().getInt("CustomLevel");
            return style -> style.withColor(CHAT_FORMATTINGS.get(Math.min(i, CHAT_FORMATTINGS.size() - 1)));
        }
        int i = 0;//COMMON
        switch(itemStack.getRarity()){
            case UNCOMMON -> i++;
            case RARE -> i = i + 2;
            case EPIC -> i = i + 3;
        }
        if(itemStack.getOrCreateTag().contains("Modified")){
            i = i + itemStack.getOrCreateTag().getInt("Modified");
        }
        if(itemStack.getItem() instanceof EssenceItem){
            i = i + (EssenceDataHelper.getEssenceLevel(itemStack) > 0 ? 1 : 0);
            i = i + (EssenceDataHelper.getEssenceNegativeLevel(itemStack) > 0 ? 1 : 0);
            i = i + (EssenceBindingHelper.getItemEntailBinding(itemStack) != ModBindings.EMPTY_BINDING ? 1 : 0);
        }
        int iFinal = i;
        return style -> style.withColor(CHAT_FORMATTINGS.get(Math.min(iFinal, CHAT_FORMATTINGS.size() - 1)));
    }

}
