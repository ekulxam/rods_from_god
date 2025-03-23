package survivalblock.rods_from_god.mixin.medusa;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import org.ladysnake.cca.api.v3.component.ComponentProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Mixin(value = ServerWorld.class, priority = 2000)
public class ServerWorldMixin {

    @Inject(method = "tickEntity", at = @At("HEAD"), cancellable = true)
    private void midasTouchCancelClientTickEntity(Entity entity, CallbackInfo ci){
        if (entity instanceof LivingEntity living && RodsFromGodEntityComponents.STONE_STATUE.get(living).isStatue()) {
            ((ComponentProvider) living).getComponentContainer().tickServerComponents();
            for (Entity passenger : entity.getPassengerList()) {
                ((ComponentProvider) passenger).getComponentContainer().tickServerComponents();
            }
            ci.cancel();
        }
    }
}
