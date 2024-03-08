package de.hallotheengineer.tarret.client.gui;

import de.hallotheengineer.tarret.Tarret;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AdministrationScreen extends Screen {

    public static final Identifier ADMIN_BACKGROUND_TEXTURE = new Identifier(Tarret.MOD_ID,"textures/gui/admin_background.png");

    public AdministrationScreen(Text title) {
        super(title);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
    }

    @Override
    public void renderBackgroundTexture(DrawContext context) {
        context.setShaderColor(0.25f, 0.25f, 0.25f, 1.0f);
        context.drawTexture(ADMIN_BACKGROUND_TEXTURE, 0, 0, 0, 0.0f, 0.0f, this.width, this.height, 32, 32);
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}