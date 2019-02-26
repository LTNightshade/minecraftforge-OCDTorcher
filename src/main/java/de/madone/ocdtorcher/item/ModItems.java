package de.madone.ocdtorcher.item;

import de.madone.ocdtorcher.block.BlockBase;
import de.madone.ocdtorcher.block.ModBlocks;
import de.madone.ocdtorcher.nimox;
import net.minecraft.item.Item;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(nimox.ModId)
public class ModItems {

    public static NonNullList<Item> ITEMS = NonNullList.create();

    @ObjectHolder("ocd_torcher")
    public static final ItemOCDTorcher ITEM_OCD_TORCHER = null;

    public static void register(RegistryEvent.Register<Item> itemRegistryEvent) {

        // Own Items
        ITEMS.add(new ItemOCDTorcher());

        // Itemblocks for Blocks
        for(BlockBase b : ModBlocks.BLOCKS) {
            ITEMS.add(b.getItemBlock());
        }

        // Register Items
        for(Item i : ITEMS) {
            itemRegistryEvent.getRegistry().register(i);
        }
    }

}
