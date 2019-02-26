package de.madone.ocdtorcher.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;

public class ModConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final Section2 SECTION_2 = new Section2(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

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
                    .defineInRange("TorcherDistance", 20, 0,50);
            builder.pop();
        }

    }

    public static class Section2 {
        public final ForgeConfigSpec.ConfigValue<Boolean> BoolVal2;
        public Section2(ForgeConfigSpec.Builder builder) {
            builder.push("section2");
            BoolVal2 = builder
                    .comment("Enables/Disables whatever [false/true|default:true]")
                    .translation("enable.sec2.ocdtorcher.config")
                    .define("ensec2", true);
        }
    }

    public static void Init() {
        // load Configfile
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, de.madone.ocdtorcher.config.ModConfig.spec);
    }
}
