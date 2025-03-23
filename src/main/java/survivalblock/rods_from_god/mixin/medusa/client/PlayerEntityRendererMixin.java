package survivalblock.rods_from_god.mixin.medusa.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.component.cca.entity.StoneStatueComponent;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin extends LivingEntityRendererMixin {

    @ModifyExpressionValue(method = "renderArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SkinTextures;texture()Lnet/minecraft/util/Identifier;"))
    private Identifier useStoneForSkinTexture(Identifier original, @Local(argsOnly = true) AbstractClientPlayerEntity player) {
        return StoneStatueComponent.getStoneTexture(original, player);
    }
}
