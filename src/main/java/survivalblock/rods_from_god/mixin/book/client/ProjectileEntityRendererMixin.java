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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.client.entity.EnchantedArrowEntityRenderer;

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
}
