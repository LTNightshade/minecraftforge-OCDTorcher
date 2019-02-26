package de.madone.ocdtorcher;

import de.madone.ocdtorcher.item.ModItems;
import de.madone.ocdtorcher.registry.ModRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ocdtorcher.ModId)
public class ocdtorcher {

    public static final String ModId = "ocdtorcher";

    // Directly reference a log4j logger.
    protected static final Logger LOGGER = LogManager.getLogger();

    protected static ModRegistry REGISTRY;

    public static final ItemGroup ITEM_GROUP_OCDTORCHER = new ItemGroup("OCD-Torcher") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ModItems.ITEM_OCD_TORCHER);
        }
    }.setBackgroundImageName("ocdtorchertab.png");

    public ocdtorcher() {
        REGISTRY = new ModRegistry();
    }

}
