package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import survivalblock.atmosphere.atmospheric_api.not_mixin.damage_type.AtmosphericDamageTypeTags;
import survivalblock.rods_from_god.common.init.RodsFromGodDamageTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;
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
            getOrCreateTagBuilder(AtmosphericDamageTypeTags.BYPASSES_CREATIVE).add(RodsFromGodDamageTypes.SOLAR_LASER);
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

            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN).add(RodsFromGodDamageTypes.NO_TOUCHY);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY).add(RodsFromGodDamageTypes.NO_TOUCHY);
            getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK).add(RodsFromGodDamageTypes.NO_TOUCHY);
        }
    }

    public static class RodsFromGodWorldTagGenerator extends FabricTagProvider<DimensionType> {
        public RodsFromGodWorldTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.DIMENSION_TYPE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(RodsFromGodTags.SUN_AND_MOON).addOptional(DimensionTypes.OVERWORLD);
        }
    }
}
