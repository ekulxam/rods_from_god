package survivalblock.rods_from_god.mixin.archimedeslever.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.atmosphere.atmospheric_api.not_mixin.render.screenshake.BasicScreenShaker;
import survivalblock.atmosphere.atmospheric_api.not_mixin.render.screenshake.ScreenShaker;
import survivalblock.atmosphere.atmospheric_api.not_mixin.render.screenshake.client.ClientScreenShaker;
import survivalblock.rods_from_god.common.RodsFromGod;

@Environment(EnvType.CLIENT)
@Mixin(value = BasicScreenShaker.class, remap = false)
public abstract class BasicScreenShakerMixin implements ScreenShaker {

    @ModifyReturnValue(method = "getIntensity", at = @At("RETURN"))
    private float scaleWithNausea(float original) {
        if (RodsFromGod.isWorldLeverShake(this) && (BasicScreenShaker) (Object) this instanceof ClientScreenShaker) {
            return original * MinecraftClient.getInstance().options.getDistortionEffectScale().getValue().floatValue();
        }
        return original;
    }
}
