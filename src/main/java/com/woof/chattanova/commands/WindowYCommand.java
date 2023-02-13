package com.woof.chattanova.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.woof.chattanova.config.ChatConfig;
import com.woof.chattanova.gui.SecondChatGui;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class WindowYCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher){
        LiteralArgumentBuilder<CommandSource> secondChatYCommand = Commands.literal("chattanova")
                .requires((commandSource) -> commandSource.hasPermission(0))
                .then(Commands.literal("setSecondChatY")
                    .then(Commands.argument("yPos", IntegerArgumentType.integer())
                        .executes((commandSource) -> setPosition(commandSource.getSource(), IntegerArgumentType.getInteger(commandSource, "yPos")))
                    )
                );

        dispatcher.register(secondChatYCommand);
    }

    static int setPosition(CommandSource commandSource, int yPos){
        ChatConfig.locationX.set(yPos);
        SecondChatGui.setChatPosY(yPos);

        commandSource.sendSuccess(new TranslationTextComponent("Set yPos to: " + yPos, yPos), false);
        return 1;
    }
}
