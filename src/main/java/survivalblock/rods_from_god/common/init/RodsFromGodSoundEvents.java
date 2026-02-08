package survivalblock.rods_from_god.common.init;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import survivalblock.atmosphere.atmospheric_api.not_mixin.registrant.delayed.DelayedSoundEventRegistrant;
import survivalblock.rods_from_god.common.RodsFromGod;

@SuppressWarnings("UnstableApiUsage")
public final class RodsFromGodSoundEvents {
    private RodsFromGodSoundEvents() {
    }

    public static final DelayedSoundEventRegistrant registrant = new DelayedSoundEventRegistrant(RodsFromGod::id);

    public static final SoundEvent SMOKE_BOMB_THROW = registrant.register("entity.rods_from_god.smoke_bomb.throw");
    public static final RegistryEntry.Reference<SoundEvent> TUNGSTEN_ROD_KINETIC_EXPLOSION = registrant.registerReference("entity.rods_from_god.tungsten_rod.kinetic_explosion");
    public static final SoundEvent SOLAR_PRISM_HEADSET_AMBIENT = registrant.register("item.rods_from_god.solar_prism_headset.ambient");

    public static void init() {
        registrant.consumeAll();
    }
}
