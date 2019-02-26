package de.madone.ocdtorcher.block;

import de.madone.ocdtorcher.nimox;
import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(nimox.ModId)
public class ModBlocks {

    public static NonNullList<BlockBase> BLOCKS = NonNullList.create();

    @ObjectHolder("test")
    public static final BlockTest BLOCK_TEST = null;

    public static void register(RegistryEvent.Register<Block> blockRegistryEvent){
        // Create Instances and add to BLOCKS List.
        BLOCKS.add(new BlockTest());

        // Registering all
        for(Block b : BLOCKS) {
            blockRegistryEvent.getRegistry().register(b);
        }
    }

}
