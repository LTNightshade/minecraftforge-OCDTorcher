package de.madone.ocdtorcher.item;

import de.madone.ocdtorcher.ocdtorcher;
import net.minecraft.item.Item;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ocdtorcher.ModId)
public class ModItems {

    public static NonNullList<Item> ITEMS = NonNullList.create();

    @ObjectHolder("ocd_torcher")
    public static final ItemOCDTorcher ITEM_OCD_TORCHER = null;

    public static void register(RegistryEvent.Register<Item> itemRegistryEvent) {

        // Itemblocks are Created in the Blocks-Constructor, and added to the ITEMS List.

        // Own Items
        ITEMS.add(new ItemOCDTorcher());

        // Register Items
        for (Item i : ITEMS) {
            itemRegistryEvent.getRegistry().register(i);
        }
    }

}
