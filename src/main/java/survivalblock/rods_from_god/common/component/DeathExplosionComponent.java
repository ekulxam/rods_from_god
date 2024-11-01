package survivalblock.rods_from_god.common.component;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.world.ServerWorld;
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
            RodsFromGod.LOGGER.error("Error while loading Tungsten Rod Explosion Position", new CrashException(crashReport));
            this.updateDeathPos();
        }
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("shouldExplodeOnDeath", this.shouldExplodeOnDeath);
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
                source = serverWorld.getDamageSources().playerAttack(this.obj);
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
