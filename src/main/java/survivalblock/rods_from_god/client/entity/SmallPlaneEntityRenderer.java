package survivalblock.rods_from_god.client.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.entity.RodLandingMarkerEntity;

public class SmallPlaneEntityRenderer extends EntityRenderer<RodLandingMarkerEntity>{

    protected SmallPlaneEntityModel model;

    public SmallPlaneEntityRenderer(EntityRendererFactory.Context context, SmallPlaneEntityModel model, float shadowRadius) {
        super(context);
        this.model = model;
        this.shadowRadius = shadowRadius;
    }

    @SuppressWarnings("unused")
    public SmallPlaneEntityModel getModel() {
        return this.model;
    }

    public void render(RodLandingMarkerEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw()));
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean visible = this.isVisible(entity);
        boolean translucent = !visible && !entity.isInvisibleTo(minecraftClient.player);
        boolean outline = minecraftClient.hasOutline(entity);
        RenderLayer renderLayer = this.getRenderLayer(entity, visible, translucent, outline);
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
            this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, translucent ? 654311423 : -1);
        }
        matrixStack.pop();
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(RodLandingMarkerEntity entity) {
        return RodsFromGod.id("textures/entity/rod_landing_marker.png");
    }

    @Nullable
    protected RenderLayer getRenderLayer(RodLandingMarkerEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(entity);
        if (translucent) {
            return RenderLayer.getItemEntityTranslucentCull(identifier);
        } else if (showBody) {
            return this.model.getLayer(identifier);
        } else {
            return showOutline ? RenderLayer.getOutline(identifier) : null;
        }
    }

    protected boolean isVisible(RodLandingMarkerEntity entity) {
        return !entity.isInvisible();
    }

    protected float getShadowRadius(RodLandingMarkerEntity entity) {
        return super.getShadowRadius(entity);
    }

    @Override
    public boolean shouldRender(RodLandingMarkerEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}