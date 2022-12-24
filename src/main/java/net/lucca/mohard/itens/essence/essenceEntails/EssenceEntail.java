package net.lucca.mohard.itens.essence.essenceEntails;

import net.lucca.mohard.init.ModBindings;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public class EssenceEntail {

    public void tick(Player player, int tickCount){}

    public double bonusOnHit(double value, Player player, LivingEntity entityHurt){
        return value;
    }

    public void getHit(float originalValue, float finalValue, Player player, LivingEntity livingEntity){
    }

    public void dealHit(float originalValue, float finalValue, DamageSource damageSource, Player player, LivingEntity entityHurted){
    }

    public void applyBonus(Map<Attribute, Double> map){
    }




    public static class Helper {

        public static EssenceEntail getEntailByName(String string) {
            for(String s : ModBindings.BINDINGS_MAP.keySet()){
                if(s.equals(string)) return ModBindings.BINDINGS_MAP.get(string).getEssenceEntail();
            }
            return new EmptyEntail();
        }
    }


}
