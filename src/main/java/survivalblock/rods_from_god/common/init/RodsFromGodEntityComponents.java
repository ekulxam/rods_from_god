package survivalblock.rods_from_god.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.cca.entity.*;
import survivalblock.rods_from_god.common.entity.BookEntity;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;

public class RodsFromGodEntityComponents implements EntityComponentInitializer {

    public static final ComponentKey<DeathExplosionComponent> DEATH_EXPLOSION = ComponentRegistry.getOrCreate(RodsFromGod.id("death_explosion"), DeathExplosionComponent.class);
    public static final ComponentKey<SmokeScreenComponent> SMOKE_SCREEN = ComponentRegistry.getOrCreate(RodsFromGod.id("smoke_screen"), SmokeScreenComponent.class);
    public static final ComponentKey<SolarLaserComponent> SOLAR_LASER = ComponentRegistry.getOrCreate(RodsFromGod.id("solar_laser"), SolarLaserComponent.class);
    public static final ComponentKey<SlimeBlockFlyingComponent> SLIME_BLOCK_FLYING = ComponentRegistry.getOrCreate(RodsFromGod.id("slime_block_flying"), SlimeBlockFlyingComponent.class);
    public static final ComponentKey<BookTargetComponent> BOOK_TARGET = ComponentRegistry.getOrCreate(RodsFromGod.id("book_target"), BookTargetComponent.class);
    public static final ComponentKey<StoneStatueComponent> STONE_STATUE = ComponentRegistry.getOrCreate(RodsFromGod.id("stone_statue"), StoneStatueComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(DEATH_EXPLOSION, DeathExplosionComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(SMOKE_SCREEN, SmokeScreenComponent::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(SOLAR_LASER, SolarLaserComponent::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerFor(Entity.class, SLIME_BLOCK_FLYING, SlimeBlockFlyingComponent::new);
        registry.registerFor(BookEntity.class, BOOK_TARGET, BookTargetComponent::new);
        registry.registerFor(LivingEntity.class, STONE_STATUE, StoneStatueComponent::new);
    }
}