package survivalblock.rods_from_god.mixin.book.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.client.entity.EnchantedArrowEntityRenderer;
import survivalblock.rods_from_god.common.entity.EnchantedArrowEntity;

@Mixin(ProjectileEntityRenderer.class)
public class ProjectileEntityRendererMixin {

    @WrapOperation(method = "render(Lnet/minecraft/entity/projectile/PersistentProjectileEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer enchant(VertexConsumerProvider instance, RenderLayer renderLayer, Operation<VertexConsumer> original, @Local(argsOnly = true)MatrixStack matrixStack) {
        VertexConsumer vertexConsumer = original.call(instance, renderLayer);
        if ((ProjectileEntityRenderer<?>) (Object) this instanceof EnchantedArrowEntityRenderer<?>) {
            vertexConsumer = ItemRenderer.getDynamicDisplayGlintConsumer(instance, renderLayer, matrixStack.peek().copy());
        }
        return vertexConsumer;
    }

    @Inject(method = "render(Lnet/minecraft/entity/projectile/PersistentProjectileEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "FIELD", target = "Lnet/minecraft/util/math/RotationAxis;POSITIVE_X:Lnet/minecraft/util/math/RotationAxis;", ordinal = 0))
    private void spin(PersistentProjectileEntity projectile, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (projectile instanceof EnchantedArrowEntity) {
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(projectile.age * 60));
        }
    }
}
