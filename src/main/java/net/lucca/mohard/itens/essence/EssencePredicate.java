package net.lucca.mohard.itens.essence;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface EssencePredicate<T extends LivingEntity> {
    boolean test(T entity);
}
