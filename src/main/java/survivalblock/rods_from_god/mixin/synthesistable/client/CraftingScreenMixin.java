package survivalblock.rods_from_god.mixin.synthesistable.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.rods_from_god.client.screen.SynthesisScreen;
import survivalblock.rods_from_god.common.block.SynthesisTable;

@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin extends HandledScreen<CraftingScreenHandler> {

    public CraftingScreenMixin(CraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @WrapOperation(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    private void useSynthesisTexture(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height, Operation<Void> original) {
        if ((CraftingScreen) (Object) this instanceof SynthesisScreen) {
            original.call(instance, SynthesisScreen.SYNTHESIS_TEXTURE, x, y - 18, u, v, width, 202);
            return;
        }
        original.call(instance, texture, x, y, u, v, width, height);
    }

    @WrapOperation(method = "init", at = @At(value = "NEW", target = "(IIIILnet/minecraft/client/gui/screen/ButtonTextures;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)Lnet/minecraft/client/gui/widget/TexturedButtonWidget;"))
    private TexturedButtonWidget moveRecipeBookOnCreation(int x, int y, int width, int height, ButtonTextures textures, ButtonWidget.PressAction pressAction, Operation<TexturedButtonWidget> original) {
        if ((CraftingScreen) (Object) this instanceof SynthesisScreen) {
            return original.call(this.x + 122 + SynthesisTable.SynthesisScreenHandler.RESULT_SLOT_X_OFFSET, y + 36, width, height, textures, pressAction);
        }
        return original.call(x, y, width, height, textures, pressAction);
    }

    @WrapOperation(method = "method_19890", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;setPosition(II)V"))
    private void moveRecipeBook(ButtonWidget instance, int x, int y, Operation<Void> original) {
        if ((CraftingScreen) (Object) this instanceof SynthesisScreen) {
            original.call(instance, this.x + 122 + SynthesisTable.SynthesisScreenHandler.RESULT_SLOT_X_OFFSET, y + 36);
            return;
        }
        original.call(instance, x, y);
    }
}
