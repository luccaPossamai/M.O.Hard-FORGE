package net.lucca.mohard.util.mechanics.damage;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DirectEntityDamageSource extends EntityDamageSource {

    @Nullable
    private final Entity owner;
    private final Type type;

    public DirectEntityDamageSource(String p_19406_, Entity p_19407_, @Nullable Entity p_19408_, Type type) {
        super(p_19406_, p_19407_);
        this.owner = p_19408_;
        this.type = type;
    }

    @Nullable
    public Entity getDirectEntity() {
        return this.entity;
    }

    @Nullable
    public Entity getEntity() {
        return this.owner;
    }

    public @NotNull Component getLocalizedDeathMessage(LivingEntity p_19410_) {
        Component component = this.owner == null ? this.entity.getDisplayName() : this.owner.getDisplayName();
        String s = "death.attack." + this.msgId;
        s = this.type != null ? s + "."+this.type.name : "";
        return Component.translatable(s, p_19410_.getDisplayName(), component);
    }

    public Type getType() {
        return type;
    }

    public enum Type{

        ANCIENT_BOOK("heart_of_the_glowing_depths"),
        DAGGER("floyd_dagger"),
        ALGID_AXE("algid_axe"),
        COLD_FIREBALL("cold_fireball"),
        HEALTH_DISCHARGE("health_discharge"),
        ;

        final String name;

        Type(String name){
            this.name = name;
        }
    }
}
