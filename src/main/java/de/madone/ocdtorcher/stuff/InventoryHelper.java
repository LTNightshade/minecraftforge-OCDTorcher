package de.madone.ocdtorcher.stuff;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class InventoryHelper {

    public static ItemStack ExtractItem(ItemStack predicate, int amount, IItemHandler inventory) {
        ItemStack result = ItemStack.EMPTY;
        for(int u = 0; u < inventory.getSlots(); u++) {
            if (ItemStack.areItemsEqual(predicate, inventory.getStackInSlot(u))) {
                ItemStack f = inventory.extractItem(u,amount,false);
                if (!f.isEmpty()) {
                    if (result.isEmpty()) {
                        result = f;
                    }
                     else {
                         result.setCount(result.getCount() + f.getCount());
                    }
                }
                if (!result.isEmpty()) {
                    if (result.getCount() == amount) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static ItemStack InsertItem(ItemStack stack, IItemHandler inventory) {
        ItemStack result = stack.copy();
        for(int u = 0; u < inventory.getSlots(); u++) {
            ItemStack is = inventory.insertItem(u,result, true);
            if (is.isEmpty() ) {
                inventory.insertItem(u,result, false);
                return ItemStack.EMPTY;
            } else if (is.getCount() < result.getCount()) {
                result.setCount(result.getCount() - is.getCount());
            }
        }
        if (result.getCount() <= 0)
            return ItemStack.EMPTY;
        return result;
    }

}
