package survivalblock.rods_from_god.common.component.cca.world;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import survivalblock.atmosphere.atmospheric_api.not_mixin.render.screenshake.ScreenShakeS2CPayload;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.init.RodsFromGodTags;
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

    /**
     * Activates switching between the lifted state and regular state
     * @param lifted if the world should be lifted
     * @return true if the operation succeeded
     * @see WorldLeverComponent#isSwitching()
     */
    public boolean setLifted(boolean lifted) {
        if (!(this.world instanceof ServerWorld serverWorld)) {
            return false;
        }
        if (this.lifted == lifted) {
            return false;
        }
        if (!canSwitch(serverWorld)) {
            return false;
        }
        for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
            ServerPlayNetworking.send(serverPlayer, new ScreenShakeS2CPayload(0.65f, WorldLeverComponent.MAX_CELESTIAL_ZOOM, RodsFromGod.MOD_ID, RodsFromGod.ARCHIMEDES_LEVER_SCREENSHAKE_REASON));
        }
        this.lifted = lifted;
        this.switching = true;
        this.celestialZoom = MAX_CELESTIAL_ZOOM;
        RodsFromGodWorldComponents.WORLD_LEVER.sync(this.world);
        return true;
    }

    public boolean canSwitch(ServerWorld serverWorld) {
        // check if the world actually displays the sun/moon via a tag
        return serverWorld.getDimensionEntry().isIn(RodsFromGodTags.SUN_AND_MOON);
    }

    public boolean isLifted() {
        return this.lifted;
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
