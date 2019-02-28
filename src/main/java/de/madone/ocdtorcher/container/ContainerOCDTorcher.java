package de.madone.ocdtorcher.container;

import de.madone.ocdtorcher.capability.CapabilityOCDTorcher;
import de.madone.ocdtorcher.item.ItemOCDTorcher;
import de.madone.ocdtorcher.network.ModNetwork;
import de.madone.ocdtorcher.network.server.SPacketOCDTorcher;
import de.madone.ocdtorcher.stuff.OCDTorcherPattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerOCDTorcher extends Container {

    protected static final Logger LOGGER = LogManager.getLogger();

    private ItemStack torcher;
    private EntityPlayer player;
    private IItemHandler inventory;
    private CapabilityOCDTorcher.ICapabilityOCDTorcher cap;

    private boolean Enabled;
    private boolean PickUpEnabled;
    private BlockPos Origin;
    private OCDTorcherPattern Pattern;
    private boolean IsFirstRun = true;

    @SuppressWarnings("ConstantConditions")
    public ContainerOCDTorcher(EntityPlayer player) {
        this.player = player;
        this.torcher = (player.getHeldItemMainhand().getItem() instanceof ItemOCDTorcher) ? player.getHeldItemMainhand() : player.getHeldItem(EnumHand.OFF_HAND);

        cap = torcher.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElseThrow(NullPointerException::new);
        inventory = cap.GetInventory();

        if (!player.getEntityWorld().isRemote) {
            this.Enabled = cap.GetEnabled();
            this.PickUpEnabled = cap.GetPickupEnabled();
            this.Origin = cap.GetOrigin();
            this.Pattern = cap.GetPattern().Copy();
            ModNetwork.sendOCDTorcherData(this.Enabled, this.PickUpEnabled, this.Origin, this.Pattern, (EntityPlayerMP) player);
        } else {
            this.Enabled = false;
            this.PickUpEnabled = false;
            this.Origin = new BlockPos(0, 0, 0);
            this.Pattern = new OCDTorcherPattern(0, 0, false);
        }

        AddPlayerInventory(14 / 2, 191 / 2);
        AddInventory(302 / 2, 16 / 2, inventory);
    }

    private void AddInventory(int x, int y, IItemHandler inventory) {
        for (int u = 0; u < inventory.getSlots(); u++) {
            int y1 = y + u * 18;
            addSlot(new SlotItemHandler(inventory, u, x, y1));
        }
    }

    @SuppressWarnings("NullableProblems")
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

    public void ProcessMessage(SPacketOCDTorcher.PacketTorcher data) {
        this.Enabled = data.isEnabled();
        this.PickUpEnabled = data.isPickUpEnabled();
        this.Origin = data.getOrigin();
        this.Pattern = data.getPattern().Copy();
    }
}
