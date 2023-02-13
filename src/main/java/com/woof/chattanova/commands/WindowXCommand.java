package com.woof.chattanova.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.woof.chattanova.config.ChatConfig;
import com.woof.chattanova.gui.SecondChatGui;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class WindowXCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher){
        LiteralArgumentBuilder<CommandSource> secondChatXCommand = Commands.literal("chattanova")
                .requires((commandSource) -> commandSource.hasPermission(0))
                .then(Commands.literal("setSecondChatX")
                    .then(Commands.argument("xPos", IntegerArgumentType.integer())
                        .executes((commandSource) -> setPosition(commandSource.getSource(), IntegerArgumentType.getInteger(commandSource, "xPos")))
                    )
                );

        dispatcher.register(secondChatXCommand);
    }

    static int setPosition(CommandSource commandSource, int xPos){
        ChatConfig.locationX.set(xPos);
        SecondChatGui.setChatPosX(xPos);

        commandSource.sendSuccess(new TranslationTextComponent("Set xPos to: " + xPos, xPos), false);
        return 1;
    }
}
