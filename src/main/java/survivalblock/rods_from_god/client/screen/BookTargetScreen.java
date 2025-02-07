package survivalblock.rods_from_god.client.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import survivalblock.rods_from_god.client.networking.BookTargetC2SPayload;
import survivalblock.rods_from_god.common.component.cca.entity.BookTargetComponent;
import survivalblock.rods_from_god.common.entity.BookEntity;

public class BookTargetScreen extends Screen {

    protected Screen parent;
    protected ButtonWidget doneButton, cancelButton;
    protected CyclingButtonWidget<Boolean> onlyTargetsPlayersButton;

    protected float scale;
    protected int projectileDuration, entityId;
    protected boolean onlyTargetsPlayers;
    protected double range;

    public BookTargetScreen(Text title, Screen parent, BookEntity book) {
        super(title);
        this.parent = parent;
        this.scale = book.getScale();
        this.projectileDuration = book.getProjectileDuration();
        BookTargetComponent component = book.getComponent();
        this.onlyTargetsPlayers = component.onlyTargetsPlayers();
        this.range = component.getRange();
        this.entityId = book.getId();
    }

    @Override
    protected void init() {
        this.doneButton = this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.DONE, button -> this.saveAndClose()).dimensions(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20).build()
        );
        this.cancelButton = this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.close()).dimensions(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20).build()
        );
        this.onlyTargetsPlayersButton = this.addDrawableChild(
                CyclingButtonWidget.<Boolean>builder(value -> Text.translatable("entity.rods_from_god.book.screen.onlyTargetsPlayers." + value))
                        .values(true, false)
                        .omitKeyText()
                        .initially(this.onlyTargetsPlayers)
                        .build((this.width / 2 - 50 - 100 - 4) * 2, this.height / 2 - 80, 154, 20, Text.translatable("entity.rods_from_god.book.screen.onlyTargetsPlayers"), (button, onlyTargetsPlayers) -> this.onlyTargetsPlayers = onlyTargetsPlayers)
        );
        this.addDrawableChild(doneButton);
        this.addDrawableChild(cancelButton);
        this.addDrawableChild(this.onlyTargetsPlayersButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, Text.literal("Arguments").getString(), (this.width / 2 - 50 - 100 - 4) * 2, this.height / 2 - 80 - this.textRenderer.fontHeight, 0xFFFFFFFF, true);
    }

    @Override
    public void close() {
        super.close();
        if (this.client != null) this.client.setScreen(this.parent);
    }

    public void saveAndClose() {
        ClientPlayNetworking.send(new BookTargetC2SPayload(this.scale,
                this.projectileDuration,
                this.onlyTargetsPlayers,
                this.range,
                this.entityId));
        this.close();
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public boolean shouldPause() {
        return true;
    }
}