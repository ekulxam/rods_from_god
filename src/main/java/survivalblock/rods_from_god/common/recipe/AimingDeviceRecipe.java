package survivalblock.rods_from_god.common.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.world.World;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

import java.util.List;

public abstract class AimingDeviceRecipe extends SpecialCraftingRecipe  {

    private final Item item;

    public AimingDeviceRecipe(CraftingRecipeCategory category, Item item) {
        super(category);
        this.item = item;
    }

    public static void init() {
        AimingDeviceFireRecipe.init();
        AimingDeviceCleansingRecipe.init();
        AimingDeviceMakeSmallRecipe.init();
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return this.matches(input);
    }

    public boolean matches(CraftingRecipeInput input) { // I don't need world
        List<ItemStack> stacks = input.getStacks();
        return stacks.size() == 2 && isAimingDeviceAndOther(this.item, stacks.getFirst(), stacks.getLast());
    }

    public static boolean isAimingDeviceAndOther(Item other, ItemStack first, ItemStack second) {
        if (first == null || first.isEmpty() || second == null || second.isEmpty()) {
            return false;
        }
        return (first.isOf(RodsFromGodItems.AIMING_DEVICE) && second.isOf(other))
                || (second.isOf(RodsFromGodItems.AIMING_DEVICE) && first.isOf(other));
    }

    @Override
    public boolean fits(int width, int height) {
        return (width >= 2 && height >= 1) || (width >= 1 && height >= 2);
    }
}
