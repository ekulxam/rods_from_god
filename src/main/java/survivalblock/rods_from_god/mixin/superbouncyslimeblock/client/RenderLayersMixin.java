package survivalblock.rods_from_god.mixin.superbouncyslimeblock.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.init.RodsFromGodBlocks;

import java.util.HashMap;

@Mixin(RenderLayers.class)
public class RenderLayersMixin {

    @Inject(method = "method_23685", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", remap = false, ordinal = 305)) // 305 is right after honey block
    private static void addSuperBoingySlimeBlockRenderLayer(HashMap<Block, RenderLayer> map, CallbackInfo ci, @Local(ordinal = 3) RenderLayer renderLayer4) {
        map.put(RodsFromGodBlocks.SUPER_BOUNCY_SLIME_BLOCK, renderLayer4);
    }
}
