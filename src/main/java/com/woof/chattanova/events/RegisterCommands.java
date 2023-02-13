package com.woof.chattanova.events;

import com.mojang.brigadier.CommandDispatcher;
import com.woof.chattanova.ChattaNovaMain;
import com.woof.chattanova.commands.WindowXCommand;
import com.woof.chattanova.commands.WindowYCommand;
import net.minecraft.command.CommandSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ChattaNovaMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RegisterCommands {

    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event){
        CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
        // add commands below via Class.register(commandDispatcher);
        WindowXCommand.register(commandDispatcher);
        WindowYCommand.register(commandDispatcher);
    }
}
