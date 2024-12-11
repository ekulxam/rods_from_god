package survivalblock.rods_from_god.mixin.solarprismheadset.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BeaconBlockEntityRenderer.class)
public interface BeaconBlockEntityRendererAccessor {

    @Invoker("renderBeamLayer")
    static void rods_from_god$invokeRenderBeamLayer(MatrixStack matrices, VertexConsumer vertices, int color, int yOffset, int height, float x1, float z1, float x2, float z2, float x3, float z3, float x4, float z4, float u1, float u2, float v1, float v2) {
        throw new UnsupportedOperationException();
    }
}
