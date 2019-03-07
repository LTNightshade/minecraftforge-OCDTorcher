package de.madone.ocdtorcher.config;

import com.google.common.collect.Lists;
import de.madone.ocdtorcher.ocdtorcher;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;

import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec spec = BUILDER.build();

    public static final General GENERAL = new General(BUILDER);

    public static class General {
        public final ForgeConfigSpec.ConfigValue<Boolean> ModEnabled;
        public final ForgeConfigSpec.ConfigValue<Integer> TorchDistance;

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            ModEnabled = builder
                    .comment("Enables/Disables the whole Mod [false/true|default:true]")
                    .translation("enable.ocdtorcher.config")
                    .define("enableMod", true);
            TorchDistance = builder
                    .comment("sets the Reach of the Torcher [0..50|default:20]")
                    .translation("distance.ocdtorcher.config")
                    .defineInRange("TorcherDistance", 20, 0, 50);
            builder.pop();
        }

    }




    public static void Init() {
        // load Configfile
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, de.madone.ocdtorcher.config.ModConfig.spec);

    }
}
