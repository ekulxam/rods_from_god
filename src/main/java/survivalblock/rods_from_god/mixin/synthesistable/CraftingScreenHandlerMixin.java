package survivalblock.rods_from_god.mixin.synthesistable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import survivalblock.rods_from_god.common.block.SynthesisTable.SynthesisScreenHandler;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {

    @Shadow
    @Final
    private static int INPUT_END;

    @ModifyExpressionValue(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/screen/ScreenHandlerType;CRAFTING:Lnet/minecraft/screen/ScreenHandlerType;", opcode = Opcodes.GETSTATIC))
    private static ScreenHandlerType<?> replaceTypeWithSynthesis(ScreenHandlerType<CraftingScreenHandler> original) {
        if (SynthesisScreenHandler.isSynthesisAndNotRegularCrafting) {
            SynthesisScreenHandler.isSynthesisAndNotRegularCrafting = false;
            return SynthesisScreenHandler.TYPE;
        }
        return original;
    }

    @WrapOperation(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At(value = "NEW", target = "(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/inventory/Inventory;III)Lnet/minecraft/screen/slot/CraftingResultSlot;"))
    private CraftingResultSlot shiftResultSlotToTheRight(PlayerEntity player, RecipeInputInventory input, Inventory inventory, int index, int x, int y, Operation<CraftingResultSlot> original) {
        if (!((CraftingScreenHandler) (Object) this instanceof SynthesisScreenHandler)) {
            return original.call(player, input, inventory, index, x, y);
        }
        return original.call(player, input, inventory, index, x + SynthesisScreenHandler.RESULT_SLOT_X_OFFSET, y);
    }

    @SuppressWarnings("DiscouragedShift")
    @ModifyExpressionValue(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At(value = "CONSTANT", args = "intValue=3"), slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/screen/CraftingScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", ordinal = 1, shift = At.Shift.BEFORE)))
    private int addMoreCraftingSlots(int original) {
        if (!((CraftingScreenHandler) (Object) this instanceof SynthesisScreenHandler)) {
            return original;
        }
        return Math.max(original, SynthesisScreenHandler.MAX_SIDE_LENGTH);
    }

    @WrapOperation(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At(value = "NEW", target = "(Lnet/minecraft/inventory/Inventory;III)Lnet/minecraft/screen/slot/Slot;", ordinal = 0))
    private Slot positionSynthesisCraftingSlots(Inventory inventory, int index, int x, int y, Operation<Slot> original) {
        if ((CraftingScreenHandler) (Object) this instanceof SynthesisScreenHandler) {
            return original.call(inventory, index, x - 18, y - 18);
        }
        return original.call(inventory, index, x, y);
    }

    @WrapOperation(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At(value = "NEW", target = "(Lnet/minecraft/inventory/Inventory;III)Lnet/minecraft/screen/slot/Slot;"), slice = @Slice(from = @At(value = "NEW", target = "(Lnet/minecraft/inventory/Inventory;III)Lnet/minecraft/screen/slot/Slot;", ordinal = 1)))
    private Slot positionSynthesisInventorySlots(Inventory inventory, int index, int x, int y, Operation<Slot> original) {
        if (!((CraftingScreenHandler) (Object) this instanceof SynthesisScreenHandler)) {
            ;return original.call(inventory, index, x, y);
        }
        return original.call(inventory, index, x, y + 18);
    }

    @ModifyExpressionValue(method = "quickMove", at = {
            @At(value = "CONSTANT", args = "intValue=10"),
            @At(value = "CONSTANT", args = "intValue=37"),
            @At(value = "CONSTANT", args = "intValue=46")
    })
    private int changeQuickMoveTargetsForSynthesis(int original) {
        if (!((CraftingScreenHandler) (Object) this instanceof SynthesisScreenHandler)) {
            return original;
        }
        return original + SynthesisScreenHandler.CRAFTING_SLOT_COUNT - INPUT_END;
    }
}
