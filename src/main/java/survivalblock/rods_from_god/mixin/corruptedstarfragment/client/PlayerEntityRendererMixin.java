package survivalblock.rods_from_god.mixin.corruptedstarfragment.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.component.DeathExplosionComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(method = "scale(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At("RETURN"))
    private void scaleLikeCreeper(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f, CallbackInfo ci) {
        DeathExplosionComponent deathExplosionComponent = RodsFromGodEntityComponents.DEATH_EXPLOSION.get(abstractClientPlayerEntity);
        if (!deathExplosionComponent.shouldExplodeOnDeath()) {
            return;
        }
        float g = deathExplosionComponent.getClientFuseTime(f);
        float h = 1.0F + MathHelper.sin(g * 100.0F) * g * 0.01F;
        g = MathHelper.clamp(g, 0.0F, 1.0F);
        g *= g;
        g *= g;
        float i = (1.0F + g * 0.4F) * h;
        float j = (1.0F + g * 0.1F) / h;
        matrixStack.scale(i, j, i);
    }
}
