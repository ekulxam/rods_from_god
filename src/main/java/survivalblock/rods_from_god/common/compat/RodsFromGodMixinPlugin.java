package survivalblock.rods_from_god.common.compat;

import net.fabricmc.loader.api.FabricLoader;
import survivalblock.atmosphere.atmospheric_api.mixin.compat.AtmosphericMixinConfigPlugin;

public class RodsFromGodMixinPlugin implements AtmosphericMixinConfigPlugin {

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("compat") && mixinClassName.toLowerCase().contains("config")) {
            return FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
        }
        return true;
    }
}
