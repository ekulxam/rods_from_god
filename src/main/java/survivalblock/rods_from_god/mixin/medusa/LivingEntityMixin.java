package survivalblock.rods_from_god.mixin.medusa;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.rods_from_god.common.component.cca.entity.StoneStatueComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean isAlive();

    @Shadow public abstract float getScale();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @SuppressWarnings({"RedundantCast", "DataFlowIssue"})
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tick()V"))
    private void lookIntoMyEyes(CallbackInfo ci) {
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        if (this.isInvulnerable() || !this.isAlive()) {
            return;
        }
        if ((LivingEntity) (Object) this instanceof MobEntity mob && mob.isAiDisabled()) {
            return;
        }
        for (PlayerEntity player : serverWorld.getPlayers()) {
            if (player.equals((LivingEntity) (Object) this) || player.isTeammate(this)) {
                return;
            }
            if (!StoneStatueComponent.isEntityLookingAtMe((LivingEntity) (Object) this, player, 0.02, true, false,
                    player.getEyeY())) {
                continue;
            }
            if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(RodsFromGodItems.MEDUSA_CURSE)) {
                RodsFromGodEntityComponents.STONE_STATUE.get(this).setInStone(false, true);
                return;
            }
        }
    }
}
