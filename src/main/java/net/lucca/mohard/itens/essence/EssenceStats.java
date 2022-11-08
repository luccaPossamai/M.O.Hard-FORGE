package net.lucca.mohard.itens.essence;

import net.lucca.mohard.util.help.Methods;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EssenceStats extends ArrayList<Double> {

    public static EssenceStats ZERO = new EssenceStats(0,0,0,0,0,0,0,0);

    public final int CAPACITY = 8;
    public EssenceStats(double vida, double contactArmor, double meleeDamage, double projectileDamage, double magicDamage, double penetration, double agility, double intellect){
        this();
        this.add(vida);
        this.add(contactArmor);
        this.add(meleeDamage);
        this.add(projectileDamage);
        this.add(magicDamage);
        this.add(penetration);
        this.add(agility);
        this.add(intellect);
    }

    private EssenceStats(){
        super(8);
    }

    public Map<Attribute, Double> getStats(int upgrade, int negativeUpgrade) {
        List<Attribute> attributes = Methods.getAttributes();
        Map<Attribute, Double> leveledStats = new HashMap<>();
        for (int i = 0; i < attributes.size(); i++) {
            double stat = this.get(i);
            Attribute attribute = attributes.get(i);
            stat = EssenceDataHelper.calculateUpgrades(stat, upgrade, negativeUpgrade);
            leveledStats.put(attribute, stat);
        }
        return leveledStats;
    }


}
