package net.lucca.mohard.entities.villagers.nomad.spells;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum NomadSpells {

    THUNDER(120, 200, 1, true),
    FIREBALL(20, 30, 2, true),
    ARROWS(50, 90, 3, true),
    FIELD(200, 500, 4, true),
    HEAL(50, 6000, 5, false);

    int time;
    int cooldown;
    int id;
    boolean aggressive;

    NomadSpells(int time, int cooldown, int id, boolean aggressive){
        this.time = time;
        this.cooldown = cooldown;
        this.id = id;
        this.aggressive = aggressive;
    }

    public List<NomadSpells> getSpells(){
        return ImmutableList.of(THUNDER, FIREBALL, ARROWS, FIELD);
    }

    public int getTime() {
        return time;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getId(){
        return id;
    }

    public boolean isAggressive() {
        return aggressive;
    }
}
