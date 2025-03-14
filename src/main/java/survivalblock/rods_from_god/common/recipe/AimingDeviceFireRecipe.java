package survivalblock.rods_from_god.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
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


public class AimingDeviceFireRecipe extends AimingDeviceRecipe {

    public static final RecipeSerializer<AimingDeviceFireRecipe> SERIALIZER = new SpecialRecipeSerializer<>(AimingDeviceFireRecipe::new);

    public AimingDeviceFireRecipe(CraftingRecipeCategory category) {
        super(category, Items.NETHER_STAR);
    }

    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, RodsFromGod.id("crafting_special_aiming_device_fire"), SERIALIZER);
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
        if (stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE, AimingDeviceComponent.DEFAULT_INSTANCE).createsFire()) {
            return ItemStack.EMPTY;
        }
        ItemStack returnStack = stack.copy();
        AimingDeviceComponent aimingDeviceComponent = returnStack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE, AimingDeviceComponent.DEFAULT_INSTANCE);
        returnStack.set(RodsFromGodDataComponentTypes.AIMING_DEVICE, new AimingDeviceComponent.Builder().copyFrom(aimingDeviceComponent).createsFire(true).build());
        return returnStack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
