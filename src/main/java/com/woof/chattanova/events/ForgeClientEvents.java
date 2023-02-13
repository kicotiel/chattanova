package com.woof.chattanova.events;

import com.woof.chattanova.ChattaNovaMain;
import com.woof.chattanova.gui.SecondChatGui;
import com.woof.chattanova.handlers.ServerChatHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChattaNovaMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {

    public static int timeActive;

    @SubscribeEvent
    public static void testReceived(ClientChatReceivedEvent event){
        ITextComponent message = event.getMessage();
        String str = message.getString();
        if(str.contains("[Social-Spy]") || str.contains("[SocialSpy]") ){
            ServerChatHandler.addToComponentList(message);
            if(event.isCancelable()){
                event.setCanceled(true);
            }
        }

        timeActive = 380;
        SecondChatGui.setDisplay(true);
    }

    @SubscribeEvent
    public static void onScroll(GuiScreenEvent.MouseScrollEvent event){
        if(!event.getGui().isPauseScreen()){
            SecondChatGui.setHistoryPos(event.getScrollDelta());
        }
    }

    @SubscribeEvent
    public static void onEscape(GuiScreenEvent.KeyboardKeyPressedEvent event) {
        if(!event.getGui().isPauseScreen() && (event.getKeyCode() == 256 || event.getKeyCode() == 257)){
            SecondChatGui.setHistoryPos();
            timeActive = 380;
        }

    }

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event){
        if(timeActive > 0){
            timeActive--;
        }
        if(timeActive == 0){
            SecondChatGui.setDisplay(false);
        }
        if(Minecraft.getInstance().screen instanceof ChatScreen){
            SecondChatGui.setDisplayPerm(true);
        } else{
            SecondChatGui.setDisplayPerm(false);
        }
    }

}
