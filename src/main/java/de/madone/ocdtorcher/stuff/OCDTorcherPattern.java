package de.madone.ocdtorcher.stuff;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

public class OCDTorcherPattern implements INBTSerializable<NBTTagCompound> {
    private int width;
    private int height;
    private boolean alternating;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isAlternating() {
        return alternating;
    }

    public void setAlternating(boolean alternating) {
        this.alternating = alternating;
    }

    public OCDTorcherPattern(int width, int height, boolean alternating) {
        this.width = width;
        this.height = height;
        this.alternating = alternating;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInt("width", width);
        tag.setInt("height", height);
        tag.setBoolean("alternating", alternating);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.width = nbt.getInt("width");
        this.height = nbt.getInt("height");
        this.alternating = nbt.getBoolean("alternating");
    }

    public boolean PositionMatches(BlockPos p, BlockPos origin) {
        int w = p.getX() - origin.getX();
        int h = p.getZ() - origin.getZ();
        int row = w / width;
        int col = h / height;
        boolean odd = (row % 2) == 0;
        if (alternating & !odd) {
            if ( ((w-(width/2)) % width == 0) & (h % height == 0)) {
                return true;
            }
        } else {
            if ( (w % width == 0) & (h % height == 0)) {
                return true;
            }
        }
        return false;
    }

    public OCDTorcherPattern Copy() {
        return new OCDTorcherPattern(width, height, alternating);
    }
}
