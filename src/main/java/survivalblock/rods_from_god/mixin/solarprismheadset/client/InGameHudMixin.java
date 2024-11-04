package survivalblock.rods_from_god.mixin.solarprismheadset.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.component.SolarLaserComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Debug(export = true)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Unique
    private static final Identifier SOLAR_LASER_OVERHEAT_TICKS = RodsFromGod.id("textures/misc/solar_laser_overheat_overlay.png");

    @Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I", shift = At.Shift.BEFORE))
    private void renderSolarLaserOverheat(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (this.client.player != null) {
            SolarLaserComponent solarLaserOverheatComponent = RodsFromGodEntityComponents.SOLAR_LASER.get(this.client.player);
            int overheatTicks = solarLaserOverheatComponent.getOverheatTicks();
            if (overheatTicks <= 0) {
                return;
            }
            this.renderOverlay(context, SOLAR_LASER_OVERHEAT_TICKS, (float) overheatTicks / SolarLaserComponent.MAX_OVERHEATING_TICKS);
        }
    }
}
