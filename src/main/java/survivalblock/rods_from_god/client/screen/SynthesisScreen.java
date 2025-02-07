package survivalblock.rods_from_god.client.screen;

import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import survivalblock.rods_from_god.common.RodsFromGod;

public class SynthesisScreen extends CraftingScreen {

    public static final Identifier SYNTHESIS_TEXTURE = RodsFromGod.id("textures/gui/container/synthesis_table.png");

    public SynthesisScreen(CraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.playerInventoryTitleY += 18;
    }

    @Override
    protected void init() {
        super.init();
        this.titleX -= 18;
        this.titleY -= 18;
    }
}
