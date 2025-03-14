package survivalblock.rods_from_god.common.component.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

@SuppressWarnings("unused")
public class SmokeScreenComponent implements CommonTickingComponent, AutoSyncedComponent {

    public static final int MAX_SMOKE_SCREEN_TICKS = 400;
    public static final int REAL_MAX_SMOKE_SCREEN_TICKS = MAX_SMOKE_SCREEN_TICKS + 20;

    private final PlayerEntity obj;
    private int smokeScreenTicks = 0;

    public SmokeScreenComponent(PlayerEntity player) {
        this.obj = player;
    }

    @Override
    public void tick() {
        if (smokeScreenTicks > 0) {
            smokeScreenTicks--;
            if (smokeScreenTicks > REAL_MAX_SMOKE_SCREEN_TICKS) {
                smokeScreenTicks = REAL_MAX_SMOKE_SCREEN_TICKS;
            }
        } else if (smokeScreenTicks < 0) {
            smokeScreenTicks = 0;
        }
        if (this.obj.getWorld().getTime() % 20 == 0) {
            RodsFromGodEntityComponents.SMOKE_SCREEN.sync(this.obj);
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.smokeScreenTicks = nbtCompound.getInt("smokeScreenTicks");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("smokeScreenTicks", this.smokeScreenTicks);
    }

    public int getSmokeScreenTicks() {
        return this.smokeScreenTicks;
    }

    public float getOverlayFactor() {
        return ((float) Math.min(this.smokeScreenTicks, MAX_SMOKE_SCREEN_TICKS) / MAX_SMOKE_SCREEN_TICKS);
    }

    public void setSmokeScreenTicks() {
        this.setSmokeScreenTicks(REAL_MAX_SMOKE_SCREEN_TICKS);
    }

    public void setSmokeScreenTicks(int smokeScreenTicks) {
        this.smokeScreenTicks = smokeScreenTicks;
        RodsFromGodEntityComponents.SMOKE_SCREEN.sync(this.obj);
    }
}
