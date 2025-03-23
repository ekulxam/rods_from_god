package survivalblock.rods_from_god.mixin.medusa.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.component.cca.entity.StoneStatueComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @ModifyExpressionValue(method = "getRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getTexture(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/Identifier;"))
    private Identifier useStoneTexture(Identifier original, LivingEntity living, boolean showBody, boolean translucent, boolean showOutline) {
        return StoneStatueComponent.getStoneTexture(original, living);
    }

    @ModifyExpressionValue(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LimbAnimator;getPos(F)F"))
    private float useStatueLimbAngle(float original, @Local(argsOnly = true) LivingEntity living) {
        StoneStatueComponent stoneStatueComponent = RodsFromGodEntityComponents.STONE_STATUE.get(living);
        return stoneStatueComponent.isStatue() ? stoneStatueComponent.limbAngle : original;
    }

    @ModifyExpressionValue(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LimbAnimator;getSpeed(F)F"))
    private float useStatueLimbDistance(float original, @Local(argsOnly = true) LivingEntity living) {
        StoneStatueComponent stoneStatueComponent = RodsFromGodEntityComponents.STONE_STATUE.get(living);
        return stoneStatueComponent.isStatue() ? stoneStatueComponent.limbDistance : original;
    }
}
