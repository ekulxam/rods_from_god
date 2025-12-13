package survivalblock.rods_from_god.client.compat.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import survivalblock.rods_from_god.common.RodsFromGod;

@Environment(EnvType.CLIENT)
public interface RodsFromGodConfigScreenCreator {

    RodsFromGodConfigScreenCreator INSTANCE = getInstance();

    @Nullable
    default Screen create(Screen parent) {
        return null;
    }

    static RodsFromGodConfigScreenCreator getInstance() {
        if (RodsFromGod.YACL) {
            return new RodsFromGodYACLConfigScreenCreator();
        }
        return new RodsFromGodConfigScreenCreator() {};
    }

    static void init() {
        // NO-OP
    }
}
