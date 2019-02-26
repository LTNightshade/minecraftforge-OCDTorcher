package de.madone.ocdtorcher.container;

import de.madone.ocdtorcher.capability.CapabilityOCDTorcher;
import de.madone.ocdtorcher.item.ItemOCDTorcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerOCDTorcher extends Container {

    private ItemStack torcher;
    private EntityPlayer player;
    private BlockPos pos;
    private IItemHandler inventory;

    public ContainerOCDTorcher(EntityPlayer player) {
        this.player = player;
        this.pos = player.getPosition();
        this.torcher = (player.getHeldItemMainhand().getItem() instanceof ItemOCDTorcher) ? player.getHeldItemMainhand() : player.getHeldItem(EnumHand.OFF_HAND);
        CapabilityOCDTorcher.ICapabilityOCDTorcher cap = torcher.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElse(null);
        if (cap != null) {
            inventory = cap.GetInventory();
        }
        AddPlayerInventory(14/2,191/2);
        AddInventory(302/2,16/2,inventory);
    }

    private void AddInventory(int x, int y, IItemHandler inventory) {
        for(int u = 0; u < inventory.getSlots(); u++) {
            int x1 = x;
            int y1 = y + u*18;
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
                    addSlot(new Slot(player.inventory, col , x2, y2));
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
}
