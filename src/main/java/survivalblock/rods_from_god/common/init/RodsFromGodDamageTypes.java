package survivalblock.rods_from_god.common.init;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import survivalblock.rods_from_god.common.RodsFromGod;

import java.util.HashMap;
import java.util.Map;

public class RodsFromGodDamageTypes {
    public static final RegistryKey<DamageType> KINETIC_EXPLOSION = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, RodsFromGod.id("kinetic_explosion"));
    public static final RegistryKey<DamageType> SOLAR_LASER = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, RodsFromGod.id("solar_laser"));
    public static final RegistryKey<DamageType> SOLAR_LASER_SECONDARY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, RodsFromGod.id("solar_laser"));
    public static final RegistryKey<DamageType> SOLAR_LASER_OVERHEAT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, RodsFromGod.id("solar_laser_overheat"));

    public static RegistryEntry.Reference<DamageType> get(RegistryKey<DamageType> key, World world) {
        return world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key);
    }

    /**
     * Creates a map with the {@link RegistryKey<DamageType>}s as keys and {@link DamageType}s as values
     * @return an {@link ImmutableMap}
     */
    public static ImmutableMap<RegistryKey<DamageType>, DamageType> asDamageTypes() {
        Map<RegistryKey<DamageType>, DamageType> damageTypes = new HashMap<>();
        damageTypes.put(KINETIC_EXPLOSION, new DamageType("rods_from_god.kinetic_explosion", 0.1F));
        damageTypes.put(SOLAR_LASER_SECONDARY, new DamageType("rods_from_god.solar_laser", 0.1F));
        damageTypes.put(SOLAR_LASER_OVERHEAT, new DamageType("rods_from_god.solar_laser_overheat", 0.5F));
        return ImmutableMap.copyOf(damageTypes);
    }

    public static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        for (Map.Entry<RegistryKey<DamageType>, DamageType> entry : asDamageTypes().entrySet()) {
            damageTypeRegisterable.register(entry.getKey(), entry.getValue());
        }
    }
}
