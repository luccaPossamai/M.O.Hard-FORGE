package net.lucca.mohard.entities.villagers.nomad.goals;

import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class ArmsGoal extends Goal {

    private final SpiritualNomadEntity mob;

    public ArmsGoal(SpiritualNomadEntity mobEntity) {
        this.mob = mobEntity;
    }

    @Override
    public boolean canUse() {
        return (mob.getArmState().isCrossed() && mob.level.isDay()) || (mob.getArmState().isNormal() && mob.level.isNight());
    }


    @Override
    public void start() {
        super.start();
        if(this.mob.level.isDay()) this.mob.resetArmStates();
        if(this.mob.level.isNight()) this.mob.setCrossing(true);
    }
}
