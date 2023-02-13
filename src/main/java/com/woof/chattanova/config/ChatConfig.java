package com.woof.chattanova.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ChatConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> locationX;
    public static final ForgeConfigSpec.ConfigValue<Integer> locationY;
    public static final ForgeConfigSpec.ConfigValue<Integer> chatWidth;
    public static final ForgeConfigSpec.ConfigValue<Integer> chatHeight;
    public static final ForgeConfigSpec.ConfigValue<Integer> storedMessages;
    static{
        BUILDER.push("Config for ChattaNova");

        locationX = BUILDER.comment("An integer defining the starting X position of the chat window").define("locationX", 300);
        locationY = BUILDER.comment("An integer defining the starting Y position of the chat window").define("locationY", 40);
        chatWidth = BUILDER.comment("An integer defining the width of the second chat window").define("chatWidth", 295);
        chatHeight = BUILDER.comment("An integer defining the height of the second chat window").define("chatHeight", 125);
        storedMessages = BUILDER.comment("An integer defining the number of messages to store").define("storedMessages", 100);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
