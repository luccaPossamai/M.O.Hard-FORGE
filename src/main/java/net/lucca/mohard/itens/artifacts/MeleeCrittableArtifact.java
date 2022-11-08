package net.lucca.mohard.itens.artifacts;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface MeleeCrittableArtifact {

    boolean shouldCrit(Player player, Entity entity);


}
