package de.madone.ocdtorcher.world.feature;

public class OreGenerationBiomeData {
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
