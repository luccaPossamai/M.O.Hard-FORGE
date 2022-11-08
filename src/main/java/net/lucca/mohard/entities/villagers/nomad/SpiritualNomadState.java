package net.lucca.mohard.entities.villagers.nomad;

public enum SpiritualNomadState {
    NORMAL,
    VANISHING,
    CROSSED,
    CASTING;



    public boolean isNormal(){
        return this.equals(NORMAL);
    }
    public boolean isVanishing(){
        return this.equals(VANISHING);
    }
    public boolean isCasting(){
        return this.equals(CASTING);
    }
    public boolean isCrossed(){
        return this.equals(CROSSED);
    }
}
