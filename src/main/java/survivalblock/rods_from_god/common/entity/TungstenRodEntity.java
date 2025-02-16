package survivalblock.rods_from_god.common.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import survivalblock.atmosphere.atmospheric_api.not_mixin.entity.EntityWithAttributesImpl;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.init.*;

@SuppressWarnings("unused")
public class TungstenRodEntity extends EntityWithAttributesImpl {

    public static final String EXPLOSION_COUNTER_NBT_KEY = "explosionCounter";
    public static final String EXPLOSION_BOOLEAN_NBT_KEY = "shouldExplode";
    public static final String EXPLOSION_POS_NBT_KEY = "explosionPos";
    public static final String MAXIMUM_EXPLOSIONS_NBT_KEY = "maxExplosions";
    public static final String EXPLOSION_POWER_NBT_KEY = "explosionPower";
    public static final String INVERSE_EXPLOSION_DAMAGE_FACTOR_NBT_KEY = "inverseExplosionDamageFactor";
    public static final String FIRE_NBT_KEY = "createsFire";
    public static final int DEFAULT_MAX_EXPLOSIONS = 5;
    public static final int DEFAULT_EXPLOSION_POWER = 10;
    public static final int DEFAULT_INVERSE_EXPLOSION_DAMAGE_FACTOR = 100;
    public static final boolean DEFAULT_FIRE_BOOLEAN_VALUE = false;
    public static final float DEFAULT_SCALE = 16.0f;
    public static final double DEFAULT_GRAVITY = 0.1d;

    protected float prevScale = 0;
    protected boolean shouldExplode = false;
    protected int explosionCounter = 0;
    protected Vec3d explosionPos = Vec3d.ZERO;
    protected int maxExplosions = DEFAULT_MAX_EXPLOSIONS;
    protected float explosionPower = DEFAULT_EXPLOSION_POWER;
    protected float inverseExplosionDamageFactor = DEFAULT_INVERSE_EXPLOSION_DAMAGE_FACTOR;
    protected boolean createsFire = false;

    public TungstenRodEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public TungstenRodEntity(World world, Vec3d position) {
        this(RodsFromGodEntityTypes.TUNGSTEN_ROD, world);
        this.setPosition(position);
    }

    public void setMaxExplosions(int maxExplosions) {
        this.maxExplosions = maxExplosions;
    }

    public void setExplosionPower(float explosionPower) {
        this.explosionPower = explosionPower;
    }

    public void setInverseExplosionDamageFactor(float inverseExplosionDamageFactor) {
        this.inverseExplosionDamageFactor = inverseExplosionDamageFactor;
    }

    public void setCreatesFire(boolean createsFire) {
        this.createsFire = createsFire;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains(EXPLOSION_BOOLEAN_NBT_KEY)) {
            this.shouldExplode = nbt.getBoolean(EXPLOSION_BOOLEAN_NBT_KEY);
        }
        if (nbt.contains(EXPLOSION_COUNTER_NBT_KEY)) {
            this.explosionCounter = nbt.getInt(EXPLOSION_COUNTER_NBT_KEY);
        }
        if (nbt.contains(MAXIMUM_EXPLOSIONS_NBT_KEY)) {
            this.maxExplosions = nbt.getInt(MAXIMUM_EXPLOSIONS_NBT_KEY);
        }
        if (nbt.contains(EXPLOSION_POWER_NBT_KEY)) {
            this.explosionPower = nbt.getFloat(EXPLOSION_POWER_NBT_KEY);
        }
        if (nbt.contains(INVERSE_EXPLOSION_DAMAGE_FACTOR_NBT_KEY)) {
            this.inverseExplosionDamageFactor = nbt.getFloat(INVERSE_EXPLOSION_DAMAGE_FACTOR_NBT_KEY);
        }
        if (nbt.contains(FIRE_NBT_KEY)) {
            this.createsFire = nbt.getBoolean(FIRE_NBT_KEY);
        }
        try {
            if (nbt.contains(EXPLOSION_POS_NBT_KEY)) {
                NbtList nbtList = nbt.getList(EXPLOSION_POS_NBT_KEY, NbtElement.DOUBLE_TYPE);
                this.explosionPos = new Vec3d(
                        MathHelper.clamp(nbtList.getDouble(0), -3.0000512E7, 3.0000512E7),
                        MathHelper.clamp(nbtList.getDouble(1), -2.0E7, 2.0E7),
                        MathHelper.clamp(nbtList.getDouble(2), -3.0000512E7, 3.0000512E7)
                );
            }
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Loading entity NBT");
            CrashReportSection crashReportSection = crashReport.addElement("Entity being loaded");
            this.populateCrashReport(crashReportSection);
            RodsFromGod.LOGGER.error("Error while loading Tungsten Rod Explosion Position", new CrashException(crashReport));
            this.updateExplosionPos();
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean(EXPLOSION_BOOLEAN_NBT_KEY, this.shouldExplode);
        nbt.putInt(EXPLOSION_COUNTER_NBT_KEY, this.explosionCounter);
        nbt.putInt(MAXIMUM_EXPLOSIONS_NBT_KEY, this.maxExplosions);
        nbt.putFloat(EXPLOSION_POWER_NBT_KEY, this.explosionPower);
        nbt.putFloat(INVERSE_EXPLOSION_DAMAGE_FACTOR_NBT_KEY, this.inverseExplosionDamageFactor);
        nbt.putBoolean(FIRE_NBT_KEY, this.createsFire);
        nbt.put(EXPLOSION_POS_NBT_KEY, this.toNbtList(this.explosionPos.getX(), this.explosionPos.getY(), this.explosionPos.getZ()));
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
        float l = this.getScale();
        if (l != this.prevScale) {
            this.prevScale = l;
            this.calculateDimensions();
        }
        World world = this.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            if (this.hasPassengers()) {
                this.removeAllPassengers();
            }
            if (this.groundCollision || this.isOnGround()) {
                /*
                List<Entity> affectedEntities = world.getOtherEntities(this, this.getBoundingBox().expand(this.knockbackRadius * 2));
                affectedEntities.forEach((entity) -> {
                    Vec3d entityPosition = entity.getPos();
                    Vec3d pos = this.getPos();
                    double maxDistance = Math.abs(entityPosition.squaredDistanceTo(pos));
                    if (maxDistance >= this.knockbackRadius) {
                        return;
                    }
                    Vec3d yeet = pos.subtract(entityPosition).normalize().multiply(-1);
                    yeet = new Vec3d(yeet.x, yeet.y >= 0 ? yeet.y + yStrength : yeet.y - yStrength, yeet.z);
                    yeet.multiply(5 * ((this.knockbackRadius - maxDistance) / 50));
                    entity.setVelocity(yeet);
                });
                */
                this.shouldExplode = true;
                this.updateExplosionPos();
                RodsFromGodEntityComponents.TUNGSTEN_ROD_LANDED.get(this).setLanded(true);
            }
            if (this.shouldExplode) {
                this.explode(serverWorld);
            }
        }
    }

    public float getScale() {
        AttributeContainer attributeContainer = this.getAttributes();
        return attributeContainer == null ? DEFAULT_SCALE : (float) attributeContainer.getValue(EntityAttributes.GENERIC_SCALE);
    }

    protected void updateExplosionPos() {
        this.explosionPos = new Vec3d(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
    }

    @Override
    protected double getGravity() {
        AttributeContainer attributeContainer = this.getAttributes();
        return attributeContainer == null ? DEFAULT_GRAVITY : attributeContainer.getValue(EntityAttributes.GENERIC_GRAVITY);
    }

    protected void explode(ServerWorld serverWorld) {
        if (!shouldExplode) {
            return;
        }
        MinecraftServer server = serverWorld.getServer();
        int ticks = server.getTicks();
        GameRules gameRules = serverWorld.getGameRules();
        World.ExplosionSourceType sourceType = gameRules.get(RodsFromGodGameRules.KINETIC_EXPLOSION_SOURCE_TYPE).get();
        DamageSource source = new DamageSource(serverWorld.atmospheric_api$getEntryFromKey(RegistryKeys.DAMAGE_TYPE, RodsFromGodDamageTypes.KINETIC_EXPLOSION), this);
        final ExplosionBehavior lessDamageExplosionBehavior = new ExplosionBehavior() {
            @Override
            public float calculateDamage(Explosion explosion, Entity entity) {
                return super.calculateDamage(explosion, entity) / Math.max(1, TungstenRodEntity.this.inverseExplosionDamageFactor); // thanks, Patbox
            }

            @Override
            public float getKnockbackModifier(Entity entity) {
                return entity instanceof TungstenRodEntity || entity instanceof RodLandingMarkerEntity ? 0 : super.getKnockbackModifier(entity);
            }
        };
        if (this.explosionCounter == 0) {
            serverWorld.createExplosion(this, source, lessDamageExplosionBehavior, this.explosionPos.getX(), this.explosionPos.getY(), this.explosionPos.getZ(), this.explosionPower,
                    this.createsFire && gameRules.getBoolean(RodsFromGodGameRules.KINETIC_EXPLOSION_CAN_MAKE_FIRE),
                    sourceType, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, RodsFromGodSoundEvents.TUNGSTEN_ROD_KINETIC_EXPLOSION);
        } else {
            serverWorld.createExplosion(this, source, lessDamageExplosionBehavior, this.explosionPos.getX(), this.explosionPos.getY(), this.explosionPos.getZ(), this.explosionPower,
                    false,
                    sourceType, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, RodsFromGodSoundEvents.TUNGSTEN_ROD_KINETIC_EXPLOSION);
        }
        this.explosionCounter++;
        if (this.explosionCounter >= this.maxExplosions) {
            this.discard();
        }
        if (this.explosionCounter > 1) {
            this.setInvisible(true);
        }
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    @Override
    public boolean isImmuneToExplosion(Explosion explosion) {
        return true;
    }

    @Override
    public final EntityDimensions getDimensions(EntityPose pose) {
        return this.getBaseDimensions(pose).scaled(this.getScale());
    }

    protected EntityDimensions getBaseDimensions(EntityPose pose) {
        return this.getType().getDimensions();
    }

    public AttributeContainer getDefaultAttributeContainer() {
        return new AttributeContainer(DefaultAttributeContainer.builder()
                .add(EntityAttributes.GENERIC_SCALE, DEFAULT_SCALE)
                .add(EntityAttributes.GENERIC_GRAVITY, DEFAULT_GRAVITY).build());
    }
}