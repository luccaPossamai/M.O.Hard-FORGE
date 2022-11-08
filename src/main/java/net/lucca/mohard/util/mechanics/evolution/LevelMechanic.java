package net.lucca.mohard.util.mechanics.evolution;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.util.help.Methods;
import net.lucca.mohard.itens.essence.EssenceData;
import net.lucca.mohard.itens.essence.EssenceDataHelper;
import net.lucca.mohard.itens.essence.EssenceItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Random;

public class LevelMechanic {

    public static int SERVER_LEVEL = 0;
    public static int SD = 0;
    public static int VAR = 0;
    public static int getLevel(Level world){
        double randVar = (new Random().nextFloat(2) - 1) * VAR;
        return SERVER_LEVEL + (int) Math.round(randVar);
    }


    private static double getServerLevel(Level level){
        return level.players().stream().mapToDouble(LevelMechanic::getPlayerLevel).sum() / level.players().size();
    }
    private static double getServerLevelVariance(Level level, double average){
        return level.players().stream().mapToDouble(LevelMechanic::getPlayerLevel).map(operand -> Math.pow(operand - average, 2)).sum() / level.players().size();
    }
    static double getPlayerLevel(Player player){
        return PlayerEvolution.getItems(player).stream().filter(itemStack -> itemStack.getItem() instanceof EssenceItem).mapToDouble(itemStack -> {
            int upgradeLevel = EssenceDataHelper.getEssenceLevel(itemStack);
            int negativeUpgradeLevel = EssenceDataHelper.getEssenceNegativeLevel(itemStack);
            return getEssenceLevel(((EssenceItem)itemStack.getItem()).getEssenceData(), upgradeLevel, negativeUpgradeLevel);
        }).sum();
    }

    static void updateserverLevel(Level level){
        double average = getServerLevel(level);
        double var = getServerLevelVariance(level, average);
        SERVER_LEVEL = (int) Math.round(average);
        VAR = (int) Math.round(var);
        SD = (int) Math.round(Math.sqrt(var));
        ModMain.LOGGER.trace("[UPDATING SERVER LEVEL] Current Level is "+SERVER_LEVEL);
        ModMain.LOGGER.trace("[UPDATING SERVER LEVEL] The Variance and StandardDeviation are "+VAR+" ;"+SD);
    }

    public static double getEssenceLevel(EssenceData essenceData){
        return getEssenceLevel(essenceData, 0,0);
    }

    public static double getEssenceLevel(EssenceData essenceData, int upgrade, int negativeUpgrade){
        Map<Attribute, Double> map = essenceData.essenceStats().getStats(upgrade, negativeUpgrade );
        return ((Methods.getAttributes().stream().map(attribute -> {
            if(!map.containsKey(attribute)) return 0D;
            if(Methods.isPercentageAttribute(attribute)) return map.get(attribute) * 1;
            return map.get(attribute);
        }).mapToDouble(Double::doubleValue).sum()));
    }
}
