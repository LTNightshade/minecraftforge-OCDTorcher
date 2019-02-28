package de.madone.ocdtorcher.container;

import de.madone.ocdtorcher.capability.CapabilityOCDTorcher;
import de.madone.ocdtorcher.item.ItemOCDTorcher;
import de.madone.ocdtorcher.stuff.OCDTorcherPattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerOCDTorcher extends Container {

    protected static final Logger LOGGER = LogManager.getLogger();

    private ItemStack torcher;
    private EntityPlayer player;
    private BlockPos pos;
    private IItemHandler inventory;
    private CapabilityOCDTorcher.ICapabilityOCDTorcher cap;

    private boolean Enabled;
    private boolean PickUpEnabled;
    private BlockPos Origin;
    private OCDTorcherPattern Pattern;

    public ContainerOCDTorcher(EntityPlayer player) {
        this.player = player;
        this.pos = player.getPosition();
        this.torcher = (player.getHeldItemMainhand().getItem() instanceof ItemOCDTorcher) ? player.getHeldItemMainhand() : player.getHeldItem(EnumHand.OFF_HAND);
        cap = torcher.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElse(null);
        this.Enabled = cap.GetEnabled();
        this.PickUpEnabled = cap.GetPickupEnabled();
        this.Origin = cap.GetOrigin();
        this.Pattern = cap.GetPattern();

        if (cap != null) {
            inventory = cap.GetInventory();
        }
        AddPlayerInventory(14 / 2, 191 / 2);
        AddInventory(302 / 2, 16 / 2, inventory);
    }

    private void AddInventory(int x, int y, IItemHandler inventory) {
        for (int u = 0; u < inventory.getSlots(); u++) {
            int x1 = x;
            int y1 = y + u * 18;
            addSlot(new SlotItemHandler(inventory, u, x1, y1));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    public ItemStack getTorcher() {
        return torcher;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void AddPlayerInventory(int x, int y) {
        // 14,191
        int i = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 9; col++) {
                int x2 = x + (col * 18);
                int y2 = y + (row * 18);
                if (row == 3) {
                    y2 = y2 + 3;
                    addSlot(new Slot(player.inventory, col, x2, y2));
                } else {
                    addSlot(new Slot(player.inventory, col + (row * 9) + 9, x2, y2));
                }
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
        return super.transferStackInSlot(p_82846_1_, p_82846_2_);
    }

    @Override
    public void detectAndSendChanges() {
        LOGGER.info("Starting DetectChanges");
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); i++) {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.Enabled != cap.GetEnabled()) {
                icontainerlistener.sendWindowProperty(this, 0, cap.GetEnabled() ? 1 : 0);
            }
            if (this.PickUpEnabled != cap.GetPickupEnabled()) {
                icontainerlistener.sendWindowProperty(this, 1, cap.GetPickupEnabled() ? 1 : 0);
            }
            if (this.Pattern.isAlternating() != cap.GetPattern().isAlternating()) {
                icontainerlistener.sendWindowProperty(this, 2, cap.GetPattern().isAlternating() ? 1 : 0);
            }
            if (this.Origin.getX() != cap.GetOrigin().getX()) {
                icontainerlistener.sendWindowProperty(this, 3, cap.GetOrigin().getX());
            }
            if (this.Origin.getY() != cap.GetOrigin().getY()) {
                icontainerlistener.sendWindowProperty(this, 4, cap.GetOrigin().getY());
            }
            if (this.Origin.getZ() != cap.GetOrigin().getZ()) {
                icontainerlistener.sendWindowProperty(this, 5, cap.GetOrigin().getZ());
            }
            if (this.Pattern.getWidth() != cap.GetPattern().getWidth()) {
                icontainerlistener.sendWindowProperty(this, 6, cap.GetPattern().getWidth());
            }
            if (this.Pattern.getHeight() != cap.GetPattern().getHeight()) {
                icontainerlistener.sendWindowProperty(this, 7, cap.GetPattern().getHeight());
            }
        }
        this.Enabled = cap.GetEnabled();
        this.PickUpEnabled = cap.GetPickupEnabled();
        this.Origin = cap.GetOrigin();
        this.Pattern = cap.GetPattern();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        LOGGER.info(String.format("UpdateProgressbar (%d) %d", id, data));
        switch (id) {
            case 0:
                this.Enabled = data == 1;
                break;
            case 1:
                this.PickUpEnabled = data == 1;
                break;
            case 2:
                this.Pattern.setAlternating(data == 1);
                break;
            case 3:
                this.Origin = new BlockPos(data, this.Origin.getY(), this.Origin.getZ());
                break;
            case 4:
                this.Origin = new BlockPos(this.Origin.getX(), data, this.Origin.getZ());
                break;
            case 5:
                this.Origin = new BlockPos(this.Origin.getX(), this.Origin.getY(), data);
                break;
            case 6:
                this.Pattern.setWidth(data);
                break;
            case 7:
                this.Pattern.setHeight(data);
                break;
        }
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public boolean isPickUpEnabled() {
        return PickUpEnabled;
    }

    public BlockPos getOrigin() {
        return Origin;
    }

    public OCDTorcherPattern getPattern() {
        return Pattern;
    }
}
