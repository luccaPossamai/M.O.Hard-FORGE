package net.lucca.mohard.enchantments.essences;

import net.lucca.mohard.itens.essence.essenceEntails.EssenceEntail;

public class EssenceEntailBinding {
    private final EssenceEntail essenceEntail;

    protected final String description;

    public EssenceEntailBinding(EssenceEntail essenceEntail, String description) {
        this.essenceEntail = essenceEntail;
        this.description = description;
    }

    public EssenceEntail getEssenceEntail() {
        return essenceEntail;
    }

    public String getDescription() {
        return description;
    }
}
