package de.madone.ocdtorcher.item;

import de.madone.ocdtorcher.capability.CapabilityOCDTorcher;
import de.madone.ocdtorcher.container.ContainerOCDTorcher;
import de.madone.ocdtorcher.gui.ModGuiHandler;
import de.madone.ocdtorcher.network.client.CPacketOCDTorcher;
import de.madone.ocdtorcher.ocdtorcher;
import de.madone.ocdtorcher.stuff.InventoryHelper;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ItemOCDTorcher extends Item {

    private static final int Distance = 20;
    protected String name;

    public ItemOCDTorcher() {
        super(new Properties()
                .rarity(EnumRarity.RARE)
                .maxStackSize(1)
                .defaultMaxDamage(0)
                .setNoRepair()
                .group(ocdtorcher.ITEM_GROUP_OCDTORCHER)
        );
        this.name = "ocd_torcher";
        this.setRegistryName(name);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (stack.getItem() instanceof ItemOCDTorcher)
            return new CapabilityOCDTorcher.Provider();
        else
            return super.initCapabilities(stack, nbt);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking()) {
                ItemStack is = playerIn.getHeldItem(handIn);
                CapabilityOCDTorcher.ICapabilityOCDTorcher cap = is.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElse(null);
                if (cap != null) {
                    BlockPos pos = worldIn.rayTraceBlocks(playerIn.getEyePosition(0), playerIn.getLook(0)).getBlockPos();
                    if (pos != null) {
                        cap.SetOrigin(pos);
                        return ActionResult.newResult(EnumActionResult.SUCCESS, is);
                    }
                }
            } else {
                ItemStack is = playerIn.getHeldItem(handIn);

                CapabilityOCDTorcher.ICapabilityOCDTorcher cap = is.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElse(null);
                if (cap != null) {
                    IInteractionObject interactionObject = new IInteractionObject() {
                        @Override
                        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
                            return new ContainerOCDTorcher(playerIn);
                        }

                        @Override
                        public String getGuiID() {
                            return new ResourceLocation(ocdtorcher.ModId, ModGuiHandler.EnumGuis.TORCHER_GUI.getName()).toString();
                        }

                        @Override
                        public ITextComponent getName() {
                            return new TextComponentString("OCD-Torcher");
                        }

                        @Override
                        public boolean hasCustomName() {
                            return false;
                        }

                        @Nullable
                        @Override
                        public ITextComponent getCustomName() {
                            return null;
                        }
                    };
                    NetworkHooks.openGui((EntityPlayerMP) playerIn, interactionObject);
                    playerIn.displayGui(interactionObject);
                    return new ActionResult<>(EnumActionResult.SUCCESS, is);
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (worldIn.isRemote)
            return;

        long t = worldIn.getGameTime();
        if (t % 20 != 0)
            return;
        if(!(stack.getItem() instanceof ItemOCDTorcher))
            return;

        CapabilityOCDTorcher.ICapabilityOCDTorcher cap = stack.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElseThrow(NullPointerException::new);

        if (entityIn instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) entityIn;
            cap = stack.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElse(null);
            if (cap != null) {
                if ((stack == player.getHeldItem(EnumHand.MAIN_HAND)) | (stack == player.getHeldItem(EnumHand.OFF_HAND))) {
                    if (cap.GetPickupEnabled()) {
                        ItemOCDTorcher.PickupTorches(stack, worldIn, player.getPosition());
                    }
                    if (cap.GetEnabled()) {
                        ItemOCDTorcher.PlaceTorches(stack, worldIn, player.getPosition());
                    }
                }
            }
        }

    }

    private static void PlaceTorches(ItemStack stack, World worldIn, BlockPos position) {
        CapabilityOCDTorcher.ICapabilityOCDTorcher cap = stack.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElse(null);
        int level = cap.GetOrigin().getY();
        Iterable<BlockPos> area = BlockPos.getAllInBox(position.getX() - Distance, level, position.getZ() - Distance, position.getX() + Distance, level, position.getZ() + Distance);
        for (BlockPos p : area) {
            int w = Math.abs(p.getX() - cap.GetOrigin().getX());
            int h = Math.abs(p.getZ() - cap.GetOrigin().getZ());
            if (cap.GetPattern().PositionMatches(p, cap.GetOrigin())) {
                IBlockState bs = worldIn.getBlockState(p);
                if ((bs instanceof BlockTorch) | (!(bs.getBlock() instanceof BlockAir)))
                    continue;
                bs = worldIn.getBlockState(p.down());
                if ((bs.getBlock() instanceof BlockAir))
                    continue;
                if (bs.canPlaceTorchOnTop(worldIn, p.down())) {
                    ItemStack is = InventoryHelper.ExtractItem(new ItemStack(Item.BLOCK_TO_ITEM.get(Blocks.TORCH)), 1, cap.GetInventory());
                    if (!is.isEmpty()) {
                        worldIn.setBlockState(p, Blocks.TORCH.getDefaultState(), 3);
                        is = ItemStack.EMPTY;
                    }
                }
            }
        }
    }

    private static void PickupTorches(ItemStack stack, World worldIn, BlockPos position) {
        CapabilityOCDTorcher.ICapabilityOCDTorcher cap = stack.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElse(null);
        int level = cap.GetOrigin().getY();
        Iterable<BlockPos> area = null;
        if (level == -1) {
            area = BlockPos.getAllInBox(position.getX() - Distance, position.getY() - Distance, position.getZ() - Distance, position.getX() + Distance, position.getY() + Distance, position.getZ() + Distance);
        } else {
            area = BlockPos.getAllInBox(position.getX() - Distance, level, position.getZ() - Distance, position.getX() + Distance, level, position.getZ() + Distance);
        }
        for (BlockPos p : area) {
            int w = Math.abs(p.getX() - cap.GetOrigin().getX());
            int h = Math.abs(p.getZ() - cap.GetOrigin().getZ());
            if (!cap.GetPattern().PositionMatches(p, cap.GetOrigin())) {
                IBlockState bs = worldIn.getBlockState(p);
                if (bs instanceof BlockTorch) {
                    ItemStack is = InventoryHelper.InsertItem(new ItemStack(Item.BLOCK_TO_ITEM.get(Blocks.TORCH), 1), cap.GetInventory());
                    if (is.isEmpty()) {
                        worldIn.setBlockState(p, Blocks.AIR.getDefaultState(), 3);
                    } else {
                        worldIn.spawnEntity(new EntityItem(worldIn, p.getX(), p.getY(), p.getZ(), is));
                        worldIn.setBlockState(p, Blocks.AIR.getDefaultState(), 3);
                    }
                }
            }
        }
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Nullable
    @Override
    public NBTTagCompound getShareTag(ItemStack stack) {
        CapabilityOCDTorcher.ICapabilityOCDTorcher cap = stack.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElse(null);
        if ( cap != null) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("capability", cap.serializeNBT());
            return compound;
        }
        return null;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (nbt != null) {
            CapabilityOCDTorcher.ICapabilityOCDTorcher cap = stack.getCapability(CapabilityOCDTorcher.OCD_TORCHER_CAPABILITY).orElse(null);
            if (cap != null) {
               cap.deserializeNBT(nbt.getCompound("capability"));
            }
        }
    }
}
