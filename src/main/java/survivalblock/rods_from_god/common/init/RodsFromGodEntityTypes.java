package survivalblock.rods_from_god.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.entity.SmokeBombEntity;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;

public class RodsFromGodEntityTypes {

    public static final EntityType<TungstenRodEntity> TUNGSTEN_ROD = registerEntity("tungsten_rod", EntityType.Builder.<TungstenRodEntity>create(TungstenRodEntity::new, SpawnGroup.MISC).dimensions(1.25f, 24.5f));
    public static final EntityType<SmokeBombEntity> SMOKE_BOMB = registerEntity("smoke_bomb", EntityType.Builder.<SmokeBombEntity>create(SmokeBombEntity::new, SpawnGroup.MISC).dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));

    @SuppressWarnings("SameParameterValue")
    private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, RodsFromGod.id(name), builder.build());
    }

    public static void init(){

    }
}
