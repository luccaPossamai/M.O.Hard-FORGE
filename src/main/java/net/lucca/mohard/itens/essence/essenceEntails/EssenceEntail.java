package net.lucca.mohard.itens.essence.essenceEntails;

import net.lucca.mohard.init.ModBindings;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EssenceEntail {

    public void tick(Player player, int tickCount){}

    public double bonusOnHit(double value, Player player, LivingEntity entityHurt){
        return value;
    }

    public void getHit(float originalValue, float finalValue, Player player, LivingEntity livingEntity){
    }

    public void dealHit(float originalValue, float finalValue, Player player, LivingEntity entityHurted){
    }

    public double applyBonus(double value){
        return 1;
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
