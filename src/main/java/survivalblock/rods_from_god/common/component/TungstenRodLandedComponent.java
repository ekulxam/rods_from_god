package survivalblock.rods_from_god.common.component;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import survivalblock.rods_from_god.common.entity.TungstenRodEntity;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

public class TungstenRodLandedComponent implements AutoSyncedComponent {

    private final TungstenRodEntity obj;
    private boolean landed = false;

    public TungstenRodLandedComponent(TungstenRodEntity entity) {
        this.obj = entity;
    }
    
    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.landed = nbtCompound.getBoolean("landed");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("landed", this.landed);
    }

    public boolean isLanded() {
        return landed;
    }

    public void setLanded(boolean landed) {
        this.landed = landed;
        RodsFromGodEntityComponents.TUNGSTEN_ROD_LANDED.sync(this.obj);
    }
}
