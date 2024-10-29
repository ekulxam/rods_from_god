package survivalblock.rods_from_god.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;

@Environment(value= EnvType.CLIENT)
public class OctagonalPrismEntityRenderer extends EntityRenderer<TungstenRodEntity> {

    protected OctagonalPrismEntityModel model;

    public OctagonalPrismEntityRenderer(EntityRendererFactory.Context context, OctagonalPrismEntityModel model, float shadowRadius) {
        super(context);
        this.model = model;
        this.shadowRadius = shadowRadius;
    }

    @SuppressWarnings("unused")
    public OctagonalPrismEntityModel getModel() {
        return this.model;
    }

    public void render(TungstenRodEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        float scale = entity.getScale();
        matrixStack.scale(scale, scale, scale);
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean bl = this.isVisible(entity);
        boolean bl2 = !bl && !entity.isInvisibleTo(minecraftClient.player);
        boolean bl3 = minecraftClient.hasOutline(entity);
        RenderLayer renderLayer = this.getRenderLayer(entity, bl, bl2, bl3);
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
            this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, bl2 ? 654311423 : -1);
        }
        matrixStack.pop();
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(TungstenRodEntity entity) {
        return RodsFromGod.id("textures/entity/tungsten_rod.png");
    }

    @Nullable
    protected RenderLayer getRenderLayer(TungstenRodEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(entity);
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }

    protected boolean isVisible(TungstenRodEntity entity) {
        return !entity.isInvisible();
    }

    protected float getShadowRadius(TungstenRodEntity entity) {
        return super.getShadowRadius(entity) * entity.getScale();
    }

    @Override
    public boolean shouldRender(TungstenRodEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}
