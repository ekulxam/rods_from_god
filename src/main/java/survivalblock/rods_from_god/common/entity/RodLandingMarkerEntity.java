package survivalblock.rods_from_god.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityTypes;

import java.util.UUID;

public class RodLandingMarkerEntity extends Entity {

    protected UUID tungstenRodUUID = null;

    public RodLandingMarkerEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public RodLandingMarkerEntity(World world, Vec3d position, UUID tungstenRodUUID) {
        this(RodsFromGodEntityTypes.ROD_LANDING_MARKER, world);
        this.setPosition(position);
        this.tungstenRodUUID = tungstenRodUUID;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.tungstenRodUUID = nbt.getUuid("tungstenRodUUID");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putUuid("tungstenRodUUID", this.tungstenRodUUID);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isLogicalSideForUpdatingMovement()) {
            this.velocityDirty = true;
            this.applyGravity();
            this.move(MovementType.SELF, this.getVelocity());
            this.velocityModified = true;
        }
        World world = this.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            if (this.hasPassengers()) {
                this.removeAllPassengers();
            }
            if (tungstenRodUUID == null) {
                this.discard();
            } else {
                Entity entity = serverWorld.getEntity(tungstenRodUUID);
                if (entity == null || entity.isRemoved() || (entity instanceof TungstenRodEntity tungstenRodEntity && RodsFromGodEntityComponents.TUNGSTEN_ROD_LANDED.get(tungstenRodEntity).isLanded())) {
                    this.discard();
                }
            }
        } else {
            Random random = world.getRandom();
            world.addParticle(ParticleTypes.CRIT, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), MathHelper.nextDouble(random, -1, 1), MathHelper.nextDouble(random, 0.2, 4), MathHelper.nextDouble(random, -1, 1));
        }
    }

    @Override
    protected double getGravity() {
        return 0.08;
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    @Override
    public boolean isImmuneToExplosion(Explosion explosion) {
        return true;
    }
}
