package survivalblock.rods_from_god.common.compat;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;
import survivalblock.rods_from_god.common.recipe.AimingDeviceFireRecipe;
import survivalblock.rods_from_god.common.recipe.AimingDeviceUndoFireRecipe;

import java.util.List;

public class RodsFromGodEMIPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        RecipeManager manager = registry.getRecipeManager();

        for (RecipeEntry<CraftingRecipe> entry : manager.listAllOfType(RecipeType.CRAFTING)) {
            CraftingRecipe recipe = entry.value();
            if (recipe instanceof AimingDeviceFireRecipe) {
                ItemStack aimingDeviceStack = new ItemStack(RodsFromGodItems.AIMING_DEVICE);
                EmiStack aimingDevice = EmiStack.of(aimingDeviceStack);
                ItemStack resultItemstack = new ItemStack(RodsFromGodItems.AIMING_DEVICE);
                resultItemstack.set(RodsFromGodDataComponentTypes.AIMING_DEVICE_CREATES_FIRE, true);
                registry.addRecipe(new EmiCraftingRecipe(List.of(aimingDevice, EmiStack.of(Items.NETHER_STAR)),
                        EmiStack.of(resultItemstack),
                        RodsFromGod.id("/crafting/aiming_device_fire"),
                        true));
            } else if (recipe instanceof AimingDeviceUndoFireRecipe) {
                ItemStack aimingDeviceStack = new ItemStack(RodsFromGodItems.AIMING_DEVICE);
                aimingDeviceStack.set(RodsFromGodDataComponentTypes.AIMING_DEVICE_CREATES_FIRE, true);
                EmiStack aimingDevice = EmiStack.of(aimingDeviceStack);
                ItemStack resultItemstack = new ItemStack(RodsFromGodItems.AIMING_DEVICE);
                registry.addRecipe(new EmiCraftingRecipe(List.of(aimingDevice, EmiStack.of(Items.HEART_OF_THE_SEA)),
                        EmiStack.of(resultItemstack),
                        RodsFromGod.id("/crafting/aiming_device_undo_fire"),
                        true));
            }
        }
    }
}
