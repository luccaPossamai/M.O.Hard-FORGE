package net.lucca.mohard.capabilities.entail;

import net.lucca.mohard.itens.essence.essenceEntails.EssenceEntail;

public class EssenceEntailCapability {

    private EssenceEntail entail;

    EssenceEntailCapability(EssenceEntail entail){
        this.entail = entail;
    }

    public EssenceEntail getEntail() {
        return this.entail;
    }

    public void setEntail(EssenceEntail entail){
        this.entail = entail;
    }

}
