package survivalblock.rods_from_god.client.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class SmallPlaneEntityModel extends EntityModel<Entity> {

    private final ModelPart surface;

    public SmallPlaneEntityModel(ModelPart root) {
        this.surface = root.getChild("surface");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("surface", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -24.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        surface.render(matrices, vertices, light, overlay);
    }
}
