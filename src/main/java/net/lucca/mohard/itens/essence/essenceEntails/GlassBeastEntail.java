package net.lucca.mohard.itens.essence.essenceEntails;

import net.lucca.mohard.init.ModAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Map;

public class GlassBeastEntail extends EssenceEntail {

    @Override
    public void applyBonus(Map<Attribute, Double> map) {
        double healthValue = map.get(Attributes.MAX_HEALTH) - 1;
        Double magicValue = map.get(ModAttributes.MAGIC_DAMAGE);
        map.put(ModAttributes.MAGIC_DAMAGE, magicValue + (magicValue * healthValue * 0.025));
        Double projectileValue = map.get(ModAttributes.PROJECTILE_DAMAGE);
        map.put(ModAttributes.PROJECTILE_DAMAGE, projectileValue + (projectileValue * healthValue * 0.025));
        Double meleeValue = map.get(ModAttributes.PHYSICAL_DAMAGE);
        map.put(ModAttributes.PHYSICAL_DAMAGE, meleeValue + (meleeValue * healthValue * 0.025));
        map.put(Attributes.MAX_HEALTH, 1D);
    }
}
