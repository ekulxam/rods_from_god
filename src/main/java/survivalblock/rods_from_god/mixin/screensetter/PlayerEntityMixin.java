package survivalblock.rods_from_god.mixin.screensetter;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import survivalblock.rods_from_god.common.entity.BookEntity;
import survivalblock.rods_from_god.common.entity.injected_interface.RodsFromGodScreenSetter;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements RodsFromGodScreenSetter {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult rods_from_god$openBookTargetScreen(ActionResult original, Hand hand, BookEntity book) {
        return original;
    }

    @Override
    public boolean rods_from_god$openOneWatchScreen(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, StackReference cursorStackReference) {
        return ClickType.RIGHT == clickType;
    }
}
