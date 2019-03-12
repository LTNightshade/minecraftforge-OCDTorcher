package de.madone.ocdtorcher.world.feature;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.madone.ocdtorcher.ocdtorcher;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;

import static net.minecraftforge.common.BiomeManager.oceanBiomes;

public class ModOreFeature {

    protected static final Logger LOGGER = LogManager.getLogger();

    public static OreGeneration ORE_GENERATION;

    public static class OreGenerationBiomeData {
        public int minHeight;
        public int maxHeight;
        public int Count;
        public int Size = 12;

        public OreGenerationBiomeData(int minHeight, int maxHeight, int count, int size) {
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
            Count = count;
            Size = size;
        }
    }

    public static class OreGenerationOre {
        public HashMap<ResourceLocation, OreGenerationBiomeData> Biomes;

        public OreGenerationOre() {
            Biomes = new HashMap<>();
        }

        public OreGenerationOre(ResourceLocation oreName, OreGenerationBiomeData data) {
            Biomes = new HashMap<>();
            Biomes.put(oreName, data);
        }
    }

    public static class OreGeneration {
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

    public static void Init() {
        ReadJsonConfig(new ResourceLocation(ocdtorcher.ModId, "oregeneration/oregeneration.json"));
        SaveJsonConfig(new ResourceLocation(ocdtorcher.ModId, "oregeneration/oregeneration.json"));
        Register();
    }

    private static void Register() {
        ORE_GENERATION.Ores.forEach((k, v) -> {
            IBlockState state = ForgeRegistries.BLOCKS.getValue(k).getDefaultState();
            v.Biomes.forEach((bk, bv) -> {
                Biome biome = ForgeRegistries.BIOMES.getValue(bk);
                if (biome != null) {
                    if (!(bv.maxHeight == bv.minHeight || (bv.Count == 0))) {
                        biome.addFeature(
                                GenerationStage.Decoration.UNDERGROUND_ORES,
                                Biome.createCompositeFeature(
                                        Feature.MINABLE,
                                        new MinableConfig(MinableConfig.IS_ROCK, state, bv.Size),
                                        Biome.COUNT_RANGE,
                                        new CountRangeConfig(bv.Count, bv.minHeight, bv.minHeight, bv.maxHeight) // Count, MinHeight, MaxHeightBase, MaxHeight
                                ));
                    }
                }
            });
        });
    }

    private static void SaveJsonConfig(ResourceLocation location) {
        try {
            File f = new File(Minecraft.getInstance().gameDir + "/config/" + location.getNamespace() + "/" + location.getPath());
            File d = f.getParentFile();
            d.mkdirs();
            OutputStream os = new FileOutputStream(f);
            OutputStreamWriter writer = new OutputStreamWriter(os);
            BufferedWriter r = new BufferedWriter(writer);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .enableComplexMapKeySerialization()
                    .registerTypeAdapter(ResourceLocation.class, new ResourceLocationJsonSerializer())
                    .serializeNulls()
                    .create();
            String data = gson.toJson(ORE_GENERATION, OreGeneration.class);
            r.write(data);
            r.flush();
            r.close();
            writer.close();
            os.close();
        } catch (IOException ex) {
            LOGGER.error("can not save Ore Generation Data to configfile !");
        }
    }

    private static void ReadJsonConfig(ResourceLocation location) {
        try {
            InputStream is = new FileInputStream(Minecraft.getInstance().gameDir + "/config/" + location.getNamespace() + "/" + location.getPath());
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader r = new BufferedReader(reader);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .enableComplexMapKeySerialization()
                    .registerTypeAdapter(ResourceLocation.class, new ResourceLocationJsonSerializer())
                    .create();
            ORE_GENERATION = gson.fromJson(r, OreGeneration.class);
            if (ORE_GENERATION == null)
                ORE_GENERATION = new OreGeneration();
        } catch (IOException ex) {
            LOGGER.warn("Can not read Ore Generation config file - generation new one.");
            ORE_GENERATION = new OreGeneration();
        }
    }

    private static void registerOreOnAllBiomes(IBlockState state, int min, int max, int count) {
        //  int k = random.nextInt(placementConfig.maxHeight - placementConfig.maxHeightBase) + placementConfig.minHeight;

        ForgeRegistries.BIOMES.getValues().forEach((Biome b) -> {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    Biome.createCompositeFeature(
                            Feature.MINABLE,
                            new MinableConfig(MinableConfig.IS_ROCK, state, 12),
                            Biome.COUNT_RANGE,
                            new CountRangeConfig(count, min, min, max) // Count, MinHeight, MaxHeightBase, MaxHeight
                    ));
        });
    }
}
