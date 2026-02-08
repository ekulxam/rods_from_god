package survivalblock.rods_from_god.common.init;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.dynamic.DamageTypeRegistrant;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodDamageTypes {
    private RodsFromGodDamageTypes() {
    }

    public static final DamageTypeRegistrant registrant = new DamageTypeRegistrant(RodsFromGod::id);

    public static final RegistryKey<DamageType> KINETIC_EXPLOSION = registrant.register(
            "kinetic_explosion",
            new DamageType("rods_from_god.kinetic_explosion", 0.1F)
    );
    public static final RegistryKey<DamageType> SOLAR_LASER = registrant.register(
            "solar_laser",
            new DamageType("rods_from_god.solar_laser", 0.1F)
    );
    public static final RegistryKey<DamageType> SOLAR_LASER_OVERHEAT = registrant.register(
            "solar_laser_overheat",
            new DamageType("rods_from_god.solar_laser_overheat", 0.5F)
    );
    public static final RegistryKey<DamageType> NO_TOUCHY = registrant.register(
            "no_touching",
            new DamageType("rods_from_god.no_touching", 10F)
    );
}
