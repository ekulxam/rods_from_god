package survivalblock.rods_from_god.mixin.archimedeslever.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.common.component.cca.world.WorldLeverComponent;
import survivalblock.rods_from_god.common.init.RodsFromGodWorldComponents;

@Mixin(DebugHud.class)
public class DebugHudMixin {

    @Unique
    private static Random atmospheric_api$random = Random.createLocal();
    @Shadow @Final private MinecraftClient client;

    @ModifyExpressionValue(method = "getLeftText", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 4))
    private String whatIsMyYPosition(String original) {
        if (this.client.world == null) {
            return original;
        }
        WorldLeverComponent worldLeverComponent = RodsFromGodWorldComponents.WORLD_LEVER.get(this.client.world);
        if (worldLeverComponent.lifted() || worldLeverComponent.isSwitching()) {
            // yahoo, more StringBuilder shenanigans
            StringBuilder stringBuilder = new StringBuilder(original);
            int firstSlashIndex = original.indexOf("/");
            int secondSlashIndex = original.indexOf("/", firstSlashIndex + 1);
            if (firstSlashIndex == -1 || secondSlashIndex == -1) {
                // return early if the slashes are missing (or were not found)
                return original;
            }
            stringBuilder.delete(firstSlashIndex + 2, secondSlashIndex - 1); // delete y value but leave the spaces
            String string = worldLeverComponent.isSwitching() ? "" + MathHelper.nextFloat(atmospheric_api$random, -400, 400) : Text.translatable("rods_from_god.archimedes_lever.unknown_y").getString();
            stringBuilder.insert(firstSlashIndex + 2, string); // insert string between the slashes and spaces
            return stringBuilder.toString();
        }
        return original;
    }
}
