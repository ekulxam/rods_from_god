package survivalblock.rods_from_god.client.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class OctagonalPrismEntityModel extends EntityModel<Entity> {

	private final ModelPart octagonalPrism;

	public OctagonalPrismEntityModel(ModelPart root) {
		this.octagonalPrism = root.getChild("octagon");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData octagon = modelPartData.addChild("octagon", ModelPartBuilder.create().uv(0, 0).cuboid(-0.2589F, -24F, -0.625F, 0.5178F, 24.5F, 1.25F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-0.625F, -24F, -0.2589F, 1.25F, 24.5F, 0.5178F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        octagon.addChild("octagon_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-0.625F, -24F, -0.2589F, 1.25F, 24.5F, 0.5178F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.2589F, -24F, -0.625F, 0.5178F, 24.5F, 1.25F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
	}


	@Override
	public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		octagonalPrism.render(matrices, vertices, light, overlay);
	}
}