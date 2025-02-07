package survivalblock.rods_from_god.common.component.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import survivalblock.rods_from_god.common.entity.BookEntity;
import survivalblock.rods_from_god.common.init.RodsFromGodEntityComponents;

public class BookTargetComponent implements AutoSyncedComponent {

    public static final String ONLY_TARGETS_PLAYERS_KEY = "onlyTargetsPlayers";
    public static final String RANGE_KEY = "range";

    private final BookEntity obj;
    private boolean onlyTargetsPlayers = true;
    private double range = 8;

    public BookTargetComponent(BookEntity book) {
        this.obj = book;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.onlyTargetsPlayers = nbtCompound.getBoolean(ONLY_TARGETS_PLAYERS_KEY);
        this.range = nbtCompound.getDouble(RANGE_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean(ONLY_TARGETS_PLAYERS_KEY, this.onlyTargetsPlayers);
        nbtCompound.putDouble(RANGE_KEY, this.range);
    }

    public boolean onlyTargetsPlayers() {
        return this.onlyTargetsPlayers;
    }

    public double getRange() {
        return this.range;
    }

    public void setValues(boolean onlyTargetsPlayers, double range) {
        this.onlyTargetsPlayers = onlyTargetsPlayers;
        this.range = range;
        RodsFromGodEntityComponents.BOOK_TARGET.sync(this.obj);
    }
}
