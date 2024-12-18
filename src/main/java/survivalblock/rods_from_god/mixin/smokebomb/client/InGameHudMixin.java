package survivalblock.rods_from_god.mixin.smokebomb.client;

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
import survivalblock.rods_from_god.common.component.SmokeScreenComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

import static survivalblock.rods_from_god.client.RodsFromGodClient.SMOKE_SCREEN_OVERLAY;

@Mixin(value = InGameHud.class, priority = 1500)
public abstract class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I", shift = At.Shift.BEFORE))
    private void renderSmokeScreen(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (this.client.player != null) {
            SmokeScreenComponent smokeScreenComponent = RodsFromGodEntityComponents.SMOKE_SCREEN.get(this.client.player);
            int smokeScreenTicks = smokeScreenComponent.getSmokeScreenTicks();
            if (smokeScreenTicks <= 0) {
                return;
            }
            this.renderOverlay(context, SMOKE_SCREEN_OVERLAY, (float) smokeScreenTicks / SmokeScreenComponent.MAX_SMOKE_SCREEN_TICKS);
        }
    }
}
