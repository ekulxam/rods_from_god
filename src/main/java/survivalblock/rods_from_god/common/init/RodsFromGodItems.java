package survivalblock.rods_from_god.common.init;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.item.AimingDeviceItem;
import survivalblock.rods_from_god.common.item.SmokeBombItem;

public class RodsFromGodItems {

    public static final Item AIMING_DEVICE = registerItem("aiming_device", new AimingDeviceItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE).component(RodsFromGodDataComponentTypes.AIMING_DEVICE_COOLDOWN, AimingDeviceItem.DEFAULT_COOLDOWN_TICKS)));
    public static final Item SMOKE_BOMB = registerItem("smoke_bomb", new SmokeBombItem(new Item.Settings().maxCount(64)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, RodsFromGod.id(name), item);
    }

    public static void init() {

    }
}
