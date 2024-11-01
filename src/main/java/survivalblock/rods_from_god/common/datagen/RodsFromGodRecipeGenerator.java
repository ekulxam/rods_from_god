package survivalblock.rods_from_god.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

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
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, RodsFromGodItems.CORRUPTED_STAR_FRAGMENT)
                .input(Items.CREEPER_HEAD).criterion(FabricRecipeProvider.hasItem(Items.CREEPER_HEAD),
                        FabricRecipeProvider.conditionsFromItem(Items.CREEPER_HEAD))
                .input(Items.NETHER_STAR).criterion(FabricRecipeProvider.hasItem(Items.NETHER_STAR),
                        FabricRecipeProvider.conditionsFromItem(Items.NETHER_STAR)).offerTo(exporter);
        // ComplexRecipeJsonBuilder.create(KaleidoscopeShaderTypeRecipe::new).offerTo(exporter, "kaleidoscope_shader_type");
    }
}
