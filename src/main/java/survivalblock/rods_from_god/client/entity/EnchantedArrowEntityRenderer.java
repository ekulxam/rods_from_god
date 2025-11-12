package survivalblock.rods_from_god.client.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.entity.EnchantedArrowEntity;

public class EnchantedArrowEntityRenderer<T extends EnchantedArrowEntity> extends ProjectileEntityRenderer<T> {

    public static final Identifier TEXTURE = RodsFromGod.id("textures/entity/enchanted_arrow.png");

    public EnchantedArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }

    @Override
    public void render(T arrow, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        float scale = arrow.getScale();
        matrixStack.scale(scale, scale, scale);
        super.render(arrow, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    protected float getShadowRadius(T entity) {
        return super.getShadowRadius(entity) * entity.getScale();
    }

    @Override
    public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}
