package survivalblock.rods_from_god.common.component.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.dynamic.Codecs;
import survivalblock.atmosphere.atmospheric_api.not_mixin.datafixer.AtmosphericPacketCodecs;

import java.util.Optional;
import java.util.function.Consumer;

import static survivalblock.rods_from_god.common.entity.TungstenRodEntity.*;
import static survivalblock.rods_from_god.common.item.AimingDeviceItem.DEFAULT_COOLDOWN_TICKS;

@SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
public class AimingDeviceComponent implements TooltipAppender {

    public static final AimingDeviceComponent DEFAULT_INSTANCE = new Builder().build();
    public static final AimingDeviceComponent CREATES_FIRE = new Builder().createsFire(true).build();

    public static final Codec<AimingDeviceComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codecs.NONNEGATIVE_INT.optionalFieldOf("cooldown").forGetter((component) -> component.cooldown),
                            Codecs.NONNEGATIVE_INT.optionalFieldOf("maxExplosions").forGetter((component) -> component.maxExplosions),
                            Codecs.NONNEGATIVE_INT.optionalFieldOf("explosionPower").forGetter((component) -> component.explosionPower),
                            Codecs.NONNEGATIVE_INT.optionalFieldOf("inverseExplosionDamageFactor").forGetter((component) -> component.inverseExplosionDamageFactor),
                            Codec.DOUBLE.optionalFieldOf("gravity").forGetter((component) -> component.gravity),
                            Codecs.POSITIVE_FLOAT.optionalFieldOf("scale").forGetter((component) -> component.scale),
                            Codec.BOOL.optionalFieldOf("createsFire").forGetter((component) -> component.createsFire)
                    )
                    .apply(instance, AimingDeviceComponent::new)
    );
    public static final PacketCodec<RegistryByteBuf, AimingDeviceComponent> PACKET_CODEC = AtmosphericPacketCodecs.tuple(
            PacketCodecs.VAR_INT.collect(PacketCodecs::optional), ((component) -> component.cooldown),
            PacketCodecs.VAR_INT.collect(PacketCodecs::optional), ((component) -> component.maxExplosions),
            PacketCodecs.VAR_INT.collect(PacketCodecs::optional), ((component) -> component.explosionPower),
            PacketCodecs.VAR_INT.collect(PacketCodecs::optional), ((component) -> component.inverseExplosionDamageFactor),
            PacketCodecs.DOUBLE.collect(PacketCodecs::optional), ((component) -> component.gravity),
            PacketCodecs.FLOAT.collect(PacketCodecs::optional), ((component) -> component.scale),
            PacketCodecs.BOOL.collect(PacketCodecs::optional), ((component) -> component.createsFire),
            AimingDeviceComponent::new
    );

    private final Optional<Integer> cooldown;
    private final Optional<Integer> maxExplosions;
    private final Optional<Integer> explosionPower;
    private final Optional<Integer> inverseExplosionDamageFactor;
    private final Optional<Double> gravity;
    private final Optional<Float> scale;
    private final Optional<Boolean> createsFire;

    public AimingDeviceComponent(int cooldown, int maxExplosions, int explosionPower, int inverseExplosionDamageFactor, double gravity, float scale, boolean createsFire) {
        this(Optional.of(cooldown), Optional.of(maxExplosions), Optional.of(explosionPower), Optional.of(inverseExplosionDamageFactor), Optional.of(gravity), Optional.of(scale), Optional.of(createsFire));
    }

    public AimingDeviceComponent(Optional<Integer> cooldown, Optional<Integer> maxExplosions, Optional<Integer> explosionPower, Optional<Integer> inverseExplosionDamageFactor, Optional<Double> gravity, Optional<Float> scale, Optional<Boolean> createsFire) {
        this.cooldown = cooldown;
        this.maxExplosions = maxExplosions;
        this.explosionPower = explosionPower;
        this.inverseExplosionDamageFactor = inverseExplosionDamageFactor;
        this.gravity = gravity;
        this.scale = scale;
        this.createsFire = createsFire;
    }

    public int cooldown() {
        return cooldown.orElse(DEFAULT_COOLDOWN_TICKS);
    }

    public int maxExplosions() {
        return maxExplosions.orElse(DEFAULT_MAX_EXPLOSIONS);
    }

    public int explosionPower() {
        return explosionPower.orElse(DEFAULT_EXPLOSION_POWER);
    }

    public int inverseExplosionDamageFactor() {
        return inverseExplosionDamageFactor.orElse(DEFAULT_INVERSE_EXPLOSION_DAMAGE_FACTOR);
    }

    public double gravity() {
        return gravity.orElse(DEFAULT_GRAVITY);
    }

    public float scale() {
        return scale.orElse(DEFAULT_SCALE);
    }

    public boolean createsFire() {
        return createsFire.orElse(DEFAULT_FIRE_BOOLEAN_VALUE);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AimingDeviceComponent aimingDeviceComponent) {
            return (this.cooldown() == aimingDeviceComponent.cooldown())
                    && (this.maxExplosions() == aimingDeviceComponent.maxExplosions())
                    && (this.explosionPower() == aimingDeviceComponent.explosionPower())
                    && (this.inverseExplosionDamageFactor() == aimingDeviceComponent.inverseExplosionDamageFactor())
                    && (this.gravity() == aimingDeviceComponent.gravity())
                    && (this.scale() == aimingDeviceComponent.scale())
                    && (this.createsFire() == aimingDeviceComponent.createsFire());
        }
        return false;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if (createsFire()) tooltip.accept(Text.translatable("item.rods_from_god.aiming_device.aiming_device_creates_fire").withColor(16742418)); // orange
        tooltip.accept(Text.translatable("item.rods_from_god.aiming_device.aiming_device_cooldown", cooldown()).formatted(Formatting.GRAY));
        tooltip.accept(Text.translatable("item.rods_from_god.aiming_device.aiming_device_max_explosions", maxExplosions()).formatted(Formatting.GRAY));
        tooltip.accept(Text.translatable("item.rods_from_god.aiming_device.aiming_device_explosion_power", explosionPower()).formatted(Formatting.GRAY));
        tooltip.accept(Text.translatable("item.rods_from_god.aiming_device.aiming_device_inverse_explosion_damage_factor", inverseExplosionDamageFactor()).formatted(Formatting.GRAY));
        tooltip.accept(Text.translatable("item.rods_from_god.aiming_device.aiming_device_gravity", gravity()).formatted(Formatting.GRAY));
        tooltip.accept(Text.translatable("item.rods_from_god.aiming_device.aiming_device_scale", scale()).formatted(Formatting.GRAY));
    }

    @SuppressWarnings("unused")
    public static class Builder {
        int cooldown = DEFAULT_COOLDOWN_TICKS;
        int maxExplosions = DEFAULT_MAX_EXPLOSIONS;
        int explosionPower = DEFAULT_EXPLOSION_POWER;
        int inverseExplosionDamageFactor = DEFAULT_INVERSE_EXPLOSION_DAMAGE_FACTOR;
        double gravity = DEFAULT_GRAVITY;
        float scale = DEFAULT_SCALE;
        boolean createsFire = DEFAULT_FIRE_BOOLEAN_VALUE;

        public AimingDeviceComponent build() {
            return new AimingDeviceComponent(cooldown, maxExplosions, explosionPower, inverseExplosionDamageFactor, gravity, scale, createsFire);
        }

        public Builder copyFrom(Builder other) {
            this.cooldown = other.cooldown;
            this.maxExplosions = other.maxExplosions;
            this.explosionPower = other.explosionPower;
            this.inverseExplosionDamageFactor = other.inverseExplosionDamageFactor;
            this.gravity = other.gravity;
            this.scale = other.scale;
            this.createsFire = other.createsFire;
            return this;
        }

        public Builder copyFrom(AimingDeviceComponent aimingDeviceComponent) {
            this.cooldown = aimingDeviceComponent.cooldown();
            this.maxExplosions = aimingDeviceComponent.maxExplosions();
            this.explosionPower = aimingDeviceComponent.explosionPower();
            this.inverseExplosionDamageFactor = aimingDeviceComponent.inverseExplosionDamageFactor();
            this.gravity = aimingDeviceComponent.gravity();
            this.scale = aimingDeviceComponent.scale();
            this.createsFire = aimingDeviceComponent.createsFire();
            return this;
        }

        public Builder cooldown(int cooldown) {
            this.cooldown = cooldown;
            return this;
        }

        public Builder maxExplosions(int maxExplosions) {
            this.maxExplosions = maxExplosions;
            return this;
        }

        public Builder explosionPower(int explosionPower) {
            this.explosionPower = explosionPower;
            return this;
        }

        public Builder inverseExplosionDamageFactor(int inverseExplosionDamageFactor) {
            this.inverseExplosionDamageFactor = inverseExplosionDamageFactor;
            return this;
        }

        public Builder gravity(double gravity) {
            this.gravity = gravity;
            return this;
        }
        public Builder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public Builder createsFire(boolean createsFire) {
            this.createsFire = createsFire;
            return this;
        }
    }
}