package survivalblock.rods_from_god.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.item.AimingDeviceComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

import java.util.List;

public class AimingDeviceCleansingRecipe extends AimingDeviceRecipe {

    public static final RecipeSerializer<AimingDeviceCleansingRecipe> SERIALIZER = new SpecialRecipeSerializer<>(AimingDeviceCleansingRecipe::new);

    public AimingDeviceCleansingRecipe(CraftingRecipeCategory category) {
        super(category, Items.HEART_OF_THE_SEA);
    }

    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, RodsFromGod.id("crafting_special_aiming_device_cleanse"), SERIALIZER);
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        if (!matches(input)) {
            return ItemStack.EMPTY;
        }
        List<ItemStack> stacks = input.getStacks();
        ItemStack stack = stacks.getFirst();
        if (!stack.isOf(RodsFromGodItems.AIMING_DEVICE)) {
            stack = stacks.getLast();
        }
        if (stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE, AimingDeviceComponent.DEFAULT_INSTANCE).equals(AimingDeviceComponent.DEFAULT_INSTANCE)) {
            return ItemStack.EMPTY;
        }
        ItemStack returnStack = stack.copy();
        returnStack.set(RodsFromGodDataComponentTypes.AIMING_DEVICE, AimingDeviceComponent.DEFAULT_INSTANCE);
        return returnStack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
