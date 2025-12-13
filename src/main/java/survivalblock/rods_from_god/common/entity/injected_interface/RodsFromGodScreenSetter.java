package survivalblock.rods_from_god.common.entity.injected_interface;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import survivalblock.rods_from_god.common.entity.BookEntity;

public interface RodsFromGodScreenSetter {

    default ActionResult rods_from_god$openBookTargetScreen(ActionResult original, Hand hand, BookEntity book) {
        throw new UnsupportedOperationException();
    }
}
