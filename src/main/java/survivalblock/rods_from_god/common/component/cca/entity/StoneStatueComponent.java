package survivalblock.rods_from_god.common.component.cca.entity;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;

public class StoneStatueComponent implements AutoSyncedComponent, CommonTickingComponent {

    public static final Identifier STONE_BLOCK = Identifier.ofVanilla("textures/block/stone.png");
    public static final Identifier STONE_BRICKS = Identifier.ofVanilla("textures/block/stone_bricks.png");

    public static final int MAX_STATUE_TICKS = 20 * 10;

    private final LivingEntity obj;
    private int ticksInStone = 0;
    private Vec3d position;
    private EntityPose pose;
    private boolean showcase = false;

    // I literally cannot be bothered to make setter and getter methods for all of these
    public float limbAngle, limbDistance, headYaw, bodyYaw, pitch;

    private boolean shouldSendAll = true;
    private boolean alreadySynced = false;

    public StoneStatueComponent(LivingEntity living) {
        this.obj = living;
        this.position = living.getPos();
        this.pose = living.getPose();
    }

    @Override
    public void tick() {
        if (this.obj.isRemoved() || this.obj.isDead()) {
            if (this.ticksInStone != 0 || this.showcase) {
                this.ticksInStone = 0;
                this.showcase = false;
                this.sync();
            }
            return;
        }
        if (ticksInStone > 0 || showcase) {
            if (ticksInStone > MAX_STATUE_TICKS) {
                ticksInStone = MAX_STATUE_TICKS;
            }
            this.obj.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
            this.obj.getBrain().forget(MemoryModuleType.ANGRY_AT);
            if (this.obj.getVelocity().lengthSquared() > 1.0E-7 && this.obj.isLogicalSideForUpdatingMovement()) {
                this.obj.setVelocity(Vec3d.ZERO);
                if (this.obj instanceof ServerPlayerEntity) {
                    this.obj.velocityModified = true;
                }
            }
            if (this.obj.squaredDistanceTo(this.position) > 0.01) {
                this.obj.setPosition(this.position);
                this.obj.updateTrackedPosition(this.position.x, this.position.y, this.position.z);
            }
            this.obj.setPose(this.pose);
            this.obj.setHeadYaw(this.headYaw);
            this.obj.prevHeadYaw = this.headYaw;
            this.obj.setBodyYaw(this.bodyYaw);
            this.obj.prevBodyYaw = this.bodyYaw;
            this.obj.setYaw(this.bodyYaw);
            this.obj.prevYaw = this.bodyYaw;
            this.obj.setPitch(this.pitch);
            this.obj.prevPitch = this.pitch;
            if (this.obj instanceof PathAwareEntity pathAwareEntity) {
                pathAwareEntity.getNavigation().stop();
            }
            if (this.obj.getVehicle() != null) {
                this.obj.stopRiding();
            }
        } else if (ticksInStone < 0) {
            ticksInStone = 0;
        }
        this.shouldSendAll = true;
        if (this.obj.getWorld().getTime() % 40 == 0) {
            sync();
        }
        this.alreadySynced = false;
    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.ticksInStone = nbt.getInt("ticksInStone");
        if (nbt.contains("showcase")) {
            this.showcase = nbt.getBoolean("showcase");
        }
        if (nbt.contains("pose")) {
            this.pose = EntityPose.valueOf(nbt.getString("pose"));
        }
        if (nbt.contains("position")) {
            this.position = Vec3d.CODEC.parse(wrapperLookup.getOps(NbtOps.INSTANCE), nbt.get("position"))
                    .resultOrPartial(error -> RodsFromGod.LOGGER.error("Tried to load invalid Vec3d: '{}'", error))
                    .orElse(this.obj.getPos());
        }
        if (nbt.contains("headYaw")) {
            this.headYaw = nbt.getFloat("headYaw");
        }
        if (nbt.contains("bodyYaw")) {
            this.bodyYaw = nbt.getFloat("bodyYaw");
        }
        if (nbt.contains("pitch")) {
            this.pitch = nbt.getFloat("pitch");
        }
        if (nbt.contains("limbAngle")) {
            this.limbAngle = nbt.getFloat("limbAngle");
        }
        if (nbt.contains("limbDistance")) {
            this.limbDistance = nbt.getFloat("limbDistance");
        }
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.writeBasicDataToNbt(nbt, wrapperLookup);
        nbt.putString("pose", this.pose.toString());
        nbt.put("position", Vec3d.CODEC.encodeStart(wrapperLookup.getOps(NbtOps.INSTANCE), this.position)
                .getOrThrow());
        nbt.putFloat("headYaw", this.headYaw);
        nbt.putFloat("bodyYaw", this.bodyYaw);
        nbt.putFloat("pitch", this.pitch);
        nbt.putFloat("limbAngle", this.limbAngle);
        nbt.putFloat("limbDistance", this.limbDistance);
    }

    @SuppressWarnings("unused")
    private void writeBasicDataToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbt.putInt("ticksInStone", this.ticksInStone);
        nbt.putBoolean("showcase", this.showcase);
    }

    public boolean isStatue() {
        return this.ticksInStone > 0 || this.showcase;
    }

    public float getOverlayFactor() {
        return ((float) Math.min(this.ticksInStone, MAX_STATUE_TICKS) / MAX_STATUE_TICKS);
    }

    public void setInStone(boolean showcase, boolean autoSync) {
        this.setInStone(MAX_STATUE_TICKS, showcase, autoSync);
    }

    public void setInStone(int ticksInStone, boolean showcase, boolean autoSync) {
        boolean alreadyStatue = this.isStatue();
        this.ticksInStone = ticksInStone;
        this.showcase = showcase;
        if (alreadyStatue) {
            if (autoSync) {
                this.shouldSendAll = false;
                sync();
            }
            return;
        }
        if (ticksInStone > 0) {
            this.shouldSendAll = true;
            this.position = this.obj.getPos();
            this.pose = this.obj.getPose();
            this.headYaw = this.obj.getHeadYaw();
            this.bodyYaw = this.obj.getYaw();
            this.pitch = this.obj.getPitch();
            this.limbAngle = this.obj.limbAnimator.getPos();
            this.limbDistance = this.obj.limbAnimator.getSpeed();
        }
        if (autoSync) {
            sync();
        }
    }

    public boolean showcase() {
        return this.showcase;
    }

    @Override
    public void writeSyncPacket(RegistryByteBuf buf, ServerPlayerEntity recipient) {
        NbtCompound nbt = new NbtCompound();
        if (this.shouldSendAll) {
            this.writeToNbt(nbt, buf.getRegistryManager());
        } else {
            this.writeBasicDataToNbt(nbt, buf.getRegistryManager());
        }
        buf.writeNbt(nbt);
    }

    public void sync() {
        if (this.alreadySynced) {
            return;
        }
        RodsFromGodEntityComponents.STONE_STATUE.sync(this.obj);
        this.alreadySynced = true;
    }

    public static Identifier getStoneTexture(Identifier original, LivingEntity living) {
        StoneStatueComponent stoneStatueComponent = RodsFromGodEntityComponents.STONE_STATUE.get(living);
        if (!stoneStatueComponent.isStatue()) {
            return original;
        }
        if (stoneStatueComponent.showcase || (double) stoneStatueComponent.ticksInStone / MAX_STATUE_TICKS > 0.5) {
            return STONE_BLOCK;
        }
        return STONE_BRICKS;
    }

    // 1.21.4
    public static boolean isEntityLookingAtMe(LivingEntity other, LivingEntity me, double tolerance, boolean scaleByDistance, boolean visual, double... yValues) {
        Vec3d rotationVec = other.getRotationVec(1.0F).normalize();

        for (double y : yValues) {
            Vec3d vec3d = new Vec3d(me.getX() - other.getX(), y - other.getEyeY(), me.getZ() - other.getZ());
            double length = vec3d.length();
            vec3d = vec3d.normalize();
            double dotProduct = rotationVec.dotProduct(vec3d);
            if (dotProduct > 1.0 - tolerance / (scaleByDistance ? length : 1.0) && canSee(other, me, visual ? RaycastContext.ShapeType.VISUAL : RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, y)) {
                return true;
            }
        }
        return false;
    }

    public static boolean canSee(LivingEntity other, LivingEntity me, RaycastContext.ShapeType shapeType, RaycastContext.FluidHandling fluidHandling, double y) {
        if (other.getWorld() != me.getWorld()) {
            return false;
        } else {
            Vec3d vec3d = new Vec3d(me.getX(), me.getEyeY(), me.getZ());
            Vec3d vec3d2 = new Vec3d(other.getX(), y, other.getZ());
            return !(vec3d2.distanceTo(vec3d) > 128.0) && me.getWorld().raycast(new RaycastContext(vec3d, vec3d2, shapeType, fluidHandling, me)).getType() == HitResult.Type.MISS;
        }
    }
}
