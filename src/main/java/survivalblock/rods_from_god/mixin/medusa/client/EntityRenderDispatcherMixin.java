package survivalblock.rods_from_god.mixin.medusa.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import survivalblock.rods_from_god.common.component.cca.entity.StoneStatueComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

import java.util.Optional;

@Debug(export = true)
@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @ModifyVariable(method = "render", at = @At("HEAD"), index = 9, argsOnly = true)
    private float tickDeltaIsNowOne(float tickDelta, @Local(argsOnly = true)Entity entity) {
        Optional<StoneStatueComponent> optional = RodsFromGodEntityComponents.STONE_STATUE.maybeGet(entity);
        if (optional.isPresent() && optional.get().isStatue()) {
            return 1;
        }
        return tickDelta;
    }
}
