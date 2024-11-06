package survivalblock.rods_from_god.common.init;


import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodTags {

    public static class RodsFromGodItemTags {
        public static final TagKey<Item> WATCHES = TagKey.of(RegistryKeys.ITEM, Identifier.of("modfest", "watches"));
    }

    public static class RodsFromGodDamageTypeTags {
        public static final TagKey<DamageType> BYPASSES_CREATIVE = TagKey.of(RegistryKeys.DAMAGE_TYPE, RodsFromGod.id("bypasses_creative"));
    }
}
