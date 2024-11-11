package survivalblock.rods_from_god.common.init;


import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodTags {

    public static class RodsFromGodDamageTypeTags {
        public static final TagKey<DamageType> BYPASSES_CREATIVE = TagKey.of(RegistryKeys.DAMAGE_TYPE, RodsFromGod.id("bypasses_creative"));
    }
}
