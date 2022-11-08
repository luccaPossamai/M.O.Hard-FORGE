package net.lucca.mohard.init;

import net.lucca.mohard.ModMain;
import net.lucca.mohard.entities.corrupter.Corrupter;
import net.lucca.mohard.entities.dummy.DummyEntity;
import net.lucca.mohard.entities.etc.ColdFireball;
import net.lucca.mohard.entities.etc.corruptedPlayer.CorruptedPlayer;
import net.lucca.mohard.entities.illagers.iceIsolator.IceIsolator;
import net.lucca.mohard.entities.villagers.nomad.SpiritualNomadEntity;
import net.lucca.mohard.itens.artifacts.algidAxe.ThrownAlgidAxe;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ModMain.MODID);


    public static final RegistryObject<EntityType<IceIsolator>> ICE_ISOLATOR = ENTITIES.register("ice_isolator", () ->
            EntityType.Builder.of(
            IceIsolator::new,
            MobCategory.CREATURE)
            .canSpawnFarFromPlayer()
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8).build("ice_isolator"));

    public static final RegistryObject<EntityType<SpiritualNomadEntity>> NOMAD = ENTITIES.register("spiritual_nomad", () ->
            EntityType.Builder.of(
            SpiritualNomadEntity::new,
            MobCategory.MISC)
            .canSpawnFarFromPlayer()
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8).build("spiritual_nomad"));

    public static final RegistryObject<EntityType<CorruptedPlayer>> CORRUPTED_PLAYER = ENTITIES.register("corrupted_player", () ->
            EntityType.Builder.of(
            CorruptedPlayer::new,
            MobCategory.MONSTER)
            .canSpawnFarFromPlayer()
            .sized(0.6F, 1.8F)
            .clientTrackingRange(8).build("corrupted_player"));

    public static final RegistryObject<EntityType<Corrupter>> CORRUPTER = ENTITIES.register("corrupter", () ->
            EntityType.Builder.of(
            Corrupter::new,
            MobCategory.CREATURE)
            .canSpawnFarFromPlayer()
            .sized(0.6F, 1.25F)
            .clientTrackingRange(8).build("corrupter"));

    public static final RegistryObject<EntityType<DummyEntity>> DUMMY = ENTITIES.register("dummy", () ->
            EntityType.Builder.of(
            DummyEntity::new,
            MobCategory.MISC)
            .canSpawnFarFromPlayer()
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8).build("dummy"));


    public static final RegistryObject<EntityType<ThrownAlgidAxe>> ALGID_AXE = ENTITIES.register("algid_axe", () ->
            EntityType.Builder.<ThrownAlgidAxe>of(
            ThrownAlgidAxe::new,
            MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(4)
            .updateInterval(20).build("algid_axe"));

    public static final RegistryObject<EntityType<ColdFireball>> COLD_FIREBALL = ENTITIES.register("cold_fireball", () ->
            EntityType.Builder.<ColdFireball>of(ColdFireball::new, MobCategory.MISC)
            .sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10).build("cold_fireball"));



}