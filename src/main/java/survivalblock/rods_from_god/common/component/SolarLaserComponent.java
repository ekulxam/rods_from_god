package survivalblock.rods_from_god.common.component;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.function.LazyIterationConsumer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import survivalblock.rods_from_god.common.RodsFromGodUtil;
import survivalblock.rods_from_god.common.init.*;
import survivalblock.rods_from_god.mixin.solarprismheadset.ServerWorldAccessor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class SolarLaserComponent implements CommonTickingComponent, AutoSyncedComponent {

    public static final int MAX_OVERHEATING_TICKS = 600;
    private final PlayerEntity obj;
    private int overheatTicks = 0;
    private int ticksUsed = 0;

    public SolarLaserComponent(PlayerEntity player) {
        this.obj = player;
    }

    @Override
    public void tick() {
        World world = this.obj.getWorld();
        ItemStack stack = this.obj.getEquippedStack(EquipmentSlot.HEAD);
        if (stack.isOf(RodsFromGodItems.SOLAR_PRISM_HEADSET) && RodsFromGodUtil.isAffectedByDaylight(this.obj)) {
            if (stack.getOrDefault(RodsFromGodDataComponentTypes.SOLAR_PRISM_HEADSET_OVERHEAT, true)) {
                overheatTicks++;
            } else {
                overheatTicks--;
            }
            ticksUsed++;
            if (this.ticksUsed % 3 == 0 && !world.isClient()) {
                world.playSound(null, this.obj.getX(), this.obj.getY(), this.obj.getZ(), RodsFromGodSoundEvents.SOLAR_PRISM_HEADSET_AMBIENT, SoundCategory.PLAYERS, 0.8F, 1.0F);
            }
        } else {
            overheatTicks--;
            ticksUsed = 0;
        }
        overheatTicks = Math.min(Math.max(overheatTicks, 0), MAX_OVERHEATING_TICKS);
        //noinspection NonStrictComparisonCanBeEquality
        if (overheatTicks >= MAX_OVERHEATING_TICKS) {
            if (!world.isClient()) {
                DamageSource source = new DamageSource(RodsFromGodDamageTypes.get(RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT, world), this.obj);
                this.obj.damage(source, 0.4f);
                this.obj.setOnFireForTicks(30);
            }
        }
        if (overheatTicks % 10 == 0) {
            RodsFromGodEntityComponents.SOLAR_LASER.sync(this.obj);
        }
    }

    @Override
    public void serverTick() {
        CommonTickingComponent.super.serverTick();
        if (!(this.obj.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        if (this.obj.isSpectator()) {
            return;
        }
        if (!RodsFromGodUtil.isAffectedByDaylight(this.obj)) {
            return;
        }
        if (!this.obj.getEquippedStack(EquipmentSlot.HEAD).isOf(RodsFromGodItems.SOLAR_PRISM_HEADSET)) {
            return;
        }
        HitResult hitResult = this.obj.raycast(384, 1.0F, false);
        if (!(hitResult instanceof BlockHitResult)) {
            return;
        }
        Vec3d hitResultPos = hitResult.getPos();
        Vec3d eyePos = this.obj.getEyePos();
        Vec3d direction = hitResultPos.subtract(eyePos).normalize();
        double distance = eyePos.squaredDistanceTo(hitResultPos);
        Set<Entity> entities = new HashSet<>(1024);
        for (float step = 0; step * step < distance; step += 0.2f) {
            Vec3d vec3d = eyePos.add(direction.multiply(step));
            final double boxRadius = 0.25;
            Vec3d lowerCorner = vec3d.subtract(boxRadius, boxRadius, boxRadius);
            Vec3d upperCorner = vec3d.add(boxRadius, boxRadius, boxRadius);
            Box box = new Box(lowerCorner, upperCorner);
            getEntitiesAndAddToCollection(serverWorld, (entity -> entity.getBoundingBox().intersects(box) && entity instanceof LivingEntity), entities);
        }
        entities.remove(this.obj);
        DamageSource source = new DamageSource(RodsFromGodDamageTypes.get(RodsFromGodDamageTypes.SOLAR_LASER, serverWorld), this.obj);
        entities.forEach(entity -> {
            entity.damage(source, 1);
            if (!entity.isInvulnerable()) entity.setOnFireForTicks(40);
        });
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.overheatTicks = nbtCompound.getInt("overheatTicks");
        this.ticksUsed = nbtCompound.getInt("ticksUsed");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("overheatTicks", this.overheatTicks);
        nbtCompound.putInt("ticksUsed", this.ticksUsed);
    }

    public int getOverheatTicks() {
        return this.overheatTicks;
    }

    @SuppressWarnings("NonStrictComparisonCanBeEquality") // AMARONG AGAIN??
    private static void getEntitiesAndAddToCollection(ServerWorld serverWorld, Predicate<Entity> predicate, Collection<Entity> entities) {
        AtomicInteger entityCount = new AtomicInteger(0);
        ((ServerWorldAccessor) serverWorld).rods_from_god$invokeGetEntityLookup().forEach(TypeFilter.instanceOf(Entity.class), (entity) -> {
            if (entityCount.get() >= Integer.MAX_VALUE) {
                return LazyIterationConsumer.NextIteration.ABORT;
            }
            if (entity == null || !entity.isAlive()) {
                return LazyIterationConsumer.NextIteration.CONTINUE;
            }
            if (predicate.test(entity)) {
                entityCount.incrementAndGet();
                entities.add(entity);
            }
            return LazyIterationConsumer.NextIteration.CONTINUE;
        });
    }
}
