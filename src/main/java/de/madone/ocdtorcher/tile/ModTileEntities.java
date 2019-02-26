package de.madone.ocdtorcher.tile;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import de.madone.ocdtorcher.block.BlockBase;
import de.madone.ocdtorcher.block.BlockBaseTE;
import de.madone.ocdtorcher.block.ModBlocks;
import de.madone.ocdtorcher.nimox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(nimox.ModId)
public class ModTileEntities {

    @ObjectHolder("tile_block_test_te")
    public static TileEntityType<?> testTETileEntityType;

    public static void Init(RegistryEvent.Register<TileEntityType<?>> event) {
        for(BlockBase b : ModBlocks.BLOCKS) {
            if (b instanceof BlockBaseTE)
                registerTileEntityType(event.getRegistry(), register("tile_"+b.getRegistryName().getPath(), ((BlockBaseTE)b).getTETBuilder()), "tile_"+b.getRegistryName().getPath());
        }
    }

    protected static <T extends TileEntityType<?>> T registerTileEntityType(IForgeRegistry<TileEntityType<?>> registry, T tileEntityType, String name)
    {
        register(registry, tileEntityType, new ResourceLocation(name));
        return tileEntityType;
    }

    protected static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T thing, ResourceLocation name)
    {
        thing.setRegistryName(name);
        registry.register(thing);
        return thing;
    }

    public static <T extends TileEntity> TileEntityType<T> register(String id, TileEntityType.Builder<T> builder)
    {
        Type<?> type = null;

        try
        {
            type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(1519)).getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        }
        catch (IllegalArgumentException illegalstateexception)
        {
            if (SharedConstants.developmentMode)
            {
                throw illegalstateexception;
            }
        }

        TileEntityType<T> tileEntityType = builder.build(type);
        return tileEntityType;
    }
}
