package survivalblock.rods_from_god.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.AimingDeviceComponent;
import survivalblock.rods_from_god.common.component.EvokerInvokerComponent;
import survivalblock.rods_from_god.common.component.TheOneWatchComponent;

public class RodsFromGodDataComponentTypes {


    public static final ComponentType<AimingDeviceComponent> AIMING_DEVICE = ComponentType.<AimingDeviceComponent>builder().codec(AimingDeviceComponent.CODEC).packetCodec(AimingDeviceComponent.PACKET_CODEC).build();
    public static final ComponentType<TheOneWatchComponent> THE_ONE_WATCH = ComponentType.<TheOneWatchComponent>builder().codec(TheOneWatchComponent.CODEC).packetCodec(TheOneWatchComponent.PACKET_CODEC).build();
    public static final ComponentType<EvokerInvokerComponent> EVOKER_INVOKER = ComponentType.<EvokerInvokerComponent>builder().codec(EvokerInvokerComponent.CODEC).packetCodec(EvokerInvokerComponent.PACKET_CODEC).build();

    public static final ComponentType<Boolean> KEEP_CORRUPTED_STAR_FRAGMENT = ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();
    public static final ComponentType<Boolean> SOLAR_PRISM_HEADSET_OVERHEAT = ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();

    public static void init() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("aiming_device"), AIMING_DEVICE);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("the_one_watch"), THE_ONE_WATCH);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("evoker_invoker"), EVOKER_INVOKER);

        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("keep_corrupted_star_fragment"), KEEP_CORRUPTED_STAR_FRAGMENT);
        Registry.register(Registries.DATA_COMPONENT_TYPE, RodsFromGod.id("solar_prism_headset_overheat"), SOLAR_PRISM_HEADSET_OVERHEAT);
    }
}
