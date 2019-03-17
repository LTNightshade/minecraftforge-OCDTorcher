package de.madone.ocdtorcher.world.feature;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class OreGenerationOre {
    public HashMap<ResourceLocation, OreGenerationBiomeData> Biomes;

    public OreGenerationOre() {
        Biomes = new HashMap<>();
    }

    public OreGenerationOre(ResourceLocation oreName, OreGenerationBiomeData data) {
        Biomes = new HashMap<>();
        Biomes.put(oreName, data);
    }
}
