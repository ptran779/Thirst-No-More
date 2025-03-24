package com.github.ptran779.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ThirstNomoreConfigs {
    public static ForgeConfigSpec CONFIG;
    // Common
    public static ForgeConfigSpec.ConfigValue<Integer> MAX_USAGE_BOTTLE_STRAP;
    public static ForgeConfigSpec.ConfigValue<Integer> MAX_USAGE_CAMEL_PACK;
    public static ForgeConfigSpec.ConfigValue<Integer> PURITY_MINIMUM;
    public static ForgeConfigSpec.ConfigValue<Integer> THIRST_RESTORE_PER_DRINK;
    public static ForgeConfigSpec.ConfigValue<Integer> QUENCH_RESTORE_PER_DRINK;
    public static ForgeConfigSpec.ConfigValue<Integer> TICKS_PER_DRINK_CHECK;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        BUILDER.comment("Server Config").push("Server CONFIG");
        MAX_USAGE_BOTTLE_STRAP = BUILDER.comment("How many quench can bottle_strap hold?")
                        .defineInRange("Bottle Strap quench max", 10, 1, Integer.MAX_VALUE);
        MAX_USAGE_CAMEL_PACK = BUILDER.comment("How many quench can camel_pack hold?")
            .defineInRange("Bottle Strap quench max", 20, 1, Integer.MAX_VALUE);
        PURITY_MINIMUM = BUILDER.comment("What is the minimum level of purity does the container (ex:minecraft:potion) need to refill the water container?")
            .defineInRange("Purity Level Min", 3, 0,3);
        THIRST_RESTORE_PER_DRINK = BUILDER.comment("How many thirst value does each drink restore?")
            .defineInRange("Thirst restore per drink", 4, 0, 20);
        QUENCH_RESTORE_PER_DRINK = BUILDER.comment("How many Quench value does each drink restore? (this is the " +
                "\"saturation\" value for thirst)")
            .defineInRange("Quench restore per drink", 2, 0, 20);
        TICKS_PER_DRINK_CHECK = BUILDER.comment("How many tick before system check for water needed? (20 ticks == 1s) " +
                "(leave this alone if you dont know what you doing). Can be performance sensitive")
            .defineInRange("Tick check period", 200, 1, Integer.MAX_VALUE);
        BUILDER.pop();

        CONFIG = BUILDER.build();
    }
}
