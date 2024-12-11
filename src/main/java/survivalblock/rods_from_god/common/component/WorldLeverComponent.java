package survivalblock.rods_from_god.common.component;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodWorldComponents;

public class WorldLeverComponent implements AutoSyncedComponent, CommonTickingComponent {

    private final World world;
    private boolean lifted = false;
    private boolean switching = false;
    private int celestialZoom;
    public static final int MAX_CELESTIAL_ZOOM = 60;
    public static final float MAX_CELESTIAL_SCALE = 8;

    public WorldLeverComponent(World world) {
        this.world = world;
    }

    public boolean lifted() {
        return this.lifted;
    }

    public void setLifted(boolean lifted) {
        this.lifted = lifted;
        this.switching = true;
        this.celestialZoom = MAX_CELESTIAL_ZOOM;
        RodsFromGodWorldComponents.WORLD_LEVER.sync(this.world);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.lifted = nbtCompound.getBoolean("lifted");
        this.switching = nbtCompound.getBoolean("switching");
        this.celestialZoom = nbtCompound.getInt("celestialZoom");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("lifted", this.lifted);
        nbtCompound.putBoolean("switching", this.switching);
        nbtCompound.putInt("celestialZoom", this.celestialZoom);
    }

    @Override
    public void tick() {
        if (switching) {
            if (celestialZoom > 0) {
                celestialZoom--;
            } else {
                celestialZoom = 0;
                switching = false;
            }
        }
        if (celestialZoom <= 0) {
            celestialZoom = 0;
            switching = false;
        }
    }

    public boolean isSwitching() {
        return this.switching;
    }

    public int getCelestialZoom() {
        return this.celestialZoom;
    }
}
