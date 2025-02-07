package survivalblock.rods_from_god.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.entity.*;

public class RodsFromGodEntityTypes {

    public static final EntityType<TungstenRodEntity> TUNGSTEN_ROD = registerEntity("tungsten_rod", EntityType.Builder.<TungstenRodEntity>create(TungstenRodEntity::new, SpawnGroup.MISC).dimensions(1.25f / 16, 24.5f / 16));
    public static final EntityType<SmokeBombEntity> SMOKE_BOMB = registerEntity("smoke_bomb", EntityType.Builder.<SmokeBombEntity>create(SmokeBombEntity::new, SpawnGroup.MISC).dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));
    public static final EntityType<RodLandingMarkerEntity> ROD_LANDING_MARKER = registerEntity("tungsten_rod_landing_marker", EntityType.Builder.<RodLandingMarkerEntity>create(RodLandingMarkerEntity::new, SpawnGroup.MISC).dimensions(0.5f, 0.1f).disableSummon());
    public static final EntityType<BookEntity> BOOK = registerEntity("book", EntityType.Builder.<BookEntity>create(BookEntity::new, SpawnGroup.MISC).dimensions(1f, 0.5f));
    public static final EntityType<EnchantedArrowEntity> ENCHANTED_ARROW = registerEntity("enchanted_arrow", EntityType.Builder.<EnchantedArrowEntity>create(EnchantedArrowEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));

    @SuppressWarnings("SameParameterValue")
    private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, RodsFromGod.id(name), builder.build());
    }

    public static void init(){

    }
}
