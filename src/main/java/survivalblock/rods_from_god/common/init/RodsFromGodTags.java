package survivalblock.rods_from_god.common.init;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.dimension.DimensionType;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodTags {

    public static final TagKey<Item> BOOK_UTILITY = TagKey.of(RegistryKeys.ITEM, RodsFromGod.id("book_utility"));
    public static final TagKey<DimensionType> SUN_AND_MOON = TagKey.of(RegistryKeys.DIMENSION_TYPE, RodsFromGod.id("has_sun_and_moon"));
}
