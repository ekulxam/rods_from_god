package survivalblock.rods_from_god.common.init;


import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class RodsFromGodTags {


    public static class RodsFromGodItemTags {
        public static final TagKey<Item> WATCHES = TagKey.of(RegistryKeys.ITEM, Identifier.of("modfest", "watches"));
    }
}
