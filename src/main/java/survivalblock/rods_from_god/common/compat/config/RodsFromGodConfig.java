package survivalblock.rods_from_god.common.compat.config;

import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;

public class RodsFromGodConfig {

    private static class Defaults {
        public static boolean allowScreenShakingForArchimedesLever = true;
    }

    public static boolean allowScreenShakingForArchimedesLever() {
        return Defaults.allowScreenShakingForArchimedesLever;
    }

    @SuppressWarnings("unused")
    @Nullable
    public static Screen create(Screen parent) {
        return null;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean load() {
        return false;
    }
}
