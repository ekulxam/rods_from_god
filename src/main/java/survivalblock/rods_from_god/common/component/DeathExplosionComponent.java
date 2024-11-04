package survivalblock.rods_from_god.common.component;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;
import survivalblock.rods_from_god.mixin.corruptedstarfragment.DamageSourceAccessor;
import survivalblock.rods_from_god.mixin.corruptedstarfragment.EntityAccessor;

public class DeathExplosionComponent implements AutoSyncedComponent, CommonTickingComponent {

    private final PlayerEntity obj;
    private boolean shouldExplodeOnDeath = false;
    private DamageSource finalDeathSource = null;
    private final int fuseTime = 50;
    private int lastFuseTime;
    private int currentFuseTime;
    private Vec3d deathPos = Vec3d.ZERO;

    public DeathExplosionComponent(PlayerEntity player) {
        this.obj = player;
    }

    @Override
    public void tick() {
        if (!this.shouldExplodeOnDeath) {
            return;
        }
        this.obj.setVelocity(Vec3d.ZERO);
        this.obj.setPosition(this.deathPos);
        this.obj.updateTrackedPosition(this.deathPos.x, this.deathPos.y, this.deathPos.z);
        this.lastFuseTime = this.currentFuseTime;

        this.currentFuseTime ++;
        if (this.currentFuseTime < 0) {
            this.currentFuseTime = 0;
        }

        if (this.currentFuseTime >= this.fuseTime) {
            this.currentFuseTime = this.fuseTime;
            this.explode();
        }
    }

    protected void updateDeathPos() {
        this.deathPos = new Vec3d(this.obj.getPos().getX(), this.obj.getPos().getY(), this.obj.getPos().getZ());
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.shouldExplodeOnDeath = nbtCompound.getBoolean("shouldExplodeOnDeath");
        if (nbtCompound.contains("lastFuseTime")) this.lastFuseTime = nbtCompound.getInt("lastFuseTime");
        if (nbtCompound.contains("currentFuseTime")) this.currentFuseTime = nbtCompound.getInt("currentFuseTime");
        if (nbtCompound.contains("damageSourceTypeID")) {
            if (this.obj.getWorld() instanceof ServerWorld serverWorld) {
                DamageType damageType = serverWorld.getDamageSources().registry.get(Identifier.of(nbtCompound.getString("damageSourceTypeID")));
                RegistryEntry<DamageType> damageTypeRegistryEntry = serverWorld.getDamageSources().registry.getEntry(damageType);
                Entity attacker = null;
                Entity source = null;
                Vec3d storedPosition = null;
                if (nbtCompound.contains("damageSourceAttacker")) {
                    Entity entity = serverWorld.getEntity(nbtCompound.getUuid("damageSourceAttacker"));
                    if (entity != null) attacker = entity;
                }
                if (nbtCompound.contains("damageSourceSource")) {
                    Entity entity = serverWorld.getEntity(nbtCompound.getUuid("damageSourceSource"));
                    if (entity != null) source = entity;
                }
                if (nbtCompound.contains("damageSourceStoredPosition")) {
                    try {
                        if (nbtCompound.contains("damageSourceStoredPosition")) {
                            NbtList nbtList = nbtCompound.getList("damageSourceStoredPosition", NbtElement.DOUBLE_TYPE);
                            storedPosition = new Vec3d(
                                    MathHelper.clamp(nbtList.getDouble(0), -3.0000512E7, 3.0000512E7),
                                    MathHelper.clamp(nbtList.getDouble(1), -2.0E7, 2.0E7),
                                    MathHelper.clamp(nbtList.getDouble(2), -3.0000512E7, 3.0000512E7)
                            );
                        }
                    } catch (Throwable throwable) {
                        CrashReport crashReport = CrashReport.create(throwable, "Loading entity NBT");
                        CrashReportSection crashReportSection = crashReport.addElement("Entity being loaded");
                        this.obj.populateCrashReport(crashReportSection);
                        RodsFromGod.LOGGER.error("Error while loading Death Explosion Component Damage Source Position", new CrashException(crashReport));
                    }
                }
                this.finalDeathSource = DamageSourceAccessor.rods_from_god$invokeConstructor(damageTypeRegistryEntry, source, attacker, storedPosition);
            }
        }
        try {
            if (nbtCompound.contains("deathPos")) {
                NbtList nbtList = nbtCompound.getList("deathPos", NbtElement.DOUBLE_TYPE);
                this.deathPos = new Vec3d(
                        MathHelper.clamp(nbtList.getDouble(0), -3.0000512E7, 3.0000512E7),
                        MathHelper.clamp(nbtList.getDouble(1), -2.0E7, 2.0E7),
                        MathHelper.clamp(nbtList.getDouble(2), -3.0000512E7, 3.0000512E7)
                );
            }
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Loading entity NBT");
            CrashReportSection crashReportSection = crashReport.addElement("Entity being loaded");
            this.obj.populateCrashReport(crashReportSection);
            RodsFromGod.LOGGER.error("Error while loading Death Explosion Component Position", new CrashException(crashReport));
            this.updateDeathPos();
        }
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("shouldExplodeOnDeath", this.shouldExplodeOnDeath);
        nbtCompound.putInt("lastFuseTime", this.lastFuseTime);
        nbtCompound.putInt("currentFuseTime", this.currentFuseTime);
        if (this.finalDeathSource != null) {
            Identifier id = this.obj.getWorld().getDamageSources().registry.getId(finalDeathSource.getType());
            if (id != null) {
                nbtCompound.putString("damageSourceTypeID", id.toString());
                Entity attacker = this.finalDeathSource.getAttacker();
                if (attacker != null) nbtCompound.putUuid("damageSourceAttacker", attacker.getUuid());
                Entity source = this.finalDeathSource.getSource();
                if (source != null) nbtCompound.putUuid("damageSourceSource", source.getUuid());
                Vec3d vec3d = this.finalDeathSource.getStoredPosition();
                if (vec3d != null) nbtCompound.put("damageSourceStoredPosition", ((EntityAccessor) this.obj).rods_from_god$invokeToNbtList(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
            }
        }
        nbtCompound.put("deathPos", ((EntityAccessor) this.obj).rods_from_god$invokeToNbtList(this.deathPos.getX(), this.deathPos.getY(), this.deathPos.getZ()));
    }

    public boolean shouldExplodeOnDeath() {
        return this.shouldExplodeOnDeath;
    }

    public void setShouldExplodeOnDeath(boolean shouldExplodeOnDeath) {
        this.shouldExplodeOnDeath = shouldExplodeOnDeath;
        RodsFromGodEntityComponents.DEATH_EXPLOSION.sync(this.obj);
    }

    public void setup(boolean shouldExplodeOnDeath, DamageSource finalDeathSource) {
        this.finalDeathSource = finalDeathSource;
        this.updateDeathPos();
        this.setShouldExplodeOnDeath(shouldExplodeOnDeath);
    }

    public void explode() {
        if (!(this.obj.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        this.setShouldExplodeOnDeath(false);
        serverWorld.createExplosion(this.obj, this.obj.getX(), this.obj.getY(), this.obj.getZ(), 4f, World.ExplosionSourceType.MOB);
        this.obj.setHealth(1.0F);
        MinecraftServer server = serverWorld.getServer();
        server.send(new ServerTask(server.getTicks(), () -> {
            this.obj.setHealth(1.0F);
            DamageSource source;
            if (this.finalDeathSource == null) {
                source = serverWorld.getDamageSources().explosion(this.obj, this.obj);
            } else {
                source = this.finalDeathSource;
            }
            this.obj.damage(source, Float.MAX_VALUE);
        }));
    }

    public float getClientFuseTime(float timeDelta) {
        return MathHelper.lerp(timeDelta, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
    }
}
