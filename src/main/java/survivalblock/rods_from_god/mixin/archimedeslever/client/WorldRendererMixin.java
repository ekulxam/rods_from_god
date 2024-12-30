package survivalblock.rods_from_god.mixin.archimedeslever.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import survivalblock.rods_from_god.common.component.cca.world.WorldLeverComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodWorldComponents;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow @Nullable private ClientWorld world;

    @WrapOperation(method = "renderSky", slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;SUN:Lnet/minecraft/util/Identifier;")), at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BufferBuilder;vertex(Lorg/joml/Matrix4f;FFF)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer andIShallMoveTheWorld(BufferBuilder instance, Matrix4f matrix4f, float x, float y, float z, Operation<VertexConsumer> original) {
        if (this.world == null) {
            return original.call(instance, matrix4f, x, y, z);
        }
        WorldLeverComponent worldLeverComponent = RodsFromGodWorldComponents.WORLD_LEVER.get(this.world);
        float scale = WorldLeverComponent.MAX_CELESTIAL_SCALE; // for readability
        if (worldLeverComponent.isSwitching()) {
            int zoom = WorldLeverComponent.MAX_CELESTIAL_ZOOM;
            float delta = (float) (zoom - worldLeverComponent.getCelestialZoom()) / zoom;
            if (worldLeverComponent.lifted()) {
                return original.call(instance, matrix4f, MathHelper.lerp(delta, x, x * scale), y, MathHelper.lerp(delta, z, z * scale));
            }
            return original.call(instance, matrix4f, MathHelper.lerp(delta, x * scale, x), y, MathHelper.lerp(delta, z * scale, z));
        } else {
            if (worldLeverComponent.lifted()) {
                return original.call(instance, matrix4f, x * scale, y, z * scale);
            }
            return original.call(instance, matrix4f, x, y, z);
        }
    }
}
