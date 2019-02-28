package de.madone.ocdtorcher.gui;

import de.madone.ocdtorcher.container.ContainerOCDTorcher;
import de.madone.ocdtorcher.network.ModNetwork;
import de.madone.ocdtorcher.network.client.CPacketOCDTorcher;
import de.madone.ocdtorcher.ocdtorcher;
import de.madone.ocdtorcher.stuff.OCDTorcherPattern;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class GuiContainerOCDTorcher extends GuiContainer {

    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(ocdtorcher.ModId, "textures/gui/gui_torcher.png");
    private InventoryPlayer inventoryPlayer;
    //private CapabilityOCDTorcher.ICapabilityOCDTorcher cap;
    private ItemStack stack;
    private EntityPlayer player;
    private ContainerOCDTorcher container;

    private GuiToggleImageButton ButtonEnabled;
    private GuiToggleImageButton ButtonPickup;
    private GuiToggleImageButton ButtonAlternating;
    private GuiTextField tf_pattern_x;
    private GuiTextField tf_pattern_z;

    private boolean Enabled;
    private boolean PickUpEnabled;
    private BlockPos Origin;
    private OCDTorcherPattern Pattern;

    public GuiContainerOCDTorcher(ContainerOCDTorcher container) {
        super(container);
        this.container = container;
        stack = container.getTorcher();
        player = container.getPlayer();

        this.Enabled = container.isEnabled();
        this.PickUpEnabled = container.isPickUpEnabled();
        this.Origin = container.getOrigin();
        this.Pattern = container.getPattern();

        inventoryPlayer = container.getPlayer().inventory;
        xSize = 348 / 2;
        ySize = 348 / 2;
        width = 512;
        height = 512;
    }

    @Override
    protected void initGui() {
        super.initGui();
        this.Enabled = container.isEnabled();
        this.PickUpEnabled = container.isPickUpEnabled();
        this.Origin = container.getOrigin();
        this.Pattern = container.getPattern();

        ButtonEnabled = new GuiToggleImageButton(0, guiLeft + (107 / 2), guiTop + (14 / 2));
        ButtonEnabled.setState(Enabled);
        ButtonEnabled.setClickHandler(this::ButtonClicked);
        ButtonPickup = new GuiToggleImageButton(1, guiLeft + (259 / 2), guiTop + (14 / 2));
        ButtonPickup.setState(PickUpEnabled);
        ButtonPickup.setClickHandler(this::ButtonClicked);
        ButtonAlternating = new GuiToggleImageButton(2, guiLeft + (107 / 2), guiTop + (158 / 2));
        ButtonAlternating.setState(Pattern.isAlternating());
        ButtonAlternating.setClickHandler(this::ButtonClicked);
        tf_pattern_x = new GuiTextField(6, fontRenderer, (108 / 2), (87 / 2), 30, 14) {
            @Override
            public void setFocused(boolean p_146195_1_) {
                super.setFocused(p_146195_1_);
                if (p_146195_1_) {
                    GuiContainerOCDTorcher.this.tf_pattern_z.setFocused(false);
                }
            }
        };
        tf_pattern_x.setMaxStringLength(2);
        tf_pattern_x.setText(String.format("%d", Pattern.getWidth()));
        tf_pattern_x.setTextAcceptHandler(this::TextAccepted);
        tf_pattern_z = new GuiTextField(7, fontRenderer, (108 / 2), (123 / 2), 30, 14) {
            @Override
            public void setFocused(boolean p_146195_1_) {
                super.setFocused(p_146195_1_);
                if (p_146195_1_) {
                    GuiContainerOCDTorcher.this.tf_pattern_x.setFocused(false);
                }
            }
        };
        tf_pattern_z.setMaxStringLength(2);
        tf_pattern_z.setText(String.format("%d", Pattern.getHeight()));
        tf_pattern_z.setTextAcceptHandler(this::TextAccepted);

        addButton(ButtonEnabled);
        addButton(ButtonPickup);
        addButton(ButtonAlternating);

        this.children.add(tf_pattern_x);
        this.children.add(tf_pattern_z);
    }

    private void ButtonClicked(Integer buttonId, Boolean state) {
        if (buttonId == 0) {
            if (Enabled != state) {
                Enabled = state;
                CPacketOCDTorcher.PacketTorcher pkt = new CPacketOCDTorcher.PacketTorcher(
                        CPacketOCDTorcher.CommandID.BUTTON_ENABLE,
                        state ? 1 : 0
                );
                ModNetwork.HANDLER.sendToServer(new CPacketOCDTorcher(pkt));
            }
        } else if (buttonId == 1) {
            if (PickUpEnabled != state) {
                PickUpEnabled = state;
                CPacketOCDTorcher.PacketTorcher pkt = new CPacketOCDTorcher.PacketTorcher(
                        CPacketOCDTorcher.CommandID.BUTTON_PICKUP,
                        state ? 1 : 0
                );
                ModNetwork.HANDLER.sendToServer(new CPacketOCDTorcher(pkt));
            }
        } else if (buttonId == 2) {
            if (Pattern.isAlternating() != state) {
                Pattern.setAlternating(state);
                CPacketOCDTorcher.PacketTorcher pkt = new CPacketOCDTorcher.PacketTorcher(
                        CPacketOCDTorcher.CommandID.BUTTON_ALTERNATE,
                        state ? 1 : 0
                );
                ModNetwork.HANDLER.sendToServer(new CPacketOCDTorcher(pkt));
            }
        }
    }

    private void TextAccepted(Integer button, String s) {

        if (s.equalsIgnoreCase(""))
            return;

        int y = TryParse(s);
        if ((y > 0) & (y < 256)) {
            switch (button) {
                case 6:
                    if (Pattern.getWidth() != y) {
                        tf_pattern_x.setTextColor(Color.WHITE.getRGB());
                        CPacketOCDTorcher.PacketTorcher pkt = new CPacketOCDTorcher.PacketTorcher(
                                CPacketOCDTorcher.CommandID.TEXTFIELD_PATTERN_X,
                                y
                        );
                        ModNetwork.HANDLER.sendToServer(new CPacketOCDTorcher(pkt));
                    }
                    break;
                case 7:
                    if (Pattern.getHeight() != y) {
                        tf_pattern_z.setTextColor(Color.WHITE.getRGB());
                        CPacketOCDTorcher.PacketTorcher pkt = new CPacketOCDTorcher.PacketTorcher(
                                CPacketOCDTorcher.CommandID.TEXTFIELD_PATTERN_Z,
                                y
                        );
                        ModNetwork.HANDLER.sendToServer(new CPacketOCDTorcher(pkt));
                    }
                    break;
            }
        } else {
            switch (button) {
                case 6:
                    tf_pattern_x.setTextColor(Color.RED.getRGB());
                    break;
                case 7:
                    tf_pattern_z.setTextColor(Color.RED.getRGB());
                    break;
            }
        }
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
        this.Enabled = container.isEnabled();
        this.PickUpEnabled = container.isPickUpEnabled();
        this.Origin = container.getOrigin();
        this.Pattern = container.getPattern();
        if (!tf_pattern_x.isFocused()) {
            if (!tf_pattern_x.getText().equalsIgnoreCase(String.format("%d", Pattern.getWidth()))) {
                tf_pattern_x.setText(String.format("%d", Pattern.getWidth()));
            }
        }
        if (!tf_pattern_z.isFocused()) {
            if (!tf_pattern_z.getText().equalsIgnoreCase(String.format("%d", Pattern.getHeight()))) {
                tf_pattern_z.setText(String.format("%d", Pattern.getHeight()));
            }
        }
        if (ButtonEnabled.isState() != Enabled) {
            ButtonEnabled.setState(Enabled);
        }
        if (ButtonPickup.isState() != PickUpEnabled) {
            ButtonPickup.setState(PickUpEnabled);
        }
        if (ButtonAlternating.isState() != Pattern.isAlternating()) {
            ButtonAlternating.setState(Pattern.isAlternating());
        }
        //String name = I18n.format("gui.torcher.name");
        //fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(inventoryPlayer.getDisplayName().getUnformattedComponentText(), 8, ySize - 94, 0x404040);
        fontRenderer.drawString(String.format("%d,%d,%d", Origin.getX(), Origin.getY(), Origin.getZ()), (108f / 2f), (52f / 2f), 0x404040);
        tf_pattern_x.drawTextField(0, 0, 0);
        tf_pattern_z.drawTextField(0, 0, 0);
    }

    @Override
    public boolean keyPressed(int KeyCode, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (KeyCode == 257) {
            tf_pattern_x.setFocused(false);
            tf_pattern_z.setFocused(false);
        }
        if (tf_pattern_x.isFocused()) {
            return tf_pattern_x.keyPressed(KeyCode, p_keyPressed_2_, p_keyPressed_3_);
        } else if (tf_pattern_z.isFocused()) {
            return tf_pattern_z.keyPressed(KeyCode, p_keyPressed_2_, p_keyPressed_3_);
        } else
            return super.keyPressed(KeyCode, p_keyPressed_2_, p_keyPressed_3_);
    }


    @Override
    public boolean charTyped(char Key, int p_charTyped_2_) {
        if ("0123456789".indexOf(Key) > -1) {
            if (tf_pattern_x.isFocused()) return tf_pattern_x.charTyped(Key, p_charTyped_2_);
            else if (tf_pattern_z.isFocused()) return tf_pattern_z.charTyped(Key, p_charTyped_2_);
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {

        for (Object o : getChildren()) {
            if (o instanceof GuiTextField) {
                GuiTextField tf = ((GuiTextField) o);
                if (tf.mouseClicked(x - guiLeft, y - guiTop, button))
                    return true;
            }
        }
        return (super.mouseClicked(x, y, button));

    }

    private int TryParse(String s) {
        int result;

        try {
            result = Integer.parseInt(s);
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }
}