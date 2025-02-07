package survivalblock.rods_from_god.mixin.solarprismheadset.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.client.RodsFromGodClientUtil;

@Mixin(BeaconBlockEntityRenderer.class)
public class BeaconBlockEntityRendererMixin {

    @WrapWithCondition(method = "renderBeam(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/util/Identifier;FFJIIIFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V", ordinal = 0))
    private static boolean doNotGetMiddleOfBlockIfSolarPrism(MatrixStack instance, double x, double y, double z) {
        if (RodsFromGodClientUtil.renderingSolarPrismBeam) {
            RodsFromGodClientUtil.renderingSolarPrismBeam = false;
            return false;
        }
        return true;
    }
}
