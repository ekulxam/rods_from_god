package survivalblock.rods_from_god.common.compat;

import net.fabricmc.loader.api.FabricLoader;
import survivalblock.atmosphere.atmospheric_api.mixin.compat.AtmosphericMixinConfigPlugin;

import java.util.Locale;

public class RodsFromGodMixinPlugin implements AtmosphericMixinConfigPlugin {

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("compat") ) {
            final String lower = mixinClassName.toLowerCase(Locale.ROOT);
            if (lower.contains("config")) {
                return FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
            }
            if (lower.contains("skyrender")) {
                return FabricLoader.getInstance().isModLoaded("sky_aesthetics");
            }
        }
        return true;
    }
}
