package de.madone.ocdtorcher.capability;

import de.madone.ocdtorcher.ocdtorcher;
import de.madone.ocdtorcher.stuff.OCDTorcherPattern;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class CapabilityOCDTorcher {

    @CapabilityInject(ICapabilityOCDTorcher.class)
    public static final Capability<ICapabilityOCDTorcher> OCD_TORCHER_CAPABILITY = null;

    public static final ResourceLocation RLOC = new ResourceLocation(ocdtorcher.ModId, "ocd_torcher_capability");

    public interface ICapabilityOCDTorcher extends INBTSerializable<NBTTagCompound> {

        BlockPos GetOrigin();

        void SetOrigin(BlockPos pos);

        boolean GetEnabled();

        void SetEnabled(boolean state);

        boolean GetPickupEnabled();

        void SetPickupEnabled(boolean state);

        OCDTorcherPattern GetPattern();

        void SetPattern(OCDTorcherPattern pattern);

        IItemHandler GetInventory();

    }

    public static class Storage implements Capability.IStorage<ICapabilityOCDTorcher> {

        @Nullable
        @Override
        public INBTBase writeNBT(Capability<ICapabilityOCDTorcher> capability, ICapabilityOCDTorcher instance, EnumFacing side) {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<ICapabilityOCDTorcher> capability, ICapabilityOCDTorcher instance, EnumFacing side, INBTBase nbt) {
            if (nbt instanceof NBTTagCompound)
                instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

        private LazyOptional<ICapabilityOCDTorcher> holder = LazyOptional.of(() -> new CapabilityOCDTorcher().new Implementation());

        @Override
        public NBTTagCompound serializeNBT() {
            return holder.orElseThrow(NullPointerException::new).serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            holder.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
        }

        @Nonnull
        @SuppressWarnings("ConstantConditions")
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing side) {
            return CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY.orEmpty(cap, holder);
        }

    }

    public class Implementation implements ICapabilityOCDTorcher {

        private BlockPos origin = new BlockPos(0, 0, 0);
        private OCDTorcherPattern pattern = new OCDTorcherPattern(12, 6, true);
        private boolean enabled = true;
        private boolean pickUpEnabled = true;
        private ItemStackHandler itemHandler = new ItemStackHandler(4);

        @Override
        public BlockPos GetOrigin() {
            return this.origin;
        }

        @Override
        public void SetOrigin(BlockPos pos) {
            this.origin = pos;
        }

        @Override
        public boolean GetEnabled() {
            return this.enabled;
        }

        @Override
        public void SetEnabled(boolean state) {
            this.enabled = state;
        }

        @Override
        public boolean GetPickupEnabled() {
            return this.pickUpEnabled;
        }

        @Override
        public void SetPickupEnabled(boolean state) {
            this.pickUpEnabled = state;
        }

        @Override
        public OCDTorcherPattern GetPattern() {
            return this.pattern;
        }

        @Override
        public void SetPattern(OCDTorcherPattern pattern) {
            this.pattern = pattern;
        }

        @Override
        public IItemHandler GetInventory() {
            return this.itemHandler;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("inventory", itemHandler.serializeNBT());
            tag.setLong("origin", origin.toLong());
            tag.setBoolean("enabled", enabled);
            tag.setBoolean("pickUpEnabled", pickUpEnabled);
            tag.setTag("pattern", pattern.serializeNBT());
            return tag;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            itemHandler.deserializeNBT(nbt.getCompound("inventory"));
            pattern.deserializeNBT(nbt.getCompound("pattern"));
            this.origin = BlockPos.fromLong(nbt.getLong("origin"));
            this.enabled = nbt.getBoolean("enabled");
            this.pickUpEnabled = nbt.getBoolean("pickUpEnabled");
        }
    }

    public static class Factory implements Callable<ICapabilityOCDTorcher> {

        @Override
        public ICapabilityOCDTorcher call() {
            return new CapabilityOCDTorcher().new Implementation();
        }
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(ICapabilityOCDTorcher.class, new Storage(), new Factory());
    }
}
