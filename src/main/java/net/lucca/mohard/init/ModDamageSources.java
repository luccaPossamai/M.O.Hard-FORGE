package net.lucca.mohard.init;

import net.lucca.mohard.util.mechanics.damage.DirectEntityDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class ModDamageSources {

    public static DamageSource healthDischarge(Entity damager, @Nullable Entity owner) {
        return playerMagicAttack(damager, owner, DirectEntityDamageSource.Type.HEALTH_DISCHARGE);
    }

    public static DamageSource algidAxe(Entity damager, @Nullable Entity owner){
        return playerMagicAttack(damager, owner, DirectEntityDamageSource.Type.ALGID_AXE);
    }
    public static DamageSource dagger(Entity damager, @Nullable Entity owner){
        return playerMagicAttack(damager, owner, DirectEntityDamageSource.Type.DAGGER);
    }
    public static DamageSource fireball(Entity damager, @Nullable Entity owner){
        return playerMagicAttack(damager, owner, DirectEntityDamageSource.Type.COLD_FIREBALL).setIsFire();
    }
    public static DamageSource ancientBook(Entity damager, @Nullable Entity owner){
        return playerMagicAttack(damager, owner, DirectEntityDamageSource.Type.ANCIENT_BOOK);
    }

    public static DamageSource scream(Entity damager, @Nullable Entity owner){
        return playerMagicAttack(damager, owner, DirectEntityDamageSource.Type.CONTEMPLATION_SCREAM);
    }

    public static DamageSource cursedFriendShip(Entity damager, @Nullable Entity owner){
        return playerMagicAttack(damager, owner, DirectEntityDamageSource.Type.CURSED_FRIENDSHIP);
    }

    public static DamageSource playerMagicAttack(Entity damager, @Nullable Entity owner, DirectEntityDamageSource.Type type) {
        return (new DirectEntityDamageSource("directMagic", damager, owner, type)).setMagic();
    }
}