package survivalblock.rods_from_god.mixin.medusa;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@Mixin(value = ServerPlayerEntity.class, priority = 2000)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
    private void midasTouchCancelServerPlayerTick(CallbackInfo ci){
        if (RodsFromGodEntityComponents.STONE_STATUE.get(this).isStatue()) {
            ci.cancel();
        }
    }
}
