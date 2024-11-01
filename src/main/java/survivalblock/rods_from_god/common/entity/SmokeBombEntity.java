package survivalblock.rods_from_god.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

import java.util.List;
import static survivalblock.rods_from_god.common.component.SmokeScreenComponent.MAX_SMOKE_SCREEN_TICKS;

@SuppressWarnings("unused")
public class SmokeBombEntity extends ThrownItemEntity {
    public SmokeBombEntity(EntityType<? extends SmokeBombEntity> entityType, World world) {
        super(entityType, world);
    }

    public SmokeBombEntity(World world, LivingEntity owner) {
        super(RodsFromGodEntityTypes.SMOKE_BOMB, owner, world);
    }

    public SmokeBombEntity(World world, double x, double y, double z) {
        super(RodsFromGodEntityTypes.SMOKE_BOMB, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return RodsFromGodItems.SMOKE_BOMB;
    }

    @Override
    protected double getGravity() {
        return 0.07;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            Random random = serverWorld.getRandom();
            this.spawnParticles(serverWorld, hitResult.getPos().getX(), hitResult.getPos().getY(), hitResult.getPos().getZ(), random.nextBetween(60, 100), 2f, 1f, 2f);
            List<PlayerEntity> players = serverWorld.getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(10, 5, 10), Entity::isAlive);
            players.forEach(player -> RodsFromGodEntityComponents.SMOKE_SCREEN.get(player).setSmokeScreenTicks((int) (MAX_SMOKE_SCREEN_TICKS * 0.98)));
            serverWorld.createExplosion(this, serverWorld.getDamageSources().explosion(this, this.getOwner() instanceof PlayerEntity player ? player : this), new ExplosionBehavior(), this.getPos(), 0.2f, false, World.ExplosionSourceType.NONE);
            this.discard();
        }
    }

    public <T extends ParticleEffect> void spawnParticles(ServerWorld serverWorld, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ) {
        ParticleS2CPacket particleS2CPacket = new ParticleS2CPacket(ParticleTypes.CAMPFIRE_COSY_SMOKE, false, x, y, z, (float)deltaX, (float)deltaY, (float)deltaZ, 0.03f, count);
        for (int i = 0; i < serverWorld.getPlayers().size(); i++) {
            ServerPlayerEntity serverPlayerEntity = serverWorld.getPlayers().get(i);
            serverWorld.sendToPlayerIfNearby(serverPlayerEntity, true, x, y, z, particleS2CPacket);
        }
    }
}
