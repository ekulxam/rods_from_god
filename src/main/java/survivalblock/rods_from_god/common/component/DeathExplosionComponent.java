package survivalblock.rods_from_god.common.component;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class DeathExplosionComponent implements AutoSyncedComponent, CommonTickingComponent {

    private final PlayerEntity obj;
    private boolean shouldExplodeOnDeath = false;

    public DeathExplosionComponent(PlayerEntity player) {
        this.obj = player;
    }

    @Override
    public void tick() {

    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.shouldExplodeOnDeath = nbtCompound.getBoolean("shouldExplodeOnDeath");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("shouldExplodeOnDeath", this.shouldExplodeOnDeath);
    }

    public boolean shouldExplodeOnDeath() {
        return this.shouldExplodeOnDeath;
    }
}
