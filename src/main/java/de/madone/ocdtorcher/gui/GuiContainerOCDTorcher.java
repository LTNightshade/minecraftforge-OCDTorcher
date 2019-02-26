package de.madone.ocdtorcher.gui;

import de.madone.ocdtorcher.capability.CapabilityOCDTorcher;
import de.madone.ocdtorcher.container.ContainerOCDTorcher;
import de.madone.ocdtorcher.item.ItemOCDTorcher;
import de.madone.ocdtorcher.network.ModNetwork;
import de.madone.ocdtorcher.network.client.CPacketOCDTorcher;
import de.madone.ocdtorcher.ocdtorcher;
import de.madone.ocdtorcher.stuff.OCDTorcherPattern;
import net.minecraft.client.gui.GuiCommandBlockBase;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiContainerOCDTorcher extends GuiContainer {

    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(ocdtorcher.ModId, "textures/gui/gui_torcher.png");
    private InventoryPlayer inventoryPlayer;
    CapabilityOCDTorcher.ICapabilityOCDTorcher cap;
    ItemStack stack;
    EntityPlayer player;

    GuiToggleImageButton ButtonEnabled;
    GuiToggleImageButton ButtonPickup;
    GuiToggleImageButton ButtonAlternating;
    GuiTextField tf_origin_x;
    GuiTextField tf_origin_z;
    GuiTextField tf_pattern_x;
    GuiTextField tf_pattern_z;

    private boolean enabled;
    private boolean pickUpEnabled;
    private BlockPos origin;
    private int level;
    private OCDTorcherPattern pattern;

    public GuiContainerOCDTorcher(ContainerOCDTorcher container) {
        super(container);
        stack = container.getTorcher();
        player = container.getPlayer();
        cap = stack.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElseThrow(NullPointerException::new);
        inventoryPlayer = container.getPlayer().inventory;
        xSize = 348 / 2;
        ySize = 348 / 2;
        width = 512;
        height = 512;
    }

    @Override
    protected void initGui() {
        super.initGui();
        origin = cap.GetOrigin();
        pattern = cap.GetPattern();
        ButtonEnabled = new GuiToggleImageButton(0, guiLeft + (107 / 2), guiTop + (14 / 2));
        ButtonEnabled.setClickHandler(this::ButtonClicked);
        ButtonPickup = new GuiToggleImageButton(1, guiLeft + (259 / 2), guiTop + (14 / 2));
        ButtonPickup.setClickHandler(this::ButtonClicked);
        ButtonAlternating = new GuiToggleImageButton(2, guiLeft + (107 / 2), guiTop + (158 / 2));
        ButtonAlternating.setClickHandler(this::ButtonClicked);
        tf_origin_x = new GuiTextField(4, fontRenderer, guiLeft + (108 / 2), guiTop + (51 / 2), 50, 14) {
            @Override
            public void setFocused(boolean p_146195_1_) {
                super.setFocused(p_146195_1_);
                if (p_146195_1_) {
                    GuiContainerOCDTorcher.this.tf_origin_z.setFocused(false);
                    GuiContainerOCDTorcher.this.tf_pattern_x.setFocused(false);
                    GuiContainerOCDTorcher.this.tf_pattern_z.setFocused(false);
                }
            }
        };
        tf_origin_x.setMaxStringLength(6);
        tf_origin_x.setTextAcceptHandler(this::TextAccepted);
        tf_origin_x.setText(String.format("%d", origin.getX()));
        tf_origin_z = new GuiTextField(5, fontRenderer, guiLeft + (147 / 2), guiTop + (51 / 2), 50, 14) {
            @Override
            public void setFocused(boolean p_146195_1_) {
                super.setFocused(p_146195_1_);
                if (p_146195_1_) {
                    GuiContainerOCDTorcher.this.tf_origin_x.setFocused(false);
                    GuiContainerOCDTorcher.this.tf_pattern_x.setFocused(false);
                    GuiContainerOCDTorcher.this.tf_pattern_z.setFocused(false);
                }
            }
        };
        tf_origin_z.setMaxStringLength(6);
        tf_origin_z.setTextAcceptHandler(this::TextAccepted);
        tf_origin_z.setText(String.format("%d", origin.getZ()));
        tf_pattern_x = new GuiTextField(6, fontRenderer, guiLeft + (108 / 2), guiTop + (87 / 2), 30, 14) {
            @Override
            public void setFocused(boolean p_146195_1_) {
                super.setFocused(p_146195_1_);
                if (p_146195_1_) {
                    GuiContainerOCDTorcher.this.tf_origin_x.setFocused(false);
                    GuiContainerOCDTorcher.this.tf_origin_z.setFocused(false);
                    GuiContainerOCDTorcher.this.tf_pattern_z.setFocused(false);
                }
            }
        };
        tf_pattern_x.setMaxStringLength(2);
        tf_pattern_x.setTextAcceptHandler(this::TextAccepted);
        tf_pattern_x.setText(String.format("%d", pattern.getWidth()));
        tf_pattern_z = new GuiTextField(7, fontRenderer, guiLeft + (108 / 2), guiTop + (123 / 2), 30, 14) {
            @Override
            public void setFocused(boolean p_146195_1_) {
                super.setFocused(p_146195_1_);
                if (p_146195_1_) {
                    GuiContainerOCDTorcher.this.tf_origin_x.setFocused(false);
                    GuiContainerOCDTorcher.this.tf_pattern_x.setFocused(false);
                    GuiContainerOCDTorcher.this.tf_origin_z.setFocused(false);
                }
            }
        };
        tf_pattern_z.setMaxStringLength(2);
        tf_pattern_z.setTextAcceptHandler(this::TextAccepted);
        tf_pattern_z.setText(String.format("%d", pattern.getHeight()));

        addButton(ButtonEnabled);
        addButton(ButtonPickup);
        addButton(ButtonAlternating);

        this.children.add(tf_origin_x);
        this.children.add(tf_origin_z);
        this.children.add(tf_pattern_x);
        this.children.add(tf_pattern_z);
    }

    private void ButtonClicked(Integer buttonId, Boolean state) {
        boolean flag = false;
        if (buttonId == 0) {
            if (this.enabled != state) {
                this.enabled = state;
                flag = true;
            }
        } else if (buttonId == 1) {
            if (this.pickUpEnabled != state) {
                this.pickUpEnabled = state;
                flag = true;
            }
        } else if (buttonId == 2) {
            if (this.pattern.isAlternating() != state) {
                this.pattern.setAlternating(state);
                flag = true;
            }
        }
        if (flag) {
            CPacketOCDTorcher.PacketTorcher pkt = new CPacketOCDTorcher.PacketTorcher(
                    stack,
                    this.level,
                    this.enabled,
                    this.pickUpEnabled,
                    this.origin,
                    this.pattern
            );
            ModNetwork.HANDLER.sendToServer(new CPacketOCDTorcher(pkt));
        }
    }

    private void TextAccepted(Integer button, String s) {
        boolean flag = false;
        if (button == 4) {
            if (this.origin.getX() != Integer.parseInt(s)) {
                this.origin = new BlockPos(Integer.parseInt(s), origin.getY(), origin.getZ());
                flag = true;
            }
        } else if (button == 5) {
            if (this.origin.getZ() != Integer.parseInt(s)) {
                this.origin = new BlockPos(origin.getX(), origin.getY(), Integer.parseInt(s));
                flag = true;
            }
        } else if (button == 6) {
            if (this.pattern.getWidth() != Integer.parseInt(s)) {
                this.pattern.setWidth(Integer.parseInt(s));
                flag = true;
            }
        } else if (button == 7) {
            if (this.pattern.getHeight() != Integer.parseInt(s)) {
                this.pattern.setHeight(Integer.parseInt(s));
                flag = true;
            }
        }
        if (flag) {
            CPacketOCDTorcher.PacketTorcher pkt = new CPacketOCDTorcher.PacketTorcher(
                    stack,
                    this.level,
                    this.enabled,
                    this.pickUpEnabled,
                    this.origin,
                    this.pattern
            );
            ModNetwork.HANDLER.sendToServer(new CPacketOCDTorcher(pkt));
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
        String name = I18n.format("gui.torcher.name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(inventoryPlayer.getDisplayName().getUnformattedComponentText(), 8, ySize - 94, 0x404040);
        tf_origin_x.drawTextField(0, 0, 0);
        tf_pattern_z.drawTextField(0, 0, 0);
        tf_pattern_x.drawTextField(0, 0, 0);
        tf_pattern_z.drawTextField(0, 0, 0);
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);

    }

}