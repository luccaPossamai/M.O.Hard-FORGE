package net.lucca.mohard.entities.villagers.nomad.goals;

import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadEntity;
import net.lucca.mohard.entities.villagers.nomad.spells.NomadSpells;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.core.particles.ParticleTypes;

public class HealGoal<T extends AbstractVillager> extends Goal {

    protected final SpiritualNomadEntity nomad;
    private int spellTime = NomadSpells.HEAL.getTime();
    private int count = 0;
    private int urgency;

    public HealGoal(SpiritualNomadEntity nomadEntity){
        this.nomad = nomadEntity;
        this.urgency =  10 - (int) Math.round(Math.floor(nomadEntity.getHealth() / 10));
    }

    @Override
    public boolean canUse() {
        return nomad.isSpellFree(NomadSpells.HEAL) && calculateTick();
    }

    @Override
    public boolean canContinueToUse() {
        return this.spellTime > 0;
    }

    @Override
    public void start() {
        super.start();
        nomad.setCasting(true);
        nomad.setCastingSpell(NomadSpells.HEAL);
    }

    @Override
    public void stop() {
        nomad.setCasting(false);
        nomad.setTiredCooldown();
        nomad.setCooldown(nomad.getDataBySpell(NomadSpells.HEAL));
        nomad.setCastingSpell(null);
    }

    @Override
    public void tick() {
        count++;
        spellTime--;
        regenLife();
        this.nomad.addParticlesAroundSelf(ParticleTypes.HEART);

    }

    private void regenLife(){
        double maxHealth = this.nomad.getMaxHealth();
        double health = this.nomad.getHealth();
        double regen = maxHealth / 500;
        double mult = count / 20;
        double total = regen * (mult * mult);
        if(health + total <= maxHealth){
            this.nomad.setHealth((float) (health + total));
        } else {
            this.nomad.setHealth((float) maxHealth);
        }
    }

    private boolean calculateTick(){
        int area = this.nomad.tickCount % 6000;
        return area <= ((this.urgency / 10) * 6000);
    }
}
