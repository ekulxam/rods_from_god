package survivalblock.rods_from_god.common.init;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.DeathExplosionComponent;
import survivalblock.rods_from_god.common.component.SmokeScreenComponent;
import survivalblock.rods_from_god.common.component.TungstenRodLandedComponent;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;

public class RodsFromGodEntityComponents implements EntityComponentInitializer {

    public static final ComponentKey<DeathExplosionComponent> DEATH_EXPLOSION = ComponentRegistry.getOrCreate(RodsFromGod.id("death_explosion"), DeathExplosionComponent.class);
    public static final ComponentKey<SmokeScreenComponent> SMOKE_SCREEN = ComponentRegistry.getOrCreate(RodsFromGod.id("smoke_screen"), SmokeScreenComponent.class);
    public static final ComponentKey<TungstenRodLandedComponent> TUNGSTEN_ROD_LANDED = ComponentRegistry.getOrCreate(RodsFromGod.id("tungsten_rod_landed"), TungstenRodLandedComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(DEATH_EXPLOSION, DeathExplosionComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(SMOKE_SCREEN, SmokeScreenComponent::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerFor(TungstenRodEntity.class, TUNGSTEN_ROD_LANDED, TungstenRodLandedComponent::new);
    }
}