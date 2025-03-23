package survivalblock.rods_from_god.mixin.medusa.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.ComponentProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Mixin(value = ClientWorld.class, priority = 2000)
public class ClientWorldMixin {

    @Inject(method = "tickEntity", at = @At("HEAD"), cancellable = true)
    private void medusaCancelClientTickEntity(Entity entity, CallbackInfo ci){
        if (entity instanceof LivingEntity living && RodsFromGodEntityComponents.STONE_STATUE.get(living).isStatue()) {
            ((ComponentProvider) entity).getComponentContainer().tickClientComponents();
            for (Entity passenger : entity.getPassengerList()) {
                ((ComponentProvider) passenger).getComponentContainer().tickClientComponents();
            }
            ci.cancel();
        }
    }
}
