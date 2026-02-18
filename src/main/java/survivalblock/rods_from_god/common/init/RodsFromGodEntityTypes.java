package survivalblock.rods_from_god.common.init;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.EntityTypeRegistrant;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.entity.*;

@SuppressWarnings("UnstableApiUsage")
public class RodsFromGodEntityTypes {
    public static final EntityTypeRegistrant registrant = new EntityTypeRegistrant(RodsFromGod::id);

    public static final EntityType<TungstenRodEntity> TUNGSTEN_ROD = registrant.register("tungsten_rod", EntityType.Builder.<TungstenRodEntity>create(TungstenRodEntity::new, SpawnGroup.MISC).dimensions(1.25f / 16, 24.5f / 16));
    public static final EntityType<SmokeBombEntity> SMOKE_BOMB = registrant.register("smoke_bomb", EntityType.Builder.<SmokeBombEntity>create(SmokeBombEntity::new, SpawnGroup.MISC).dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));
    public static final EntityType<RodLandingMarkerEntity> ROD_LANDING_MARKER = registrant.register("tungsten_rod_landing_marker", EntityType.Builder.<RodLandingMarkerEntity>create(RodLandingMarkerEntity::new, SpawnGroup.MISC).dimensions(0.5f, 0.1f).disableSummon());
    public static final EntityType<BookEntity> BOOK = registrant.register("book", EntityType.Builder.<BookEntity>create(BookEntity::new, SpawnGroup.MISC).dimensions(0.8f, 0.5f));
    public static final EntityType<EnchantedArrowEntity> ENCHANTED_ARROW = registrant.register("enchanted_arrow", EntityType.Builder.<EnchantedArrowEntity>create(EnchantedArrowEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));

    public static void init() {
    }
}
