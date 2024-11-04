package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.rods_from_god.common.init.*;

import java.util.concurrent.CompletableFuture;

public class RodsFromGodEnUsLangGenerator extends FabricLanguageProvider {

    public RodsFromGodEnUsLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        // entity
        translationBuilder.add(RodsFromGodEntityTypes.TUNGSTEN_ROD, "Tungsten Rod");
        translationBuilder.add(RodsFromGodEntityTypes.SMOKE_BOMB, "Smoke Bomb");
        translationBuilder.add(RodsFromGodEntityTypes.ROD_LANDING_MARKER, "Tungsten Rod Landing Marker");

        // damage
        translationBuilder.add("death.attack.rods_from_god.kinetic_explosion", "%1$s was struck by a kinetic bombardment");
        translationBuilder.add("death.attack.rods_from_god.kinetic_explosion.player", "%1$s was struck by a kinetic bombardment from %2$s");
        translationBuilder.add("death.attack.rods_from_god.kinetic_explosion.item", "%1$s was struck by a kinetic bombardment from %2$s using %3$s");
        translationBuilder.add("death.attack.rods_from_god.solar_laser", "%1$s was hit by a solar laser from %2$s");
        translationBuilder.add("death.attack.rods_from_god.solar_laser.player", "%1$s was hit by a solar laser from %2$s");
        translationBuilder.add("death.attack.rods_from_god.solar_laser.item", "%1$s was hit by a solar laser from %2$s using %3$s");
        translationBuilder.add("death.attack.rods_from_god.solar_laser_overheat", "%1$s overheated from using a solar laser");
        translationBuilder.add("death.attack.rods_from_god.solar_laser_overheat.player", "%%1$s overheated from using a solar laser whilst fighting %2$s");
        translationBuilder.add("death.attack.rods_from_god.solar_laser_overheat.item", "%%1$s overheated from using a solar laser whilst fighting %2$s");

        // gamerules
        translationBuilder.add("gamerule.rodsFromGodKineticExplosionCanMakeFire", "Rods from God - Kinetic Explosions Can Produce Fire");
        translationBuilder.add("gamerule.rodsFromGodKineticExplosionSourceType", "Rods from God - Kinetic Explosion Source Type");
        translationBuilder.add("gamerule.rodsFromGodSmokeBombsTriggerBlocks", "Rods from God - Smoke Bombs Trigger Blocks (Similar to Wind Charges)");

        // item
        translationBuilder.add(RodsFromGodItems.AIMING_DEVICE, "Rods from God - Aiming Device");
        translationBuilder.add(RodsFromGodItems.SMOKE_BOMB, "Smoke Bomb");
        translationBuilder.add(RodsFromGodItems.LIGHTNING_SPLASH_POTION, "Splash Potion of Lightning");
        translationBuilder.add(RodsFromGodItems.SUPER_BOUNCY_SLIME_BLOCK, "Super Bouncy Slime Block");
        translationBuilder.add(RodsFromGodItems.CORRUPTED_STAR_FRAGMENT, "Corrupted Star Fragment");
        translationBuilder.add(RodsFromGodItems.THE_ONE_WATCH, "The One Watch");
        translationBuilder.add(RodsFromGodItems.EVOKER_INVOKER, "The Illager's Guide to Spellcasting : Volume 1 : Fangs");
        translationBuilder.add(RodsFromGodItems.SOLAR_PRISM_HEADSET, "Solar Prism Headset");
        translationBuilder.add("item.minecraft.potion.effect.gravity_increase", "Potion of Gravity Increase");
        translationBuilder.add("item.minecraft.splash_potion.effect.gravity_increase", "Splash Potion of Gravity Increase");
        translationBuilder.add("item.minecraft.lingering_potion.effect.gravity_increase", "Lingering Potion of Gravity Increase");

        // screen
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand", "Subcommand");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.query", "Query (no arguments)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.rate", "Rate (1 float argument, default value is 20)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.step", "Step (1 time argument or stop)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.sprint", "Sprint (1 time argument or stop)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.freeze", "Freeze (no arguments)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.unfreeze", "Unfreeze (no arguments)");

        // components
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_cooldown", "Cooldown Ticks : %s");
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_max_explosions", "Maximum Number of Explosions : %s");
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_explosion_power", "Explosion Power : %s");
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_inverse_explosion_damage_factor", "Inverse Explosion Damage Factor : %s");
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_creates_fire", "Creates Fire");
        translationBuilder.add("item.rods_from_god.corrupted_star_fragment.keep_corrupted_star_fragment", "Keep on Explosion");
        translationBuilder.add("item.rods_from_god.the_one_watch.arguments", "Argument(s) : %s");
        translationBuilder.add("item.rods_from_god.solar_prism_headset.no_overheat", "Does Not Overheat");
        translationBuilder.add("item.rods_from_god.evoker_invoker.evoker_invoker_cooldown", "Cooldown Ticks : %s");

        // tooltip lore
        translationBuilder.add("item.rods_from_god.the_one_watch.lore.hidden", "Press and hold shift to see lore");
        translationBuilder.add("item.rods_from_god.the_one_watch.lore.0", "Two Watches for the modders venturing alone,");
        translationBuilder.add("item.rods_from_god.the_one_watch.lore.1", "Three for the teams under the sky,");
        translationBuilder.add("item.rods_from_god.the_one_watch.lore.2", "One for the dragon sleeping in stone,");
        translationBuilder.add("item.rods_from_god.the_one_watch.lore.3", "All for participants ready to try,");
        translationBuilder.add("item.rods_from_god.the_one_watch.lore.4", "In the Land of Stasis where the hourglasses fly.");
        translationBuilder.add("item.rods_from_god.the_one_watch.lore.5", "  One Watch to perceive all, One Watch to lap them,");
        translationBuilder.add("item.rods_from_god.the_one_watch.lore.6", "  One Watch to return all, and One Watch to guide them");
        translationBuilder.add("item.rods_from_god.the_one_watch.lore.7", "In the Land of Stasis where the hourglasses fly.");

        // sounds
        translationBuilder.add("subtitles.entity.rods_from_god.smoke_bomb.throw", "Smoke Bomb Thrown");
        translationBuilder.add("subtitles.entity.rods_from_god.tungsten_rod.kinetic_explosion", "Kinetic Explosion");
        translationBuilder.add("subtitles.item.rods_from_god.solar_prism_headset.ambient", "Solar Prism Headset Hums");

        // item tags
        translationBuilder.add(RodsFromGodTags.RodsFromGodItemTags.WATCHES, "Modfest 1.21 Watches");

        // status effect
        translationBuilder.add(RodsFromGodStatusEffects.GRAVITY_INCREASE.value(), "Gravity Increase");
    }
}
