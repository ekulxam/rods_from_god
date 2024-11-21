package survivalblock.rods_from_god.common.compat;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import survivalblock.rods_from_god.common.RodsFromGod;

import java.util.List;
import java.util.Set;

public class RodsFromGodMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        /*
        RodsFromGod.isConnectorLoaded = FabricLoader.getInstance().isModLoaded("connector");
        if (mixinClassName.contains("tungstenrod") && mixinClassName.contains("EntityTrackerEntryMixin")) {
            return !RodsFromGod.isConnectorLoaded;
        }

         */
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
