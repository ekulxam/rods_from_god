package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;
import survivalblock.rods_from_god.common.recipe.AimingDeviceFireRecipe;
import survivalblock.rods_from_god.common.recipe.AimingDeviceUndoFireRecipe;

import java.util.concurrent.CompletableFuture;

public class RodsFromGodRecipeGenerator extends FabricRecipeProvider {

    public RodsFromGodRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RodsFromGodItems.SUPER_BOUNCY_SLIME_BLOCK).pattern("xx").pattern("xx").input('x', Items.SLIME_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.SLIME_BLOCK),
                        FabricRecipeProvider.conditionsFromItem(Items.SLIME_BLOCK)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, RodsFromGodItems.CORRUPTED_STAR_FRAGMENT)
                .input(Items.CREEPER_HEAD).criterion(FabricRecipeProvider.hasItem(Items.CREEPER_HEAD),
                        FabricRecipeProvider.conditionsFromItem(Items.CREEPER_HEAD))
                .input(Items.NETHER_STAR).criterion(FabricRecipeProvider.hasItem(Items.NETHER_STAR),
                        FabricRecipeProvider.conditionsFromItem(Items.NETHER_STAR)).offerTo(exporter);
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
        ComplexRecipeJsonBuilder.create(AimingDeviceFireRecipe::new).offerTo(exporter, "aiming_device_fire");
        ComplexRecipeJsonBuilder.create(AimingDeviceUndoFireRecipe::new).offerTo(exporter, "aiming_device_undo_fire");
    }
}
