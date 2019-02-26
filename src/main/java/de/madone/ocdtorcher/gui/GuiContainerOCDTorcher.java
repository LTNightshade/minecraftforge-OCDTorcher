package de.madone.ocdtorcher.gui;

import de.madone.ocdtorcher.container.ContainerOCDTorcher;
import de.madone.ocdtorcher.nimox;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiContainerOCDTorcher extends GuiContainer {

    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(nimox.ModId, "textures/gui/gui_torcher.png");
    private InventoryPlayer inventoryPlayer;

    public GuiContainerOCDTorcher(ContainerOCDTorcher container) {
        super(container);
        inventoryPlayer = container.getPlayer().inventory;
        xSize = 348 / 2;
        ySize = 348 / 2;
        width = 512;
        height = 512;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format("gui.torcher.name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(inventoryPlayer.getDisplayName().getUnformattedComponentText(), 8, ySize - 94, 0x404040);
    }
}