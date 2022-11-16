package net.lucca.mohard.entities.villagers.nomad.goals;

import com.google.common.collect.ImmutableList;
import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadEntity;
import net.lucca.mohard.entities.villagers.nomad.spells.NomadSpells;
import net.lucca.mohard.init.ModEffects;
import net.lucca.mohard.util.mechanics.evolution.LevelMechanic;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class SpellInEnemy<T extends LivingEntity> extends Goal {


    protected final SpiritualNomadEntity nomad;
    protected T toKill;
    protected final float maxDist;
    protected final Class<T> killClass;
    protected final Predicate<LivingEntity> avoidPredicate;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    private final TargetingConditions avoidEntityTargeting;

    private int spellTime = 0;
    private NomadSpells choosenSpell;

    @Override
    public boolean isInterruptable() {
        return false;
    }

    public SpellInEnemy(SpiritualNomadEntity entity, Class<T> entityClass, float distance) {
        this(entity, entityClass, (p_200828_0_) -> {
            return true;
        }, distance, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    public SpellInEnemy(SpiritualNomadEntity entity, Class<T> entityClass, Predicate<LivingEntity> predicate, float distance, Predicate<LivingEntity> predicate2) {
        this.nomad = entity;
        this.killClass = entityClass;
        this.avoidPredicate = predicate;
        this.maxDist = distance;
        this.predicateOnAvoidEntity = predicate2;
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.avoidEntityTargeting = (TargetingConditions.forCombat()).range((double) distance).selector(predicate2.and(predicate));
    }

    public boolean canUse() {
        this.toKill = this.nomad.level.getNearestEntity(this.killClass, this.avoidEntityTargeting, this.nomad, this.nomad.getX(), this.nomad.getY(), this.nomad.getZ(), this.nomad.getBoundingBox().inflate((double) this.maxDist, 3.0D, (double) this.maxDist));
        if (this.toKill == null) {
            return false;
        } else {
            if(this.toKill instanceof Player){
                return (!nomad.isTired() && nomad.getFreeSpells(true).size() > 0 && LevelMechanic.getLevel(this.toKill.level) >= 500);
            }
            return (!nomad.isTired() && nomad.getFreeSpells(true).size() > 0);
        }
    }

    public boolean canContinueToUse() {
        return this.spellTime > 0;
    }

    public void start() {
        nomad.setCasting(true);
        nomad.getLookControl().setLookAt(this.toKill, 10, 10);
        choosenSpell = this.chooseSpell();
        nomad.setCastingSpell(choosenSpell);
        setEffects();
        spellTime = choosenSpell.getTime();
    }

    public void stop() {
        castSpell(this.choosenSpell);
        nomad.setCasting(false);
        nomad.setTiredCooldown();
        nomad.setCooldown(nomad.getDataBySpell(this.choosenSpell));
        this.toKill = null;
        choosenSpell = null;
        nomad.setCastingSpell(choosenSpell);
    }

    public void tick() {
        this.spellTime--;
        if(this.choosenSpell.equals(NomadSpells.FIELD)) runField();
    }


    public void runField(){
        TargetingConditions entity = TargetingConditions.forCombat().range(10);
        List<MobEffect> effects = ImmutableList.of(MobEffects.WITHER, ModEffects.INCINERATION.get(), MobEffects.POISON, MobEffects.MOVEMENT_SLOWDOWN);
        for(LivingEntity entities : this.nomad.level.getNearbyEntities(LivingEntity.class, entity, this.nomad, this.nomad.getBoundingBox().inflate(10D))) {
            for (MobEffect effect : effects) {
                if(!entities.hasEffect(effect)) entities.addEffect(new MobEffectInstance(effect, 200, 1, false, false, false));
            }
        }

    }

    public NomadSpells chooseSpell(){
        List<NomadSpells> spellsList = nomad.getFreeSpells(true);
        if(toKill.fireImmune()) {
            spellsList.remove(NomadSpells.FIREBALL);
            spellsList.remove(NomadSpells.THUNDER);
        }

        if(this.nomad.distanceTo(this.toKill) <= 5){
            spellsList.remove(NomadSpells.THUNDER);
            spellsList.add(NomadSpells.FIELD);
        }
        if(this.toKill.fireImmune()){
            spellsList.remove(NomadSpells.FIREBALL);
        }



        Collections.shuffle(spellsList);
        return spellsList.get(0);

    }


    public void castSpell(NomadSpells spell){
        if(spell.equals(NomadSpells.THUNDER)){
            Entity lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, toKill.level);
            lightning.setPos(toKill.getX(), toKill.getY(), toKill.getZ());
            toKill.level.addFreshEntity(lightning);
        } else if(spell.equals(NomadSpells.FIREBALL)){
            double d1 = this.toKill.getX() - this.nomad.getX();
            double d2 = this.toKill.getY(0.5D) - this.nomad.getY(0.5D);
            double d3 = this.toKill.getZ() - this.nomad.getZ();

            SmallFireball smallfireballentity = new SmallFireball(toKill.level, this.nomad, d1, d2, d3);
            smallfireballentity.setPos(smallfireballentity.getX(), this.nomad.getY(0.5D) + 0.5D, smallfireballentity.getZ());
            this.nomad.level.addFreshEntity(smallfireballentity);
        } else if(spell.equals(NomadSpells.ARROWS)) {
            for (int i = 1 + (int) (this.toKill.distanceTo(this.nomad) / 2); i > 0; i--) {
                ArrowItem arrowItem = (ArrowItem) Items.ARROW;
                AbstractArrow smallfireballentity = arrowItem.createArrow(this.nomad.level, new ItemStack(arrowItem), this.nomad);
                smallfireballentity.setPos(smallfireballentity.getX(), this.nomad.getY(0.5D) + 0.5D, smallfireballentity.getZ());
                double d0 = this.toKill.getX() - this.nomad.getX();
                double d1 = this.toKill.getY(0.3333333333333333D) - smallfireballentity.getY();
                double d2 = this.toKill.getZ() - this.nomad.getZ();
                double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
                this.nomad.getLookControl().setLookAt(this.toKill.getX(), this.toKill.getEyeY(), this.toKill.getZ());
                smallfireballentity.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (8 - this.nomad.level.getDifficulty().getId() * 2));
                this.nomad.level.addFreshEntity(smallfireballentity);
            }
        }
    }
    public void setEffects(){
        if(!this.choosenSpell.equals(NomadSpells.FIELD)) {
            this.toKill.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, this.choosenSpell.getTime(), 10, false, false, false));
            this.toKill.addEffect(new MobEffectInstance(MobEffects.LEVITATION, this.choosenSpell.getTime(), 0, false, false, false));
            this.toKill.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, this.choosenSpell.getTime() + 20, 0, false, false, false));
        }
        this.nomad.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, this.choosenSpell.getTime(), 10, false, false, false));

    }
}
