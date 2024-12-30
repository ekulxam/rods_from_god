package survivalblock.rods_from_god.common.init;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.dimension.DimensionType;
import survivalblock.rods_from_god.common.RodsFromGod;

import java.awt.*;

public class RodsFromGodTags {

    public static final TagKey<DimensionType> SUN_AND_MOON = TagKey.of(RegistryKeys.DIMENSION_TYPE, RodsFromGod.id("has_sun_and_moon"));
}
