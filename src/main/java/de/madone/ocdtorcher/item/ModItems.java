package de.madone.ocdtorcher.item;

import de.madone.ocdtorcher.block.ModBlocks;
import de.madone.ocdtorcher.ocdtorcher;
import de.madone.ocdtorcher.registry.IBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ocdtorcher.ModId)
public class ModItems {

    public static NonNullList<Item> ITEMS = NonNullList.create();

    @ObjectHolder("ocd_torcher")
    public static final ItemOCDTorcher ITEM_OCD_TORCHER = null;

    public static void register(RegistryEvent.Register<Item> itemRegistryEvent) {

        // Own Items
        ITEMS.add(new ItemOCDTorcher());

        // Itemblocks for Blocks
        for (Block b : ModBlocks.BLOCKS) {
            if (b instanceof IBlockRegistry) {
                ITEMS.add(new ItemBlock(b, ((IBlockRegistry) b).getDefaultProperties()).setRegistryName(b.getRegistryName().getPath()));
            }
        }

        // Register Items
        for (Item i : ITEMS) {
            itemRegistryEvent.getRegistry().register(i);
        }
    }

}
