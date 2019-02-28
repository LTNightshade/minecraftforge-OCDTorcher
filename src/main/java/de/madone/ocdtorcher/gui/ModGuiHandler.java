package de.madone.ocdtorcher.gui;

import de.madone.ocdtorcher.container.ContainerOCDTorcher;
import de.madone.ocdtorcher.item.ItemOCDTorcher;
import de.madone.ocdtorcher.ocdtorcher;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages.OpenContainer;


public class ModGuiHandler {

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

        @SuppressWarnings("NullableProblems")
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
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY,
                () -> (OpenContainer oc) -> {
                    if (ocdtorcher.ModId.equalsIgnoreCase(oc.getId().getNamespace())) {
                        EnumGuis gui = EnumGuis.getByName(oc.getId().getPath());
                        if (gui == null)
                            return null;
                        EntityPlayerSP player = Minecraft.getInstance().player;
                        switch (gui) {
                            case TORCHER_GUI:
                                return new GuiContainerOCDTorcher(new ContainerOCDTorcher(player));
                        }
                    }
                    return null;
                });
    }
}
