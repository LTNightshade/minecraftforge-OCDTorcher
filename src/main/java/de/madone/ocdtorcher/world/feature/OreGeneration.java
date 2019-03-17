package de.madone.ocdtorcher.world.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;


public class OreGeneration {
    public HashMap<ResourceLocation, OreGenerationOre> Ores;

    public OreGeneration() {
        Ores = new HashMap<>();
    }

    public void AddOreConfig(IBlockState state, ResourceLocation biome, int min, int max, int count, int size) {
        OreGenerationOre Ore;
        if (!Ores.containsKey(state.getBlock().getRegistryName())) {
            Ore = new OreGenerationOre(state.getBlock().getRegistryName(), new OreGenerationBiomeData(min, max, count, size));
        } else {
            Ore = Ores.get(state.getBlock().getRegistryName());
        }
        if (Ore.Biomes.containsKey(biome)) {
            Ore.Biomes.get(biome).minHeight = min;
            Ore.Biomes.get(biome).maxHeight = max;
            Ore.Biomes.get(biome).Count = count;
        } else {
            Ore.Biomes.put(biome, new OreGenerationBiomeData(min, max, count, size));
        }
        Ores.put(state.getBlock().getRegistryName(), Ore);
    }
}

