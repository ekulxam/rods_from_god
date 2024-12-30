package survivalblock.rods_from_god.mixin.archimedeslever;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeverBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.block.ArchimedesLeverBlock;
import survivalblock.rods_from_god.common.init.RodsFromGodBlocks;

@Debug(export = true)
@Mixin(LeverBlock.class)
public class LeverBlockMixin {

    @WrapOperation(method = {"onUse", "randomDisplayTick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LeverBlock;spawnParticles(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;F)V"))
    private void spawnDifferentParticlesForArchimedesLever(BlockState state, WorldAccess world, BlockPos pos, float alpha, Operation<Void> original) {
        if ((LeverBlock) (Object) this instanceof ArchimedesLeverBlock archimedesLeverBlock) {
            archimedesLeverBlock.spawnCustomParticles(state, world, pos, alpha);
            return;
        }
        original.call(state, world, pos, alpha);
    }
}
