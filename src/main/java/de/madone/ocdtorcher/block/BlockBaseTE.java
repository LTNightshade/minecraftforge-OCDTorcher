package de.madone.ocdtorcher.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class BlockBaseTE<TE extends TileEntity> extends BlockBase {

    public BlockBaseTE(String name, Properties properties) {
        super(name, properties);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public abstract TileEntity createTileEntity(IBlockState state, IBlockReader world);

    public abstract TileEntity createTileEntity();

    public abstract Class getTileEntityClass();

    public abstract Supplier<TileEntity> getTileEntitySupplier();

    public TileEntityType.Builder getTETBuilder() {
        return (TileEntityType.Builder) TileEntityType.Builder.create(getTileEntitySupplier());
    }
}
