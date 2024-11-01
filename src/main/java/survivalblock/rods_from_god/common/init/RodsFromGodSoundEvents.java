package survivalblock.rods_from_god.common.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import survivalblock.rods_from_god.common.RodsFromGod;

public class RodsFromGodSoundEvents {

    public static final SoundEvent SMOKE_BOMB_THROW = SoundEvent.of(RodsFromGod.id("entity.rods_from_god.smoke_bomb.throw"));

    public static void init() {
        Registry.register(Registries.SOUND_EVENT, SMOKE_BOMB_THROW.getId(), SMOKE_BOMB_THROW);
    }
}
