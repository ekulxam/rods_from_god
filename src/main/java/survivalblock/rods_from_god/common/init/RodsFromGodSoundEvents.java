package survivalblock.rods_from_god.common.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodSoundEvents {

    public static final SoundEvent SMOKE_BOMB_THROW = SoundEvent.of(RodsFromGod.id("entity.rods_from_god.smoke_bomb.throw"));
    public static final RegistryEntry.Reference<SoundEvent> TUNGSTEN_ROD_KINETIC_EXPLOSION = registerReference("entity.rods_from_god.tungsten_rod.kinetic_explosion");

    private static RegistryEntry.Reference<SoundEvent> registerReference(String id) {
        return registerReference(RodsFromGod.id(id));
    }

    private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id) {
        return registerReference(id, id);
    }

    private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id, Identifier soundId) {
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
    }

    public static void init() {
        Registry.register(Registries.SOUND_EVENT, SMOKE_BOMB_THROW.getId(), SMOKE_BOMB_THROW);
    }
}
