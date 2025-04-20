package survivalblock.rods_from_god.mixin.theonewatch.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @WrapOperation(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderBakedItemModel(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V"))
    private void eyeOfEnder(ItemRenderer instance, BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices, Operation<Void> original, @Local(argsOnly = true) VertexConsumerProvider vertexConsumerProvider) {
        original.call(instance, model, stack, light, overlay, matrices, vertices);
        if (!stack.isOf(RodsFromGodItems.THE_ONE_WATCH)) {
            return;
        }
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEndPortal());
        // 0.53125 = 1/2 + 1/32, 0.46875 = 1/2 - 1/32. Adding/subtracting 0.001 to avoid z-fighting
        grods$renderSide(matrix, vertexConsumer, 0.46875F, 0.53125F, 0.375F, 0.625F);
        grods$renderSide(matrix, vertexConsumer, 0.4375F, 0.5625F, 0.4375F, 0.5625F);
        grods$renderSide(matrix, vertexConsumer, 0.4375F, 0.5625F, 0.4375F, 0.5625F);
    }

    @Unique
    private static void grods$renderSide(Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2) {
        grods$renderSide(model, vertices, x1, x2, y1, y2, 0.53225F, 0.53225F, 0.53225F, 0.53225F);
        grods$renderSide(model, vertices, x1, x2, y2, y1, 0.46775F, 0.46775F, 0.46775F, 0.46775F);
    }

    @Unique
    private static void grods$renderSide(Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4) {
        vertices.vertex(model, x1, y1, z1);
        vertices.vertex(model, x2, y1, z2);
        vertices.vertex(model, x2, y2, z3);
        vertices.vertex(model, x1, y2, z4);
    }
}
