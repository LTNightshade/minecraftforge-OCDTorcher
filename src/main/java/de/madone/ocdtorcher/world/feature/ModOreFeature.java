package de.madone.ocdtorcher.world.feature;

import com.google.common.collect.ImmutableList;
import de.madone.ocdtorcher.block.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraftforge.common.BiomeManager;

public class ModOreFeature {

    public static void Init() {

        BiomeManager.getBiomes(BiomeManager.BiomeType.WARM).forEach((BiomeManager.BiomeEntry biomeEntry) -> biomeEntry.biome.addFeature(
                GenerationStage.Decoration.UNDERGROUND_ORES,
                Biome.createCompositeFeature(
                        Feature.MINABLE,
                        new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.BLOCK_ORE_COPPER.getDefaultState(), 12),
                        Biome.COUNT_RANGE,
                        new CountRangeConfig(20, 30, 40, 70) // Count, MinHeight, MaxHeightBase, MaxHeight
                )
        ));
        BiomeManager.getBiomes(BiomeManager.BiomeType.COOL).forEach((BiomeManager.BiomeEntry biomeEntry) -> biomeEntry.biome.addFeature(
                GenerationStage.Decoration.UNDERGROUND_ORES,
                Biome.createCompositeFeature(
                        Feature.MINABLE,
                        new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.BLOCK_ORE_COPPER.getDefaultState(), 12),
                        Biome.COUNT_RANGE,
                        new CountRangeConfig(20, 30, 40, 70) // Count, MinHeight, MaxHeightBase, MaxHeight
                )
        ));
        BiomeManager.getBiomes(BiomeManager.BiomeType.DESERT).forEach((BiomeManager.BiomeEntry biomeEntry) -> biomeEntry.biome.addFeature(
                GenerationStage.Decoration.UNDERGROUND_ORES,
                Biome.createCompositeFeature(
                        Feature.MINABLE,
                        new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.BLOCK_ORE_COPPER.getDefaultState(), 12),
                        Biome.COUNT_RANGE,
                        new CountRangeConfig(20, 30, 40, 70) // Count, MinHeight, MaxHeightBase, MaxHeight
                )
        ));
        BiomeManager.getBiomes(BiomeManager.BiomeType.ICY).forEach((BiomeManager.BiomeEntry biomeEntry) -> biomeEntry.biome.addFeature(
                GenerationStage.Decoration.UNDERGROUND_ORES,
                Biome.createCompositeFeature(
                        Feature.MINABLE,
                        new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.BLOCK_ORE_COPPER.getDefaultState(), 12),
                        Biome.COUNT_RANGE,
                        new CountRangeConfig(20, 30, 40, 70) // Count, MinHeight, MaxHeightBase, MaxHeight
                )
        ));

    }
}
