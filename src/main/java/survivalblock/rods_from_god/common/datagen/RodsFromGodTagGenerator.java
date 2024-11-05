package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Identifier;
import survivalblock.rods_from_god.common.init.RodsFromGodDamageTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodTags;

import java.util.concurrent.CompletableFuture;

public class RodsFromGodTagGenerator {

    public static class RodsFromGodDamageTypeTagGenerator extends FabricTagProvider<DamageType> {
        public RodsFromGodDamageTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).add(RodsFromGodDamageTypes.SOLAR_LASER);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN).add(RodsFromGodDamageTypes.SOLAR_LASER);
            getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK).add(RodsFromGodDamageTypes.SOLAR_LASER);

            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).add(RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN).add(RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS).add(RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE).add(RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY).add(RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_SHIELD).add(RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_WOLF_ARMOR).add(RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT);
            getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK).add(RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT);
        }
    }

    public static class RodsFromGodItemTagGenerator extends FabricTagProvider.ItemTagProvider {

        public RodsFromGodItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(RodsFromGodTags.RodsFromGodItemTags.WATCHES).addOptional(Identifier.of("chronoception", "stopwatch"));
            getOrCreateTagBuilder(RodsFromGodTags.RodsFromGodItemTags.WATCHES).addOptional(Identifier.of("shattered_stopwatch", "stopwatch"));
            getOrCreateTagBuilder(RodsFromGodTags.RodsFromGodItemTags.WATCHES).addOptional(Identifier.of("rewindwatch", "rewind_watch"));
            getOrCreateTagBuilder(RodsFromGodTags.RodsFromGodItemTags.WATCHES).addOptional(Identifier.of("unstable_timepiece", "unstable_timepiece"));
        }
    }
}
