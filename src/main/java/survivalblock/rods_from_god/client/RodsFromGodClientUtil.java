package survivalblock.rods_from_god.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;
import survivalblock.rods_from_god.mixin.solarprismheadset.client.BeaconBlockEntityRendererAccessor;

public class RodsFromGodClientUtil {

    public static void renderBeeeeeem(AbstractClientPlayerEntity clientPlayer, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider) {
        if (!shouldRenderBeam(clientPlayer)) {
            return;
        }
        HitResult hitResult = clientPlayer.raycast(384, tickDelta, false);
        if (!(hitResult instanceof BlockHitResult)) {
            return;
        }
        matrixStack.push();
        matrixStack.translate(0, clientPlayer.getEyeHeight(clientPlayer.getPose()), 0);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-clientPlayer.getYaw()));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(clientPlayer.getPitch() + 90));
        double distance = Math.sqrt(clientPlayer.getEyePos().squaredDistanceTo(hitResult.getPos()));
        renderBeam(matrixStack, vertexConsumerProvider,
                BeaconBlockEntityRenderer.BEAM_TEXTURE, tickDelta, 1.0F, clientPlayer.getWorld().getTime(), 0, (int) Math.round(distance),
                16777215, 0.2F, 0.25F);
        matrixStack.pop();
    }

    public static boolean shouldRenderBeam(AbstractClientPlayerEntity clientPlayer) {
        if (clientPlayer.isSpectator()) {
            return false;
        }
        if (!RodsFromGodEntityComponents.SOLAR_LASER.get(clientPlayer).underTheSun(true)) {
            return false;
        }
        return clientPlayer.getEquippedStack(EquipmentSlot.HEAD).isOf(RodsFromGodItems.SOLAR_PRISM_HEADSET);
    }

    @SuppressWarnings("UnreachableCode")
    public static void renderBeam(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Identifier textureId, float tickDelta, float heightScale, long worldTime, int yOffset, int maxY, int color, float innerRadius, float outerRadius) {
        int i = yOffset + maxY;
        matrices.push();
        float f = (float) Math.floorMod(worldTime, 40) + tickDelta;
        float g = maxY < 0 ? f : -f;
        float h = MathHelper.fractionalPart(g * 0.2F - (float) MathHelper.floor(g * 0.1F));
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f * 2.25F - 45.0F));
        float j;
        float m ;
        float n = -innerRadius;
        float q = -innerRadius;
        float t = -1.0F + h;
        float u = (float) maxY * heightScale * (0.5F / innerRadius) + t;
        BeaconBlockEntityRendererAccessor.rods_from_god$invokeRenderBeamLayer(matrices, vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(textureId, false)), color, yOffset, i, 0.0F, innerRadius, innerRadius, 0.0F, n, 0.0F, 0.0F, q, 0.0F, 1.0F, u, t);
        matrices.pop();
        j = -outerRadius;
        float k = -outerRadius;
        m = -outerRadius;
        n = -outerRadius;
        t = -1.0F + h;
        u = (float) maxY * heightScale + t;
        BeaconBlockEntityRendererAccessor.rods_from_god$invokeRenderBeamLayer(matrices, vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(textureId, true)), ColorHelper.Argb.withAlpha(32, color), yOffset, i, j, k, outerRadius, m, n, outerRadius, outerRadius, outerRadius, 0.0F, 1.0F, u, t);
        matrices.pop();
    }
}
