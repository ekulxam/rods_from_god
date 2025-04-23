package survivalblock.rods_from_god.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.RotationAxis;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.cca.entity.SolarLaserComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

public class RodsFromGodClientUtil {

    public static final Identifier ANIMATED_AIMING_DEVICE_PACK = RodsFromGod.id("animatedaimingdevice");

    public static boolean renderingSolarPrismBeam = false;

    public static void renderBeeeeeem(AbstractClientPlayerEntity clientPlayer, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider) {
        if (!shouldRenderBeam(clientPlayer)) {
            return;
        }
        HitResult hitResult = clientPlayer.raycast(SolarLaserComponent.MAX_RAYCAST_DISTANCE, tickDelta, false);
        if (!(hitResult instanceof BlockHitResult)) {
            return;
        }
        matrixStack.push();
        matrixStack.translate(0, clientPlayer.getEyeHeight(clientPlayer.getPose()), 0);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-clientPlayer.getYaw()));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(clientPlayer.getPitch() + 90));
        double distance = Math.sqrt(clientPlayer.getEyePos().squaredDistanceTo(hitResult.getPos()));
        renderingSolarPrismBeam = true;
        BeaconBlockEntityRenderer.renderBeam(matrixStack, vertexConsumerProvider,
                BeaconBlockEntityRenderer.BEAM_TEXTURE, tickDelta, 1.0F, clientPlayer.getWorld().getTime(), 0, (int) Math.round(distance),
                16777215, 0.2F, 0.25F);
        matrixStack.pop();
    }

    public static boolean shouldRenderBeam(AbstractClientPlayerEntity clientPlayer) {
        if (clientPlayer.isSpectator()) {
            return false;
        }
        return RodsFromGodEntityComponents.SOLAR_LASER.get(clientPlayer).underTheSun(true);
    }
}
