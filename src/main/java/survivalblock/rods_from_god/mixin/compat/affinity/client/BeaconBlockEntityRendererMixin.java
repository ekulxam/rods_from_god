package survivalblock.rods_from_god.mixin.compat.affinity.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BeaconBlockEntityRenderer.class)
public class BeaconBlockEntityRendererMixin {

    @WrapOperation(method = "renderBeam(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/util/Identifier;FFJIIIFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getBeaconBeam(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;"))
    private static RenderLayer stopBreakingMyBeams(Identifier texture, boolean translucent, Operation<RenderLayer> original) {
        if (texture.getNamespace().equals("affinity")) {
            return original.call(texture, true);
        }
        return original.call(texture, translucent);
    }
}
