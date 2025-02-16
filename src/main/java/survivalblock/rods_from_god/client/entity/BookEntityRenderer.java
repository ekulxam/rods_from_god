package survivalblock.rods_from_god.client.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.EnchantingTableBlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import survivalblock.rods_from_god.common.entity.BookEntity;

import static net.minecraft.client.render.block.entity.EnchantingTableBlockEntityRenderer.BOOK_TEXTURE;

public class BookEntityRenderer<T extends BookEntity> extends EntityRenderer<T> {

    public static final Identifier TEXTURE = BOOK_TEXTURE.getTextureId();

    private final BookModel book;

    public BookEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.book = new BookModel(ctx.getModelLoader().getModelPart(EntityModelLayers.BOOK));
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        matrixStack.push();
        matrixStack.translate(0.0F, 0.2F + (entity.getHeight() * 0.25), 0.0F);
        float g = (float)entity.ticks + tickDelta;
        matrixStack.translate(0.0F, 0.1F + MathHelper.sin(g * 0.1F) * 0.01F, 0.0F);
        float h = entity.bookRotation - entity.lastBookRotation;

        while (h >= (float) Math.PI) {
            h -= (float) (Math.PI * 2);
        }

        while (h < (float) -Math.PI) {
            h += (float) (Math.PI * 2);
        }

        float k = entity.lastBookRotation + h * tickDelta;
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation(-k));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getPitch()));
        float l = MathHelper.lerp(tickDelta, entity.pageAngle, entity.nextPageAngle);
        float m = MathHelper.fractionalPart(l + 0.25F) * 1.6F - 0.3F;
        float n = MathHelper.fractionalPart(l + 0.75F) * 1.6F - 0.3F;
        float o = MathHelper.lerp(tickDelta, entity.pageTurningSpeed, entity.nextPageTurningSpeed);
        this.book.setPageAngles(g, MathHelper.clamp(m, 0.0F, 1.0F), MathHelper.clamp(n, 0.0F, 1.0F), o);
        VertexConsumer vertexConsumer = BOOK_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid);
        float scale = entity.getScale();
        matrixStack.scale(scale, scale, scale);
        this.book.renderBook(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, -1);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
