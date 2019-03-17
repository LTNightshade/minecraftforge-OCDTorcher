package de.madone.ocdtorcher.stuff;

import com.google.common.collect.AbstractIterator;
import com.sun.javafx.geom.Vec2d;
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
            if (((w - (width / 2)) % width == 0) & (h % height == 0)) {
                return true;
            }
        } else {
            if ((w % width == 0) & (h % height == 0)) {
                return true;
            }
        }
        return false;
    }

    public OCDTorcherPattern Copy() {
        return new OCDTorcherPattern(width, height, alternating);
    }

    public static Iterable<BlockPos> getAllinDistance(int distance, BlockPos origin, BlockPos position, OCDTorcherPattern pattern) {
        Vec2i offset = new Vec2i(position.getX() - origin.getX(), position.getZ() - origin.getZ());
        ;
        Vec2i min = new Vec2i(Math.min(offset.x - distance, offset.x + distance), Math.min(offset.y - distance, offset.y + distance));
        Vec2i max = new Vec2i(Math.max(offset.x - distance, offset.x + distance), Math.max(offset.y - distance, offset.y + distance));
        return () -> {
            return new AbstractIterator<BlockPos>() {
                private Vec2i pos;

                protected BlockPos computeNext() {
                    if (pos == null) {
                        pos = new Vec2i(min.x, min.y);
                        return new BlockPos(pos.x + origin.getX(), origin.getY(), pos.y + origin.getZ());
                    } else if (pos.x == max.x && pos.y == max.y) {
                        return endOfData();
                    } else {
                        if (pos.x < max.x) {
                            pos.x += pattern.width;
                        } else if (pos.y < max.y) {
                            if (Math.floorDiv(pos.y, 2) == 0)
                                pos.x = min.x;
                            else
                                pos.x = min.x + (pattern.width / 2);
                            pos.y += pattern.height;
                        }
                        return new BlockPos(pos.x + origin.getX(), origin.getY(), pos.y + origin.getZ());
                    }
                }
            };
        };
    }

}
