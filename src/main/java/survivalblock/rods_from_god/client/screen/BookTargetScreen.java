package survivalblock.rods_from_god.client.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import survivalblock.rods_from_god.client.networking.BookTargetC2SPayload;
import survivalblock.rods_from_god.common.TickSubcommand;
import survivalblock.rods_from_god.common.component.cca.entity.BookTargetComponent;
import survivalblock.rods_from_god.common.entity.BookEntity;

public class BookTargetScreen extends Screen {

    protected Screen parent;
    protected ButtonWidget doneButton, cancelButton;
    protected CyclingButtonWidget<Boolean> onlyTargetsPlayersButton;
    protected TextFieldWidget scaleWidget;
    protected TextFieldWidget projectileDurationWidget;
    protected TextFieldWidget rangeWidget;

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
                        .build(this.width / 2 + 4, this.height / 2 - 80, 150, 20, Text.translatable("entity.rods_from_god.book.screen.onlyTargetsPlayers"), (button, onlyTargetsPlayers) -> this.onlyTargetsPlayers = onlyTargetsPlayers)
        );

        this.scaleWidget = new TextFieldWidget(this.textRenderer, this.width / 2 + 4, this.height / 2 - 50, 150, 20, Text.empty());
        this.scaleWidget.setPlaceholder(Text.literal(String.valueOf(this.scale)).formatted(Formatting.DARK_GRAY));
        this.scaleWidget.setEditable(true);
        this.addDrawableChild(this.scaleWidget);

        this.projectileDurationWidget = new TextFieldWidget(this.textRenderer, this.width / 2 + 4, this.height / 2 - 20, 150, 20, Text.empty());
        this.projectileDurationWidget.setPlaceholder(Text.literal(String.valueOf(this.projectileDuration)).formatted(Formatting.DARK_GRAY));
        this.projectileDurationWidget.setEditable(true);
        this.addDrawableChild(this.projectileDurationWidget);

        this.rangeWidget = new TextFieldWidget(this.textRenderer, this.width / 2 + 4, this.height / 2 + 10, 150, 20, Text.empty());
        this.rangeWidget.setPlaceholder(Text.literal(String.valueOf(this.range)).formatted(Formatting.DARK_GRAY));
        this.rangeWidget.setEditable(true);
        this.addDrawableChild(this.rangeWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, Text.translatable("entity.rods_from_god.book.screen.onlyTargetsPlayers").getString(), this.width / 2 - 50 - 100 - 4, this.height / 2 - 83 + this.textRenderer.fontHeight, 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, Text.translatable("entity.rods_from_god.book.screen.scale").getString(), this.width / 2 - 50 - 100 - 4, this.height / 2 - 53 + this.textRenderer.fontHeight, 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, Text.translatable("entity.rods_from_god.book.screen.projectileDuration").getString(), this.width / 2 - 50 - 100 - 4, this.height / 2 - 23 + this.textRenderer.fontHeight, 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, Text.translatable("entity.rods_from_god.book.screen.range").getString(), this.width / 2 - 50 - 100 - 4, this.height / 2 + 7 + this.textRenderer.fontHeight, 0xFFFFFFFF, true);
    }

    @Override
    public void close() {
        super.close();
        if (this.client != null) this.client.setScreen(this.parent);
    }

    public void saveAndClose() {
        String string;
        try {
            string = this.scaleWidget.getText();
            if (!string.isBlank()) {
                this.scale = Float.parseFloat(string);
            }
        } catch (NumberFormatException ignored) {

        }
        try {
            string = this.projectileDurationWidget.getText();
            if (!string.isBlank()) {
                this.projectileDuration = Integer.parseInt(string);
            }
        } catch (NumberFormatException ignored) {

        }
        try {
            string = this.rangeWidget.getText();
            if (!string.isBlank()) {
                this.range = Double.parseDouble(string);
            }
        } catch (NumberFormatException ignored) {

        }
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