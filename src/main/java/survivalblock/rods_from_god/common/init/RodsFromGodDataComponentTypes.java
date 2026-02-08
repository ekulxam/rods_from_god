package survivalblock.rods_from_god.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.delayed.DelayedDataComponentTypeRegistrant;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.item.AimingDeviceComponent;
import survivalblock.rods_from_god.common.component.item.EvokerInvokerComponent;
import survivalblock.rods_from_god.common.component.item.SolarPrismHeadsetComponent;
import survivalblock.rods_from_god.common.component.item.TheOneWatchComponent;

@SuppressWarnings("UnstableApiUsage")
public class RodsFromGodDataComponentTypes {
    public static final DelayedDataComponentTypeRegistrant registrant = new DelayedDataComponentTypeRegistrant(RodsFromGod::id);

    public static final ComponentType<AimingDeviceComponent> AIMING_DEVICE = registrant.register("aiming_device", ComponentType.<AimingDeviceComponent>builder().codec(AimingDeviceComponent.CODEC).packetCodec(AimingDeviceComponent.PACKET_CODEC));
    public static final ComponentType<TheOneWatchComponent> THE_ONE_WATCH = registrant.register("the_one_watch", ComponentType.<TheOneWatchComponent>builder().codec(TheOneWatchComponent.CODEC).packetCodec(TheOneWatchComponent.PACKET_CODEC));
    public static final ComponentType<EvokerInvokerComponent> EVOKER_INVOKER = registrant.register("evoker_invoker", ComponentType.<EvokerInvokerComponent>builder().codec(EvokerInvokerComponent.CODEC).packetCodec(EvokerInvokerComponent.PACKET_CODEC));
    public static final ComponentType<SolarPrismHeadsetComponent> SOLAR_PRISM_HEADSET = registrant.register("", ComponentType.<SolarPrismHeadsetComponent>builder().codec(SolarPrismHeadsetComponent.CODEC).packetCodec(SolarPrismHeadsetComponent.PACKET_CODEC));

    public static final ComponentType<Boolean> KEEP_CORRUPTED_STAR_FRAGMENT = registrant.register("keep_corrupted_star_fragment", ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));

    public static void init() {
        registrant.consumeAll();
    }
}
