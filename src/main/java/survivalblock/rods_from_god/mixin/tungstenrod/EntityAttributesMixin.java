package survivalblock.rods_from_god.mixin.tungstenrod;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {

    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "CONSTANT", args = "doubleValue=16.0"))
    private static double increaseMaxScale(double original) {
        return Math.max(16, original) * 32; // 512
    }
}
