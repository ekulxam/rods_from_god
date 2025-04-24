package survivalblock.rods_from_god.common.compat;

import com.bawnorton.mixinsquared.adjuster.MixinAnnotationAdjusterRegistrar;
import com.bawnorton.mixinsquared.api.MixinCanceller;
import com.bawnorton.mixinsquared.canceller.MixinCancellerRegistrar;
import net.fabricmc.loader.api.FabricLoader;
import survivalblock.atmosphere.atmospheric_api.mixin.compat.AtmosphericMixinConfigPlugin;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class RodsFromGodMixinPlugin implements AtmosphericMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
        MixinCancellerRegistrar.register(RodsFromGodMixinChanger.INSTANCE);
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        final String lower = mixinClassName.toLowerCase(Locale.ROOT);
        if (lower.contains("compat")) {
            if (lower.contains("config")) {
                return FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
            }
            if (lower.contains("lithium")) {
                FabricLoader fabricLoader = FabricLoader.getInstance();
                return fabricLoader.isModLoaded("lithium") && new File(fabricLoader.getConfigDir() + "/rods_from_god_collisions.json").exists();
            }
            if (lower.contains("affinity")) {
                return FabricLoader.getInstance().isModLoaded("affinity");
            }
        }
        if (lower.contains("medusa") && lower.contains("collision")) {
            return new File(FabricLoader.getInstance().getConfigDir() + "/rods_from_god_collisions.json").exists();
        }
        return true;
    }

    public static class RodsFromGodMixinChanger implements MixinCanceller {

        public static final RodsFromGodMixinChanger INSTANCE = new RodsFromGodMixinChanger();

        private RodsFromGodMixinChanger() {

        }

        @Override
        public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
            if (mixinClassName.equals("io.wispforest.affinity.mixin.client.BeaconBlockEntityRendererMixin")) {
                return true;
            }
            return false;
        }
    }
}
