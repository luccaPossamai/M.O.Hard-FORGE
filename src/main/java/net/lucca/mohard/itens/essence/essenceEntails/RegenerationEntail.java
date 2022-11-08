package net.lucca.mohard.itens.essence.essenceEntails;

import net.minecraft.world.entity.player.Player;

public class RegenerationEntail extends EssenceEntail {
    private static final int HEALTICK = 5;
    @Override
    public void tick(Player player, int tickCount) {
        if(tickCount % HEALTICK == 0) {
            player.heal(player.getMaxHealth() / 80);
        }
    }
}
