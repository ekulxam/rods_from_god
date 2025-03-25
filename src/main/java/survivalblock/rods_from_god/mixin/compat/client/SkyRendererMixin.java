package survivalblock.rods_from_god.mixin.compat.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.component.cca.world.WorldLeverComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodWorldComponents;

@Pseudo
@Mixin(targets = "fr.tathan.sky_aesthetics.client.skies.renderer.SkyRenderer", remap = false)
public class SkyRendererMixin {

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lfr/tathan/sky_aesthetics/client/skies/record/CustomVanillaObject;sunSize()F", remap = false), remap = true)
    private float scaleCustomSunSize(float original, @Local(argsOnly = true)ClientWorld clientWorld) {
        return rods_from_god$getLiftedScale(original, clientWorld);
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lfr/tathan/sky_aesthetics/client/skies/record/CustomVanillaObject;moonSize()F", remap = false), remap = true)
    private float scaleCustomMoonSize(float original, @Local(argsOnly = true)ClientWorld clientWorld) {
        return rods_from_god$getLiftedScale(original, clientWorld);
    }

    @Unique
    private float rods_from_god$getLiftedScale(float original, ClientWorld world) {
        WorldLeverComponent worldLeverComponent = RodsFromGodWorldComponents.WORLD_LEVER.get(world);
        final float scale = WorldLeverComponent.MAX_CELESTIAL_SCALE;
        if (worldLeverComponent.isSwitching()) {
            int zoom = WorldLeverComponent.MAX_CELESTIAL_ZOOM;
            float delta = (float) (zoom - worldLeverComponent.getCelestialZoom()) / zoom;
            if (worldLeverComponent.lifted()) {
                return MathHelper.lerp(delta, original, original * scale);
            }
            return MathHelper.lerp(delta, original * scale, original);
        } else {
            if (worldLeverComponent.lifted()) {
                return original * scale;
            }
            return original;
        }
    }
}
