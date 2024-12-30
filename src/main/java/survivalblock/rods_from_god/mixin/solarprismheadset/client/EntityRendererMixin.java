package survivalblock.rods_from_god.mixin.solarprismheadset.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.rods_from_god.client.RodsFromGodClientUtil;
import survivalblock.rods_from_god.common.component.cca.entity.SolarLaserComponent;

@Mixin(value = EntityRenderer.class, priority = 10000) // I'm not joking around anymore
public class EntityRendererMixin {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void alwaysRenderSolarLaser(Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        GameRenderer gameRenderer = client.gameRenderer; // none of these should be null, but just in case
        if (gameRenderer == null) {
            return;
        }
        Camera camera = gameRenderer.getCamera();
        if (camera == null) {
            return;
        }
        if (!(entity instanceof AbstractClientPlayerEntity player)) {
            return;
        }
        double squaredDistance = Math.sqrt(player.getEyePos().squaredDistanceTo(camera.getPos()));
        int maxDistance = SolarLaserComponent.MAX_RENDER_DISTANCE;
        if (squaredDistance > (maxDistance * maxDistance)) {
            return;
        }
        if (RodsFromGodClientUtil.shouldRenderBeam(player)) {
            cir.setReturnValue(true);
        }
    }
}
