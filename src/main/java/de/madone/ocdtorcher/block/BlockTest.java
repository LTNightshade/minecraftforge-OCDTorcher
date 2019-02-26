package de.madone.ocdtorcher.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTest extends BlockBase {

    public BlockTest() {
        super("test",
                Block.Properties.create(Material.IRON)
                        .hardnessAndResistance(15.0f, 15.0f)
                        .lightValue(1)
                        .sound(SoundType.METAL)
        );
    }
}
