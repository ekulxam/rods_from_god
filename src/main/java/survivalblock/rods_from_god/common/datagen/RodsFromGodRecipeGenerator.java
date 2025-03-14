package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.DyeColor;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.block.SuperBouncySlimeBlock;
import survivalblock.rods_from_god.common.init.RodsFromGodBlocks;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;
import survivalblock.rods_from_god.common.recipe.AimingDeviceFireRecipe;
import survivalblock.rods_from_god.common.recipe.AimingDeviceCleansingRecipe;
import survivalblock.rods_from_god.common.recipe.AimingDeviceMakeSmallRecipe;

import java.util.concurrent.CompletableFuture;

public class RodsFromGodRecipeGenerator extends FabricRecipeProvider {

    public RodsFromGodRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, Items.SLIME_BLOCK, RecipeCategory.MISC, RodsFromGodItems.BOUNCIES.get(DyeColor.WHITE));

        offerBounciesDyeingRecipe(exporter, DyeColor.WHITE, Items.WHITE_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.ORANGE, Items.ORANGE_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.MAGENTA, Items.MAGENTA_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.YELLOW, Items.YELLOW_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.LIME, Items.LIME_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.PINK, Items.PINK_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.GRAY, Items.GRAY_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.CYAN, Items.CYAN_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.PURPLE, Items.PURPLE_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.BLUE, Items.BLUE_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.BROWN, Items.BROWN_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.GREEN, Items.GREEN_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.RED, Items.RED_DYE);
        offerBounciesDyeingRecipe(exporter, DyeColor.BLACK, Items.BLACK_DYE);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, RodsFromGodItems.CORRUPTED_STAR_FRAGMENT)
                .input(Items.CREEPER_HEAD).criterion(FabricRecipeProvider.hasItem(Items.CREEPER_HEAD),
                        FabricRecipeProvider.conditionsFromItem(Items.CREEPER_HEAD))
                .input(Items.NETHER_STAR).criterion(FabricRecipeProvider.hasItem(Items.NETHER_STAR),
                        FabricRecipeProvider.conditionsFromItem(Items.NETHER_STAR))
                .input(Items.TOTEM_OF_UNDYING).criterion(FabricRecipeProvider.hasItem(Items.TOTEM_OF_UNDYING),
                        FabricRecipeProvider.conditionsFromItem(Items.TOTEM_OF_UNDYING)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, RodsFromGodItems.SMOKE_BOMB, 4)
                .input(Items.GUNPOWDER).criterion(FabricRecipeProvider.hasItem(Items.GUNPOWDER),
                        FabricRecipeProvider.conditionsFromItem(Items.GUNPOWDER))
                .input(Items.FIRE_CHARGE).criterion(FabricRecipeProvider.hasItem(Items.FIRE_CHARGE),
                        FabricRecipeProvider.conditionsFromItem(Items.FIRE_CHARGE))
                .input(Items.GHAST_TEAR).criterion(FabricRecipeProvider.hasItem(Items.GHAST_TEAR),
                        FabricRecipeProvider.conditionsFromItem(Items.GHAST_TEAR))
                .input(Items.WIND_CHARGE).criterion(FabricRecipeProvider.hasItem(Items.WIND_CHARGE),
                        FabricRecipeProvider.conditionsFromItem(Items.WIND_CHARGE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, RodsFromGodItems.EVOKER_INVOKER).pattern("XTX").pattern("TBT").pattern("XTX")
                .input('X', Items.DIAMOND_BLOCK).criterion(FabricRecipeProvider.hasItem(Items.DIAMOND_BLOCK),
                        FabricRecipeProvider.conditionsFromItem(Items.DIAMOND_BLOCK))
                .input('T', Items.TOTEM_OF_UNDYING).criterion(FabricRecipeProvider.hasItem(Items.TOTEM_OF_UNDYING),
                        FabricRecipeProvider.conditionsFromItem(Items.TOTEM_OF_UNDYING))
                .input('B', Items.ENCHANTED_BOOK).criterion(FabricRecipeProvider.hasItem(Items.ENCHANTED_BOOK),
                        FabricRecipeProvider.conditionsFromItem(Items.ENCHANTED_BOOK)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, RodsFromGodItems.SOLAR_PRISM_HEADSET).pattern("ABA").pattern("DCD").pattern("ABA")
                .input('A', Items.TINTED_GLASS).criterion(FabricRecipeProvider.hasItem(Items.TINTED_GLASS),
                        FabricRecipeProvider.conditionsFromItem(Items.TINTED_GLASS))
                .input('B', Items.DIAMOND).criterion(FabricRecipeProvider.hasItem(Items.DIAMOND),
                        FabricRecipeProvider.conditionsFromItem(Items.DIAMOND))
                .input('C', Items.NETHERITE_HELMET).criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_HELMET),
                        FabricRecipeProvider.conditionsFromItem(Items.NETHERITE_HELMET))
                .input('D', Items.NETHER_STAR).criterion(FabricRecipeProvider.hasItem(Items.NETHER_STAR),
                        FabricRecipeProvider.conditionsFromItem(Items.NETHER_STAR)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, RodsFromGodBlocks.SYNTHESIS_TABLE)
                .input(Items.EXPERIENCE_BOTTLE).criterion(FabricRecipeProvider.hasItem(Items.EXPERIENCE_BOTTLE),
                        FabricRecipeProvider.conditionsFromItem(Items.EXPERIENCE_BOTTLE))
                .input(Items.BEACON).criterion(FabricRecipeProvider.hasItem(Items.BEACON),
                        FabricRecipeProvider.conditionsFromItem(Items.BEACON))
                .input(Items.ECHO_SHARD).criterion(FabricRecipeProvider.hasItem(Items.ECHO_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.ECHO_SHARD))
                .input(Items.END_CRYSTAL).criterion(FabricRecipeProvider.hasItem(Items.END_CRYSTAL),
                        FabricRecipeProvider.conditionsFromItem(Items.END_CRYSTAL))
                .input(Items.CRAFTING_TABLE).criterion(FabricRecipeProvider.hasItem(Items.CRAFTING_TABLE),
                        FabricRecipeProvider.conditionsFromItem(Items.CRAFTING_TABLE))
                .input(Items.NETHERITE_BLOCK).criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_BLOCK),
                        FabricRecipeProvider.conditionsFromItem(Items.NETHERITE_BLOCK))
                .input(Items.TOTEM_OF_UNDYING).criterion(FabricRecipeProvider.hasItem(Items.TOTEM_OF_UNDYING),
                        FabricRecipeProvider.conditionsFromItem(Items.TOTEM_OF_UNDYING))
                .input(Items.CONDUIT).criterion(FabricRecipeProvider.hasItem(Items.CONDUIT),
                        FabricRecipeProvider.conditionsFromItem(Items.CONDUIT))
                .input(Items.SHULKER_SHELL).criterion(FabricRecipeProvider.hasItem(Items.SHULKER_SHELL),
                        FabricRecipeProvider.conditionsFromItem(Items.SHULKER_SHELL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, RodsFromGodItems.AIMING_DEVICE).pattern("  A  ").pattern(" BCB ").pattern("ACDCA").pattern(" BCB ").pattern("  A  ")
                .input('A', Items.IRON_INGOT).criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .input('B', Items.GLASS).criterion(FabricRecipeProvider.hasItem(Items.GLASS),
                        FabricRecipeProvider.conditionsFromItem(Items.GLASS))
                .input('C', Items.CALIBRATED_SCULK_SENSOR).criterion(FabricRecipeProvider.hasItem(Items.CALIBRATED_SCULK_SENSOR),
                        FabricRecipeProvider.conditionsFromItem(Items.CALIBRATED_SCULK_SENSOR))
                .input('D', Items.RESPAWN_ANCHOR).criterion(FabricRecipeProvider.hasItem(Items.RESPAWN_ANCHOR),
                        FabricRecipeProvider.conditionsFromItem(Items.RESPAWN_ANCHOR)).offerTo(exporter);
        ComplexRecipeJsonBuilder.create(AimingDeviceFireRecipe::new).offerTo(exporter, RodsFromGod.id("aiming_device_fire"));
        ComplexRecipeJsonBuilder.create(AimingDeviceCleansingRecipe::new).offerTo(exporter, RodsFromGod.id("aiming_device_cleanse"));
        ComplexRecipeJsonBuilder.create(AimingDeviceMakeSmallRecipe::new).offerTo(exporter, RodsFromGod.id("aiming_device_small"));
    }

    public static void offerBounciesDyeingRecipe(RecipeExporter exporter, DyeColor dyeColor, ItemConvertible dye) {
        offerBounciesDyeingRecipe(exporter, RodsFromGodItems.BOUNCIES.get(dyeColor), dye, dyeColor);
    }

    public static void offerBounciesDyeingRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible dye, DyeColor dyeColor) {
        String itemPath = getItemPath(output);
        for (SuperBouncySlimeBlock bouncy : RodsFromGodBlocks.SUPER_BOUNCY_SLIME_BLOCKS.values()) {
            if (bouncy.asItem().equals(output.asItem())) {
                continue;
            }
            ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output)
                    .input(dye).criterion(FabricRecipeProvider.hasItem(dye),
                            FabricRecipeProvider.conditionsFromItem(dye))
                    .input(bouncy).criterion(FabricRecipeProvider.hasItem(bouncy),
                            FabricRecipeProvider.conditionsFromItem(bouncy))
                    .offerTo(exporter, RodsFromGod.id(bouncy.getDyeColor().getName() + "_" + itemPath));
        }
    }
}
