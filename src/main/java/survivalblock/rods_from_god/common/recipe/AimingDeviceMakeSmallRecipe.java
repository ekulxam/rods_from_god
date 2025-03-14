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

import static survivalblock.rods_from_god.common.component.item.AimingDeviceComponent.SMALL_BUT_SPEEDY;

public class AimingDeviceMakeSmallRecipe extends AimingDeviceRecipe {

    public static final RecipeSerializer<AimingDeviceMakeSmallRecipe> SERIALIZER = new SpecialRecipeSerializer<>(AimingDeviceMakeSmallRecipe::new);

    public AimingDeviceMakeSmallRecipe(CraftingRecipeCategory category) {
        super(category, Items.SUGAR);
    }

    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, RodsFromGod.id("crafting_special_aiming_device_small"), SERIALIZER);
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
        AimingDeviceComponent other = stack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE, AimingDeviceComponent.DEFAULT_INSTANCE);
        if (other.gravity() == SMALL_BUT_SPEEDY.gravity() && other.scale() == SMALL_BUT_SPEEDY.scale() && other.cooldown() == SMALL_BUT_SPEEDY.cooldown()) {
            return ItemStack.EMPTY;
        }
        ItemStack returnStack = stack.copy();
        AimingDeviceComponent aimingDeviceComponent = returnStack.getOrDefault(RodsFromGodDataComponentTypes.AIMING_DEVICE, AimingDeviceComponent.DEFAULT_INSTANCE);
        returnStack.set(RodsFromGodDataComponentTypes.AIMING_DEVICE, new AimingDeviceComponent.Builder()
                .copyFrom(aimingDeviceComponent)
                .scale(SMALL_BUT_SPEEDY.scale())
                .gravity(SMALL_BUT_SPEEDY.gravity())
                .cooldown(SMALL_BUT_SPEEDY.cooldown()).build());
        return returnStack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
