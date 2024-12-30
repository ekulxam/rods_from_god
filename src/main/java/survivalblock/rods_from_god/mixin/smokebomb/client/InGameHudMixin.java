package survivalblock.rods_from_god.mixin.smokebomb.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LayeredDrawer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

import java.util.function.BooleanSupplier;

import static survivalblock.rods_from_god.client.RodsFromGodClient.SMOKE_SCREEN_OVERLAY;

@Mixin(value = InGameHud.class, priority = 1500)
public abstract class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I", shift = At.Shift.BEFORE))
    private void renderSmokeScreenInMiscOverlays(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        this.rods_from_god$renderSmokeScreen(context, tickCounter);
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/LayeredDrawer;addSubDrawer(Lnet/minecraft/client/gui/LayeredDrawer;Ljava/util/function/BooleanSupplier;)Lnet/minecraft/client/gui/LayeredDrawer;", ordinal = 0))
    private LayeredDrawer addSubDrawerToRenderSmokeScreen(LayeredDrawer instance, LayeredDrawer drawer, BooleanSupplier shouldRender, Operation<LayeredDrawer> original) {
        LayeredDrawer alternateSmokeScreenDrawer = new LayeredDrawer().addLayer(this::rods_from_god$renderSmokeScreen);
        return original.call(instance, drawer, shouldRender).addSubDrawer(alternateSmokeScreenDrawer, () -> !shouldRender.getAsBoolean());
    }

    @Unique
    private void rods_from_god$renderSmokeScreen(DrawContext context, RenderTickCounter tickCounter) {
        if (this.client.player != null) {
            this.renderOverlay(context, SMOKE_SCREEN_OVERLAY, RodsFromGodEntityComponents.SMOKE_SCREEN.get(this.client.player).getOverlayFactor());
        }
    }
}
