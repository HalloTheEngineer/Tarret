package de.hallotheengineer.tarret.client.gui;

import de.hallotheengineer.tarret.networking.packet.c2s.RequestAccessTokenC2SPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class LoginScreen extends Screen {
    public LoginScreen() {
        super(Text.literal("Login Screen"));
    }

    public TextFieldWidget textFieldWidget1;
    public ButtonWidget buttonWidget1;

    @Override
    protected void init() {
        MinecraftClient client = MinecraftClient.getInstance();
        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();

        textFieldWidget1 = new TextFieldWidget(client.textRenderer, w/2-39, h/2, 78, 18, Text.literal("Enter Access Token"));
        addDrawableChild(textFieldWidget1);

        buttonWidget1 = ButtonWidget.builder(Text.translatable("button.tarret.login"), this::onLoginPress).dimensions(w/2-40,h/2+30, 80, 20).build();
        addDrawableChild(buttonWidget1);
        setInitialFocus(textFieldWidget1);
    }

    private void onLoginPress(ButtonWidget buttonWidget) {
        buttonWidget.setMessage(Text.literal("Loading..."));
        buttonWidget.active = false;
        client.execute(() -> {
            ClientPlayNetworking.send(new RequestAccessTokenC2SPacket(textFieldWidget1.getText()));
        });
    }
}
