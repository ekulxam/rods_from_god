package survivalblock.rods_from_god.mixin.compat.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.RodsFromGod;
import survivalblock.rods_from_god.common.compat.config.RodsFromGodYACLCompat;
import survivalblock.rods_from_god.common.compat.config.RodsFromGodConfig;

@Environment(EnvType.CLIENT)
@Mixin(value = RodsFromGodConfig.class, remap = false)
public class RodsFromGodConfigMixin {

    @ModifyReturnValue(method = "allowScreenShakingForArchimedesLever", at = @At("RETURN"))
    private static boolean allowScreenShakingForArchimedesLever(boolean original) {
        return RodsFromGodYACLCompat.HANDLER.instance().allowScreenShakingForArchimedesLever;
    }

    @ModifyReturnValue(method = "create", at = @At("RETURN"))
    private static Screen create(Screen original, Screen parent) {
        return RodsFromGod.configLoaded ? RodsFromGodYACLCompat.create(parent) : original;
    }

    @ModifyReturnValue(method = "load", at = @At("RETURN"))
    private static boolean load(boolean original) {
        return RodsFromGod.shouldDoConfig && RodsFromGodYACLCompat.HANDLER.load();
    }
}
