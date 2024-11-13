package survivalblock.rods_from_god.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodDataComponentTypes {

    public static final ComponentType<Integer> AIMING_DEVICE_COOLDOWN = ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build();
    public static final ComponentType<Integer> AIMING_DEVICE_MAX_EXPLOSIONS = ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build();
    public static final ComponentType<Integer> AIMING_DEVICE_EXPLOSION_POWER = ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build();
    public static final ComponentType<Integer> AIMING_DEVICE_INVERSE_EXPLOSION_DAMAGE_FACTOR = ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build();
    public static final ComponentType<Double> AIMING_DEVICE_GRAVITY = ComponentType.<Double>builder().codec(Codec.DOUBLE).packetCodec(PacketCodecs.DOUBLE).build();
    public static final ComponentType<Float> AIMING_DEVICE_SCALE = ComponentType.<Float>builder().codec(Codec.FLOAT).packetCodec(PacketCodecs.FLOAT).build();
    public static final ComponentType<Boolean> AIMING_DEVICE_CREATES_FIRE = ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();
    public static final ComponentType<Boolean> KEEP_CORRUPTED_STAR_FRAGMENT = ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();
    public static final ComponentType<Boolean> SOLAR_PRISM_HEADSET_OVERHEAT = ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();
    public static final ComponentType<Integer> EVOKER_INVOKER_COOLDOWN = ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build();

    public static final ComponentType<String> ONE_WATCH_SUBCOMMAND = ComponentType.<String>builder().codec(Codec.STRING).packetCodec(PacketCodecs.STRING).build();
    public static final ComponentType<String> ONE_WATCH_ARGUMENTS = ComponentType.<String>builder().codec(Codec.STRING).packetCodec(PacketCodecs.STRING).build();

    public static void init() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("aiming_device_cooldown"), AIMING_DEVICE_COOLDOWN);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("aiming_device_max_explosions"), AIMING_DEVICE_MAX_EXPLOSIONS);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("aiming_device_explosion_power"), AIMING_DEVICE_EXPLOSION_POWER);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("aiming_device_inverse_explosion_damage_factor"), AIMING_DEVICE_INVERSE_EXPLOSION_DAMAGE_FACTOR);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("aiming_device_gravity"), AIMING_DEVICE_GRAVITY);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("aiming_device_scale"), AIMING_DEVICE_SCALE);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("aiming_device_creates_fire"), AIMING_DEVICE_CREATES_FIRE);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("keep_corrupted_star_fragment"), KEEP_CORRUPTED_STAR_FRAGMENT);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("solar_prism_headset_overheat"), SOLAR_PRISM_HEADSET_OVERHEAT);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("evoker_invoker_cooldown"), EVOKER_INVOKER_COOLDOWN);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("one_watch_subcommand"), ONE_WATCH_SUBCOMMAND);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("one_watch_arguments"), ONE_WATCH_ARGUMENTS);
    }
}
