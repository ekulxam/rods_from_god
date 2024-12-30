package survivalblock.rods_from_god.mixin.corruptedstarfragment.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.component.cca.entity.DeathExplosionComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @ModifyReturnValue(method = "getAnimationCounter", at = @At("RETURN"))
    private float getCreeperAnimationCounter(float original, LivingEntity living, float tickDelta) {
        if (!(living instanceof AbstractClientPlayerEntity abstractClientPlayerEntity)) {
            return original;
        }
        DeathExplosionComponent deathExplosionComponent = RodsFromGodEntityComponents.DEATH_EXPLOSION.get(abstractClientPlayerEntity);
        if (!deathExplosionComponent.shouldExplodeOnDeath()) {
            return original;
        }
        float g = deathExplosionComponent.getClientFuseTime(tickDelta);
        return (int)(g * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(g, 0.5F, 1.0F);
    }
}
