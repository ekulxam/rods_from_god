package survivalblock.rods_from_god.mixin.superbouncyslimeblock;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

import static survivalblock.rods_from_god.common.component.cca.entity.SlimeBlockFlyingComponent.MAX_BOUNCE_AIR_TICKS;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @ModifyReturnValue(method = "getMaxAllowedFloatingTicks", at = @At("RETURN"))
    private int slimeBlockFlying(int original, Entity entity) {
        if (original > MAX_BOUNCE_AIR_TICKS) {
            return original;
        }
        if (!RodsFromGodEntityComponents.SLIME_BLOCK_FLYING.get(entity).shouldFly()) {
            return original;
        }
        return MAX_BOUNCE_AIR_TICKS;
    }
}
