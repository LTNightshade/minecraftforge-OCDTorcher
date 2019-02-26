package de.madone.ocdtorcher.block;

import de.madone.ocdtorcher.tile.ModTileEntities;
import de.madone.ocdtorcher.tile.TileEntityTestTE;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BlockTestTe extends BlockBaseTE<TileEntityTestTE> {

    public BlockTestTe(String name, Properties properties) {
        super(name, properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityTestTE(ModTileEntities.testTETileEntityType);
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityTestTE();
    }

    @Override
    public Class getTileEntityClass() {
        return TileEntityTestTE.class;
    }

    @Override
    public Supplier<TileEntity> getTileEntitySupplier() {
        return TileEntityTestTE::new;
    }
}
