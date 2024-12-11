package survivalblock.rods_from_god.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.AimingDeviceComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

import java.util.List;


public class AimingDeviceUndoFireRecipe extends SpecialCraftingRecipe {

    public static final RecipeSerializer<AimingDeviceUndoFireRecipe> SERIALIZER = new SpecialRecipeSerializer<>(AimingDeviceUndoFireRecipe::new);

    public AimingDeviceUndoFireRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, RodsFromGod.id("crafting_special_aiming_device_undo_fire"), SERIALIZER);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return this.matches(input);
    }

    public boolean matches(CraftingRecipeInput input) { // I don't need world
        List<ItemStack> stacks = input.getStacks();
        return stacks.size() == 2 && isAimingDeviceAndHeartOfSea(stacks.getFirst(), stacks.getLast());
    }

    public boolean isAimingDeviceAndHeartOfSea(ItemStack first, ItemStack second) {
        if (first == null || first.isEmpty() || second == null || second.isEmpty()) {
            return false;
        }
        return (first.isOf(RodsFromGodItems.AIMING_DEVICE) && second.isOf(Items.HEART_OF_THE_SEA))
                || (second.isOf(RodsFromGodItems.AIMING_DEVICE) && first.isOf(Items.HEART_OF_THE_SEA));
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
        if (!stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE, AimingDeviceComponent.DEFAULT_INSTANCE).createsFire()) {
            return ItemStack.EMPTY;
        }
        ItemStack returnStack = stack.copy();
        AimingDeviceComponent aimingDeviceComponent = returnStack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE, AimingDeviceComponent.DEFAULT_INSTANCE);
        returnStack.set(RodsFromGodDataComponentTypes.AIMING_DEVICE, new AimingDeviceComponent.Builder().copyFrom(aimingDeviceComponent).createsFire(false).build());
        return returnStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return (width >= 2 && height >= 1) || (width >= 1 && height >= 2);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
