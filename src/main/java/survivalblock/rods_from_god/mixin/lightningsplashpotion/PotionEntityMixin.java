package survivalblock.rods_from_god.mixin.lightningsplashpotion;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin extends ThrownItemEntity {

    public PotionEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;syncWorldEvent(ILnet/minecraft/util/math/BlockPos;I)V", shift = At.Shift.BEFORE))
    private void summonLightning(HitResult hitResult, CallbackInfo ci) {
        ItemStack stack = this.getStack();
        World world = this.getWorld();
        if (!world.isClient() && stack != null && !stack.isEmpty() && stack.isOf(RodsFromGodItems.LIGHTNING_SPLASH_POTION)) {
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
            if (lightningEntity != null) {
                Vec3d vec3d = hitResult.getPos();
                double x = Math.floor(vec3d.x) + 0.5;
                double y = Math.floor(vec3d.y);
                double z = Math.floor(vec3d.z) + 0.5;
                lightningEntity.refreshPositionAfterTeleport(new Vec3d(x, y, z));
                lightningEntity.setCosmetic(false);
                if (this.getOwner() instanceof ServerPlayerEntity serverPlayer) {
                    lightningEntity.setChanneler(serverPlayer);
                }
                world.spawnEntity(lightningEntity);
            }
        }
    }
}
