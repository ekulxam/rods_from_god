package survivalblock.rods_from_god.mixin.synthesistable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.recipe.RawShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.block.SynthesisTable.SynthesisScreenHandler;

@Mixin(RawShapedRecipe.class)
public class RawShapedRecipeMixin {

    @Mixin(RawShapedRecipe.Data.class)
    public static class DataMixin {

        @ModifyExpressionValue(method = "method_55096", at = @At(value = "CONSTANT", args = "intValue=3"))
        private static int increaseRowAndColumnLimit(int original) {
            return Math.max(original, SynthesisScreenHandler.MAX_SIDE_LENGTH);
        }
    }
}
