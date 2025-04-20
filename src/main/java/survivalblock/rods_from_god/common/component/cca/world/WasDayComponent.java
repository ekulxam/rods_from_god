package survivalblock.rods_from_god.common.component.cca.world;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodWorldComponents;

public class WasDayComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final float DEFAULT_LIGHTNING_VOLUME = 10000.0f;

    private final World world;
    private boolean wasDay = true;
    private float prevLightningVolume = DEFAULT_LIGHTNING_VOLUME;
    private float lightningThunderVolume = DEFAULT_LIGHTNING_VOLUME;

    public WasDayComponent(World world) {
        this.world = world;
    }

    @Override
    public void serverTick() {
        if (!(this.world instanceof ServerWorld serverWorld)) {
            return;
        }
        boolean isDay = serverWorld.isDay();
        if (wasDay != isDay || prevLightningVolume != lightningThunderVolume || serverWorld.getTime() % 200 == 0) {
            // apparently the client thinks it's always day somehow (according to the output and usages of the isDay method)
            wasDay = isDay;
            prevLightningVolume = lightningThunderVolume;
            RodsFromGodWorldComponents.WAS_DAY.sync(this.world);
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.wasDay = nbtCompound.getBoolean("wasDay");
        this.lightningThunderVolume = nbtCompound.getFloat("lightningThunderVolume");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("wasDay", this.wasDay);
        nbtCompound.putFloat("lightningThunderVolume", this.lightningThunderVolume);
    }

    public boolean wasDay() {
        return this.wasDay;
    }

    public void setLightningVolume(float lightningVolume) {
        this.lightningThunderVolume = lightningVolume;
    }

    public float getLightningVolume() {
        return this.lightningThunderVolume;
    }
}
