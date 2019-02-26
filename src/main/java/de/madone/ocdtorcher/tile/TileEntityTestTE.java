package de.madone.ocdtorcher.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityTestTE extends TileEntity {

    public TileEntityTestTE(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TileEntityTestTE() {
        this(ModTileEntities.testTETileEntityType);
    }
}
