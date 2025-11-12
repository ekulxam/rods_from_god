package survivalblock.rods_from_god.mixin.medusa.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @ModifyVariable(method = "render", at = @At("HEAD"), index = 9, argsOnly = true)
    private float tickDeltaIsNowOne(float tickDelta, @Local(argsOnly = true)Entity entity) {
        if (!(entity instanceof LivingEntity living)) {
            return tickDelta;
        }
        if (RodsFromGodEntityComponents.STONE_STATUE.get(living).isStatue()) {
            return 1;
        }
        return tickDelta;
    }
}
