package survivalblock.rods_from_god.common.entity.injected_interface;

import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import survivalblock.rods_from_god.common.entity.BookEntity;

public interface RodsFromGodScreenSetter {

    default ActionResult rods_from_god$openBookTargetScreen(ActionResult original, Hand hand, BookEntity book) {
        throw new UnsupportedOperationException();
    }

    default boolean rods_from_god$openOneWatchScreen(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, StackReference cursorStackReference) {
        throw new UnsupportedOperationException();
    }
}
