package survivalblock.rods_from_god.common.component.cca.world;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodWorldComponents;

public class WasDayComponent implements AutoSyncedComponent, ServerTickingComponent {

    private final World world;
    private boolean wasDay = true;

    public WasDayComponent(World world) {
        this.world = world;
    }

    @Override
    public void serverTick() {
        if (!(this.world instanceof ServerWorld serverWorld)) {
            return;
        }
        boolean isDay = serverWorld.isDay();
        if (wasDay != isDay || serverWorld.getTime() % 200 == 0) {
            // apparently the client thinks it's always day somehow (according to the output and usages of the isDay method)
            wasDay = isDay;
            RodsFromGodWorldComponents.WAS_DAY.sync(this.world);
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.wasDay = nbtCompound.getBoolean("wasDay");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("wasDay", this.wasDay);
    }

    public boolean wasDay() {
        return this.wasDay;
    }
}
