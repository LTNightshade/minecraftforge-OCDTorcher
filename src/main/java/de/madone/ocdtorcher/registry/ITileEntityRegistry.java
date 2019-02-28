package de.madone.ocdtorcher.registry;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.function.Supplier;

public interface ITileEntityRegistry {

    // should return TileEntityxxx::new;
    Supplier<TileEntity> getTileEntitySupplier();

    default TileEntityType.Builder getTileEntityTypeBuilder() {
        return TileEntityType.Builder.create(getTileEntitySupplier());
    }
}
