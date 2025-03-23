package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.DyeColor;
import survivalblock.rods_from_god.common.block.SuperBouncySlimeBlock;
import survivalblock.rods_from_god.common.init.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static survivalblock.rods_from_god.common.init.RodsFromGodBlocks.SUPER_BOUNCY_SLIME_BLOCKS;

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
        translationBuilder.add(RodsFromGodEntityTypes.BOOK, "Omnipotent Arcane Book");
        translationBuilder.add(RodsFromGodEntityTypes.ENCHANTED_ARROW, "Enchanted Arrow");

        // damage
        translationBuilder.add("death.attack.rods_from_god.kinetic_explosion", "%1$s was struck by a kinetic bombardment");
        translationBuilder.add("death.attack.rods_from_god.kinetic_explosion.player", "%1$s was struck by a kinetic bombardment from %2$s");
        translationBuilder.add("death.attack.rods_from_god.kinetic_explosion.item", "%1$s was struck by a kinetic bombardment from %2$s using %3$s");
        translationBuilder.add("death.attack.rods_from_god.solar_laser", "%1$s was hit by a solar laser from %2$s");
        translationBuilder.add("death.attack.rods_from_god.solar_laser.player", "%1$s was hit by a solar laser from %2$s");
        translationBuilder.add("death.attack.rods_from_god.solar_laser.item", "%1$s was hit by a solar laser from %2$s using %3$s");
        translationBuilder.add("death.attack.rods_from_god.solar_laser_overheat", "%1$s overheated from using a solar laser");
        translationBuilder.add("death.attack.rods_from_god.solar_laser_overheat.player", "%1$s overheated from using a solar laser whilst fighting %2$s");
        translationBuilder.add("death.attack.rods_from_god.solar_laser_overheat.item", "%1$s overheated from using a solar laser whilst fighting %2$s using %3$s");

        // gamerules
        translationBuilder.add("gamerule.rodsFromGodKineticExplosionCanMakeFire", "Rods from God - Kinetic Explosions Can Produce Fire");
        translationBuilder.add("gamerule.rodsFromGodKineticExplosionSourceType", "Rods from God - Kinetic Explosion Source Type");
        translationBuilder.add("gamerule.rodsFromGodSmokeBombsTriggerBlocks", "Rods from God - Smoke Bombs Trigger Blocks (Similar to Wind Charges)");

        // item
        translationBuilder.add(RodsFromGodItems.AIMING_DEVICE, "Rods from God - Aiming Device");
        translationBuilder.add(RodsFromGodItems.SMOKE_BOMB, "Smoke Bomb");
        translationBuilder.add(RodsFromGodItems.LIGHTNING_SPLASH_POTION, "Splash Potion of Lightning");
        translationBuilder.add(RodsFromGodItems.CORRUPTED_STAR_FRAGMENT, "Corrupted Star Fragment");
        translationBuilder.add(RodsFromGodItems.THE_ONE_WATCH, "The One Watch");
        translationBuilder.add(RodsFromGodItems.EVOKER_INVOKER, "The Illager's Guide to Spellcasting : Volume 1 : Fangs");
        translationBuilder.add(RodsFromGodItems.SOLAR_PRISM_HEADSET, "Solar Prism Headset");
        translationBuilder.add("item.minecraft.potion.effect.gravity_increase", "Potion of Gravity Increase");
        translationBuilder.add("item.minecraft.splash_potion.effect.gravity_increase", "Splash Potion of Gravity Increase");
        translationBuilder.add("item.minecraft.lingering_potion.effect.gravity_increase", "Lingering Potion of Gravity Increase");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.gravity_increase", "Arrow of Gravity Increase");
        translationBuilder.add(RodsFromGodItems.MEDUSA_CURSE, "Μέδουσα Curse");

        // block
        translationBuilder.add(RodsFromGodBlocks.ARCHIMEDES_LEVER, "The Lever that Moves the World");
        translationBuilder.add("block.rods_from_god.archimedes_lever.tooltip.0", "\"Give me a lever long enough");
        translationBuilder.add("block.rods_from_god.archimedes_lever.tooltip.1", "and a fulcrum on which to place it,");
        translationBuilder.add("block.rods_from_god.archimedes_lever.tooltip.2", "and I shall move the world.\"");
        translationBuilder.add("block.rods_from_god.archimedes_lever.tooltip.3", " - Archimedes");
        translationBuilder.add(RodsFromGodBlocks.SYNTHESIS_TABLE, "Synthesis Table");

        for (Map.Entry<DyeColor, SuperBouncySlimeBlock> entry : SUPER_BOUNCY_SLIME_BLOCKS.entrySet()) {
            String dyeTranslation = getDyeTranslation(entry.getKey());
            translationBuilder.add(entry.getValue(), dyeTranslation + " Super Bouncy Slime Block");
        }

        // screen
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand", "Subcommand");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.query", "Query (no arguments)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.rate", "Rate (1 float argument, default value is 20)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.step", "Step (1 time argument or stop)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.sprint", "Sprint (1 time argument or stop)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.freeze", "Freeze (no arguments)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.subcommand.unfreeze", "Unfreeze (no arguments)");
        translationBuilder.add("item.rods_from_god.the_one_watch.screen.argument", "Arguments");

        translationBuilder.add("entity.rods_from_god.book.screen.onlyTargetsPlayers", "Only Targets Players");
        translationBuilder.add("entity.rods_from_god.book.screen.onlyTargetsPlayers.true", "True");
        translationBuilder.add("entity.rods_from_god.book.screen.onlyTargetsPlayers.false", "False");
        translationBuilder.add("entity.rods_from_god.book.screen.scale", "Scale");
        translationBuilder.add("entity.rods_from_god.book.screen.projectileDuration", "Projectile Duration");
        translationBuilder.add("entity.rods_from_god.book.screen.range", "Range");

        // components
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_cooldown", "Cooldown Ticks : %s");
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_max_explosions", "Maximum Number of Explosions : %s");
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_explosion_power", "Explosion Power : %s");
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_inverse_explosion_damage_factor", "Inverse Explosion Damage Factor : %s");
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_gravity", "Gravity : %s");
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_scale", "Scale : %s");
        translationBuilder.add("item.rods_from_god.aiming_device.aiming_device_creates_fire", "Creates Fire");
        translationBuilder.add("item.rods_from_god.corrupted_star_fragment.keep_corrupted_star_fragment", "Keep on Explosion");
        translationBuilder.add("item.rods_from_god.the_one_watch.arguments", "Argument(s) : %s");
        translationBuilder.add("item.rods_from_god.solar_prism_headset.no_overheat", "Does Not Overheat");
        translationBuilder.add("item.rods_from_god.solar_prism_headset.always_active", "Always Active");
        translationBuilder.add("item.rods_from_god.evoker_invoker.evoker_invoker_cooldown", "Cooldown Ticks : %s");
        translationBuilder.add("item.rods_from_god.evoker_invoker.evoker_invoker_max_sets", "Maximum Number of Fang Sets : %s");
        translationBuilder.add("item.rods_from_god.evoker_invoker.evoker_invoker_max_distance", "Maximum Distance : %s");

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

        // status effect
        translationBuilder.add(RodsFromGodStatusEffects.GRAVITY_INCREASE.value(), "Gravity Increase");

        // command
        translationBuilder.add("commands.rodsfromgod.worldlever.setlifted.success", "Set to the value of lifted in the World Lever Component to %2$s for dimension %1$s");
        translationBuilder.add("commands.rodsfromgod.worldlever.query.switching.true", "The World Lever Component of dimension %1$s is currently switching");
        translationBuilder.add("commands.rodsfromgod.worldlever.query.switching.false", "The World Lever Component of dimension %1$s is not currently switching");
        translationBuilder.add("commands.rodsfromgod.worldlever.query.lifted.true", "The World Lever Component of dimension %1$s is currently lifted");
        translationBuilder.add("commands.rodsfromgod.worldlever.query.lifted.false", "The World Lever Component of dimension %1$s is not currently lifted");
        translationBuilder.add("commands.rodsfromgod.worldlever.query.canswitch.true", "The World Lever Component of dimension %1$s can normally switch");
        translationBuilder.add("commands.rodsfromgod.worldlever.query.canswitch.false", "The World Lever Component of dimension %1$s cannot normally switch");
        translationBuilder.add("commands.rodsfromgod.medusastatue.set_statue", "Successfully turned %1$s into a statue.");
        translationBuilder.add("commands.rodsfromgod.medusastatue.remove_statue", "Successfully freed %1$s from being a statue.");
        translationBuilder.add("argument.rodsfromgod.medusastatue.entity_not_supported", "Entity %1$s does not provide StoneStatueComponent.");

        // packs
        translationBuilder.add("dataPack.rods_from_god.archimedes_lever_allow_lifting_overworld.name", "Rods from God - Archimedes Lever : Allow Lifting Overworld");

        translationBuilder.add("resourcePack.rods_from_god.animatedaimingdevice.name", "Animated Aiming Device");

        // config
        translationBuilder.add("rods_from_god.yacl.category.main", "Rods from God");
        translationBuilder.add("rods_from_god.yacl.category.main.tooltip", "Config");
        translationBuilder.add("rods_from_god.yacl.group.client", "Client");
        translationBuilder.add("rods_from_god.yacl.option.boolean.allowScreenShakingForArchimedesLever", "Archimedes Lever - Allow Screen Shaking");
        translationBuilder.add("rods_from_god.yacl.option.boolean.allowScreenShakingForArchimedesLever.desc", "Toggles the option for screen shaking during world lifting caused by the Archimedes Lever");

        // other
        translationBuilder.add("rods_from_god.archimedes_lever.unknown_y", "??? (the world has been lifted)");
    }

    public static String getDyeTranslation(DyeColor dyeColor) {
        switch (dyeColor) {
            case WHITE -> {
                return "White";
            }
            case ORANGE -> {
                return "Orange";
            }
            case MAGENTA -> {
                return "Magenta";
            }
            case LIGHT_BLUE -> {
                return "Light Blue";
            }
            case YELLOW -> {
                return "Yellow";
            }
            case LIME -> {
                return "Lime";
            }
            case PINK -> {
                return "Pink";
            }
            case GRAY -> {
                return "Gray";
            }
            case LIGHT_GRAY -> {
                return "Light Gray";
            }
            case CYAN -> {
                return "Cyan";
            }
            case PURPLE -> {
                return "Purple";
            }
            case BLUE -> {
                return "Blue";
            }
            case BROWN -> {
                return "Brown";
            }
            case GREEN -> {
                return "Green";
            }
            case RED -> {
                return "Red";
            }
            case BLACK -> {
                return "Black";
            }
        }
        throw new IllegalArgumentException("Dye Color was not found!");
    }
}
