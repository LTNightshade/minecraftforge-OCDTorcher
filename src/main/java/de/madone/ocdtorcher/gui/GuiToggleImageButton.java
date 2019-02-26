package de.madone.ocdtorcher.gui;

import de.madone.ocdtorcher.ocdtorcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;

public class GuiToggleImageButton extends GuiButton {

    public static final ResourceLocation RLOCOFF = new ResourceLocation(ocdtorcher.ModId, "textures/gui/gui_toggle_button_off.png");
    public static final ResourceLocation RLOCON = new ResourceLocation(ocdtorcher.ModId, "textures/gui/gui_toggle_button_on.png");

    private boolean state = false;
    private BiConsumer<Integer, Boolean> guiResponder;

    public GuiToggleImageButton(int buttonId, int x, int y, int widthIn, int heightIn) {
        super(buttonId, x, y, widthIn, heightIn, "");
    }

    public GuiToggleImageButton(int buttonId, int x, int y) {
        super(buttonId, x, y, 16, 8, "");
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = isMouseOver();
            if (state) {
                Minecraft.getInstance().getTextureManager().bindTexture(RLOCON);
                this.drawModalRectWithCustomSizedTexture(this.x, this.y, 0, 0, this.width, this.height, this.width, this.height * 2);
            }
            else {
                Minecraft.getInstance().getTextureManager().bindTexture(RLOCOFF);
                this.drawModalRectWithCustomSizedTexture(this.x, this.y, 0, 0, this.width, this.height, this.width, this.height * 2);
            }

        }
    }

    @Override
    public void onClick(double p_194829_1_, double p_194829_3_) {
        super.onClick(p_194829_1_, p_194829_3_);
        this.state = !this.state;
        if (this.guiResponder != null) {
            this.guiResponder.accept(id, state);
        }
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        if (this.visible & this.enabled)
            this.state = state;
    }

    public void setClickHandler(BiConsumer<Integer, Boolean> ClickHandler) {
        this.guiResponder = ClickHandler;
    }
}
