package com.woof.chattanova.events;


import com.woof.chattanova.ChattaNovaMain;
import com.woof.chattanova.config.ChatConfig;
import com.woof.chattanova.gui.SecondChatGui;
import com.woof.chattanova.handlers.ServerChatHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = ChattaNovaMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onConfigLoad(ModConfig.Loading event){
        if (event.getConfig().getModId().equals(ChattaNovaMain.MOD_ID)){
            SecondChatGui.setChatPosX(ChatConfig.locationX.get());
            SecondChatGui.setChatPosY(ChatConfig.locationY.get());
            SecondChatGui.setChatHeight(ChatConfig.chatHeight.get());
            SecondChatGui.setChatWidth(ChatConfig.chatWidth.get());
            ServerChatHandler.setStoredMessages(ChatConfig.storedMessages.get());
            ChattaNovaMain.LOGGER.info("ChattaNova config was loaded! :D");
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfig.Reloading event){
        if (event.getConfig().getModId().equals(ChattaNovaMain.MOD_ID)) {
            SecondChatGui.setChatPosX(ChatConfig.locationX.get());
            SecondChatGui.setChatPosY(ChatConfig.locationY.get());
            SecondChatGui.setChatHeight(ChatConfig.chatHeight.get());
            SecondChatGui.setChatWidth(ChatConfig.chatWidth.get());
            ServerChatHandler.setStoredMessages(ChatConfig.storedMessages.get());
        }
    }


    @SubscribeEvent
    public static void onConfigChange(ModConfig.ModConfigEvent event){
        if (event.getConfig().getModId().equals(ChattaNovaMain.MOD_ID)){
            SecondChatGui.setChatPosX(ChatConfig.locationX.get());
            SecondChatGui.setChatPosY(ChatConfig.locationY.get());
            SecondChatGui.setChatHeight(ChatConfig.chatHeight.get());
            SecondChatGui.setChatWidth(ChatConfig.chatWidth.get());
            ServerChatHandler.setStoredMessages(ChatConfig.storedMessages.get());
        }
    }


}
