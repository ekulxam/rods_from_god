package survivalblock.rods_from_god.client.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import survivalblock.rods_from_god.client.networking.TheOneWatchComponentC2SPayload;
import survivalblock.rods_from_god.common.TickSubcommand;

public class TheOneWatchScreen extends Screen {

    protected final String initialSubcommand, initialArguments;

    protected Screen parent;
    protected ButtonWidget doneButton, cancelButton;
    protected CyclingButtonWidget<TickSubcommand> subcommandButton;
    protected TickSubcommand subcommand;
    protected TextFieldWidget textFieldWidget;
    protected int slot, playerEntityId;

    public TheOneWatchScreen(Text title, Screen parent, int slot, int playerEntityId, String initialSubcommand, String initialArguments) {
        super(title);
        this.parent = parent;
        this.slot = slot;
        this.playerEntityId = playerEntityId;
        this.initialSubcommand = initialSubcommand;
        this.subcommand = TickSubcommand.getType(initialSubcommand);
        this.initialArguments = initialArguments;
    }

    @Override
    protected void init() {
        this.doneButton = this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.DONE, button -> this.saveAndClose()).dimensions(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20).build()
        );
        this.cancelButton = this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.close()).dimensions(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20).build()
        );
        this.subcommandButton = this.addDrawableChild(
                CyclingButtonWidget.<TickSubcommand>builder(value -> Text.translatable("item.rods_from_god.the_one_watch.screen.subcommand." + value.getName()))
                        .values(TickSubcommand.values())
                        .omitKeyText()
                        .initially(TickSubcommand.getType(this.initialSubcommand))
                        .build(this.width / 2 - 50 - 100 - 4, this.height / 2 - 80, 300 + 8, 20, Text.translatable("item.rods_from_god.the_one_watch.screen.subcommand"), (button, subcommand) -> this.subcommand = subcommand)
        );
        this.textFieldWidget = new TextFieldWidget(this.textRenderer, this.width / 2 - 50 - 100 - 4, this.height / 2 - 10, 300 + 8, 20, Text.empty());
        this.textFieldWidget.setPlaceholder(Text.literal(this.initialArguments).formatted(Formatting.DARK_GRAY));
        this.textFieldWidget.setEditable(true);
        this.addDrawableChild(doneButton);
        this.addDrawableChild(cancelButton);
        this.addDrawableChild(textFieldWidget);
        this.addDrawableChild(this.subcommandButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, Text.literal("Arguments").getString(), this.width / 2 - 50 - 100 - 4, this.height / 2 - 10 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
    }

    @Override
    public void close() {
        super.close();
        if (this.client != null) this.client.setScreen(this.parent);
    }


    public void saveAndClose() {
        // I'm sorry, Bawnorton
        ClientPlayNetworking.send(new TheOneWatchComponentC2SPayload(
                this.subcommand.getName(), this.textFieldWidget.getText(), this.slot));
        this.close();
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public boolean shouldPause() {
        return true;
    }
}