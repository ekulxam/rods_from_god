package survivalblock.rods_from_god.common.component.cca.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import survivalblock.rods_from_god.common.block.SuperBouncySlimeBlock;

/**
 * Both what it sounds like and not what it sounds like<p>
 * This component exists to prevent players from getting kicked for flying on dedicated servers due to bouncing on {@link SuperBouncySlimeBlock}s
 */
public class SlimeBlockFlyingComponent implements CommonTickingComponent {

    private final Entity obj;
    private int bounceAirTicks = 0;
    public static final int MAX_BOUNCE_AIR_TICKS = 11 * 20;

    public SlimeBlockFlyingComponent(Entity entity) {
        this.obj = entity;
    }

    @Override
    public void tick() {
        if (this.obj.getWorld().isClient) {
            this.bounceAirTicks = 0;
        } else {
            if (this.bounceAirTicks > 0) {
                this.bounceAirTicks--;
                if (this.bounceAirTicks > MAX_BOUNCE_AIR_TICKS) {
                    this.bounceAirTicks = MAX_BOUNCE_AIR_TICKS;
                }
            } else {
                this.bounceAirTicks = 0;
            }
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        if (nbtCompound.contains("bounceAirTicks")) {
            this.bounceAirTicks = nbtCompound.getInt("bounceAirTicks");
        }
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("bounceAirTicks", this.bounceAirTicks);
    }

    public void bouncedOnBlock() {
        this.bounceAirTicks = MAX_BOUNCE_AIR_TICKS;
    }

    public boolean shouldFly() {
        return this.bounceAirTicks > 0;
    }
}
