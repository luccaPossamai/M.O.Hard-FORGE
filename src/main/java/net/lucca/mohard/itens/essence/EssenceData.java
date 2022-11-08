package net.lucca.mohard.itens.essence;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import java.util.function.Supplier;

public record EssenceData(Supplier<? extends EntityType<? extends Mob>> type, EssenceStats essenceStats) {


    public EssenceData(EntityType<? extends Mob> type, EssenceStats essenceStats) {
        this(() -> type, essenceStats);
    }

    public EssenceData {
        EssenceDataHelper.ESSENCE_DATA_LIST.add(this);
    }

    public EntityType<?> entityType(){
        return this.type().get();
    }

}
