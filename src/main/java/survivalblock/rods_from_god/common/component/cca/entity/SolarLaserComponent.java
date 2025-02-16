package survivalblock.rods_from_god.common.component.cca.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import survivalblock.rods_from_god.common.component.item.SolarPrismHeadsetComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodDamageTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodDataComponentTypes;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;
import survivalblock.rods_from_god.common.init.RodsFromGodItems;
import survivalblock.rods_from_god.common.init.RodsFromGodSoundEvents;

import java.util.HashSet;
import java.util.Set;

public class SolarLaserComponent implements CommonTickingComponent, AutoSyncedComponent {

    public static final double MAX_RAYCAST_DISTANCE = 384;
    public static final int MAX_RENDER_DISTANCE = ((int) Math.ceil(MAX_RAYCAST_DISTANCE / 100d)) * 100;
    public static final int MAX_OVERHEATING_TICKS = 600;
    private final PlayerEntity obj;
    private int overheatTicks = 0;
    private int ticksUsed = 0;
    private boolean wasDay = true;

    public SolarLaserComponent(PlayerEntity player) {
        this.obj = player;
    }

    @Override
    public void tick() {
        World world = this.obj.getWorld();
        if (underTheSun(true)) {
            if (shouldOverheat()) {
                overheatTicks++;
            } else {
                overheatTicks--;
            }
            ticksUsed++;
            if (this.ticksUsed % 3 == 0 && !world.isClient()) {
                world.playSound(null, this.obj.getX(), this.obj.getY(), this.obj.getZ(), RodsFromGodSoundEvents.SOLAR_PRISM_HEADSET_AMBIENT, SoundCategory.PLAYERS, 0.85F, 1.0F);
            }
        } else {
            overheatTicks--;
            ticksUsed = 0;
        }
        overheatTicks = Math.min(Math.max(overheatTicks, 0), MAX_OVERHEATING_TICKS);
        //noinspection NonStrictComparisonCanBeEquality
        if (overheatTicks >= MAX_OVERHEATING_TICKS) {
            if (world instanceof ServerWorld serverWorld) {
                RegistryEntry.Reference<DamageType> damageTypeReference = world.atmospheric_api$getEntryFromKey(RegistryKeys.DAMAGE_TYPE, RodsFromGodDamageTypes.SOLAR_LASER_OVERHEAT);
                DamageSource source;
                boolean pvp = serverWorld.getServer().isPvpEnabled();
                if (pvp) {
                    source = new DamageSource(damageTypeReference, this.obj);
                } else {
                    source = new DamageSource(damageTypeReference);
                }
                boolean damaged = this.obj.damage(source, 0.75f);
                if (!damaged && pvp) { // if the damage failed but pvp is on, double-checking basically
                    this.obj.damage(new DamageSource(damageTypeReference), 0.75f);
                }
                DamageSource fireSource = this.obj.getDamageSources().onFire();
                if (!this.obj.isInvulnerableTo(fireSource)) {
                    if (this.obj.getFireTicks() <= 2) {
                        this.obj.setFireTicks(32);
                    }
                }
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
        boolean isDay = serverWorld.isDay();
        if (wasDay != isDay || serverWorld.getTime() % 40 == 0) {
            // apparently the client thinks it's always day somehow (according to the output and usages of the isDay method)
            wasDay = isDay;
            RodsFromGodEntityComponents.SOLAR_LASER.sync(this.obj);
        }
        if (!underTheSun(true)) {
            return;
        }
        HitResult hitResult = this.obj.raycast(MAX_RAYCAST_DISTANCE, 1.0F, false);
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
            serverWorld.atmospheric_api$getAndAddEntitiesToCollection((entity -> entity instanceof LivingEntity && entity.getBoundingBox().intersects(box)), entities);
        }
        entities.remove(this.obj);
        DamageSource source = new DamageSource(serverWorld.atmospheric_api$getEntryFromKey(RegistryKeys.DAMAGE_TYPE, RodsFromGodDamageTypes.SOLAR_LASER), this.obj);
        entities.forEach(entity -> {
            entity.damage(source, 1.375f);
            DamageSource fireSource = this.obj.getDamageSources().onFire();
            if (!entity.isInvulnerableTo(fireSource)) {
                if (entity.getFireTicks() <= 2) {
                    entity.setFireTicks(62); // approximately equivalent to fire aspect lvl 1.5 (at least in the old versions, forgot what it is in 1.21)
                }
            }
        });
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.overheatTicks = nbtCompound.getInt("overheatTicks");
        this.ticksUsed = nbtCompound.getInt("ticksUsed");
        this.wasDay = nbtCompound.getBoolean("wasDay");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("overheatTicks", this.overheatTicks);
        nbtCompound.putInt("ticksUsed", this.ticksUsed);
        nbtCompound.putBoolean("wasDay", this.wasDay);
    }

    public int getOverheatTicks() {
        return this.overheatTicks;
    }

    public boolean underTheSun(boolean accountForUltrawarm) {
        if (this.obj.isSpectator()) {
            return false;
        }
        ItemStack stack = this.obj.getEquippedStack(EquipmentSlot.HEAD);
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        if (!stack.isOf(RodsFromGodItems.SOLAR_PRISM_HEADSET)) {
            return false;
        }
        if (stack.getOrDefault(RodsFromGodDataComponentTypes.SOLAR_PRISM_HEADSET, SolarPrismHeadsetComponent.DEFAULT_INSTANCE).alwaysActive()) {
            return true;
        }
        // ignores weather, not going to fix
        if (this.obj.atmospheric_api$isAffectedByDaylight() && wasDay) {
            return true;
        }
        return accountForUltrawarm && this.obj.getWorld().getDimension().ultrawarm();
    }

    public boolean shouldOverheat() {
        ItemStack stack = this.obj.getEquippedStack(EquipmentSlot.HEAD);
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        if (!stack.isOf(RodsFromGodItems.SOLAR_PRISM_HEADSET)) {
            return false;
        }
        return !stack.getOrDefault(RodsFromGodDataComponentTypes.SOLAR_PRISM_HEADSET, SolarPrismHeadsetComponent.DEFAULT_INSTANCE).noOverheat();
    }
}
