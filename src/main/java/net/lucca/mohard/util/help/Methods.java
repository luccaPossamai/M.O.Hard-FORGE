package net.lucca.mohard.util.help;


import net.lucca.mohard.init.ModAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Methods {


    public static Component stringToText(String message) {
        return Component.translatable(message);
    }
    public static List<Attribute> getAttributes(){
        return Arrays.asList(
                Attributes.MAX_HEALTH,
                ModAttributes.RAW_ARMOR,
                ModAttributes.PHYSICAL_DAMAGE,
                ModAttributes.PROJECTILE_DAMAGE,
                ModAttributes.MAGIC_DAMAGE,
                ModAttributes.ARMOR_PENETRATION,
                ModAttributes.AGILITY,
                ModAttributes.INTELLECT
        );
    }

    public static boolean isPercentageAttribute(@Nonnull Attribute att){
        return (att.equals(ModAttributes.AGILITY) ||
                att.equals(ModAttributes.ARMOR_PENETRATION) ||
                att.equals(ModAttributes.INTELLECT))  ;
    }

    public static Component formatEntity(EntityType<?> type){
        return Component.translatable(type.getDescriptionId());
    }


    public static String capitalizeAndRemove(String string, char... chars){
        StringBuilder s = new StringBuilder();
        int i = 0;
        List<String> strings = new ArrayList<>();
        for (char aChar : chars) { strings.add(aChar + ""); }
        boolean toUpper = true;
        while(i < string.length()){
            if(!strings.contains(string.charAt(i)+"") || i == 0){
                if(toUpper){
                    s.append((string.charAt(i) + "").toUpperCase());
                    toUpper = false;
                } else {
                    s.append(string.charAt(i));
                }
            } else {
                toUpper = true;
            }
            i++;
        }
        return s.toString();
    }

}
