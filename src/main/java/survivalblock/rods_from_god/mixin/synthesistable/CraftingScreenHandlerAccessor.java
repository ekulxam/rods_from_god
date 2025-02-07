package survivalblock.rods_from_god.mixin.synthesistable;

import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CraftingScreenHandler.class)
public interface CraftingScreenHandlerAccessor {

    @Accessor("context")
    ScreenHandlerContext rods_from_god$getContext();
}
