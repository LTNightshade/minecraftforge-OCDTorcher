package de.madone.ocdtorcher.gui;

import de.madone.ocdtorcher.container.ContainerOCDTorcher;
import de.madone.ocdtorcher.item.ItemOCDTorcher;
import de.madone.ocdtorcher.nimox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.network.FMLPlayMessages.OpenContainer;

import javax.annotation.Nullable;


public class ModGuiHandler implements IGuiHandler {

    public static ModGuiHandler INSTANCE;

    public enum EnumGuis implements IStringSerializable {
        TORCHER_GUI("torcher_gui", 0);

        final String name;
        final int index;

        EnumGuis(String name, int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }

        public static EnumGuis getByName(String s) {
            for (EnumGuis g : EnumGuis.values()) {
                if (s.equalsIgnoreCase(g.name))
                    return g;
            }
            return null;
        }

    }

    public static void Init() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> (OpenContainer oc) -> {
            if (nimox.ModId.equalsIgnoreCase(oc.getId().getNamespace())) {
                EnumGuis gui = EnumGuis.getByName(oc.getId().getPath());
                if (gui == null)
                    return null;
                EntityPlayerSP player = Minecraft.getInstance().player;
                ItemStack is = player.getHeldItemMainhand().getItem() instanceof ItemOCDTorcher ? player.getHeldItemMainhand() : player.getHeldItemOffhand();
                World world = Minecraft.getInstance().world;
                BlockPos pos = player.getPosition();
                if (oc.getAdditionalData().isReadable(8)) {
                    pos = oc.getAdditionalData().readBlockPos();
                }
                switch (gui) {
                    case TORCHER_GUI:
                        return new GuiContainerOCDTorcher(new ContainerOCDTorcher(player));
                }
            }
            return null;
        });
    }


    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (EnumGuis.values()[ID]) {
            case TORCHER_GUI:
                return new ContainerOCDTorcher(player);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (EnumGuis.values()[ID]) {
            case TORCHER_GUI:
                return new GuiContainerOCDTorcher((ContainerOCDTorcher) getServerGuiElement(ID, player, world, x, y, z));
        }
        return null;
    }
}
