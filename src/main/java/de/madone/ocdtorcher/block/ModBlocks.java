package de.madone.ocdtorcher.block;

import de.madone.ocdtorcher.ocdtorcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ocdtorcher.ModId)
public class ModBlocks {

    public static NonNullList<Block> BLOCKS = NonNullList.create();

    @ObjectHolder("test")
    public static final BlockTest BLOCK_TEST = null;
    @ObjectHolder("block_ore_copper")
    public static final BlockOreCopper BLOCK_ORE_COPPER = null;

    public static void register(RegistryEvent.Register<Block> blockRegistryEvent){

        // Create Instances and add to BLOCKS List.
        BLOCKS.add(new BlockTest());
        BLOCKS.add(new BlockOreCopper());

        // Registering all
        for(Block b : BLOCKS) {
            blockRegistryEvent.getRegistry().register(b);
        }
    }

}
