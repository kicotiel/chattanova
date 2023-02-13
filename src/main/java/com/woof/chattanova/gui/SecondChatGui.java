package com.woof.chattanova.gui;

import com.woof.chattanova.ChattaNovaMain;
import com.woof.chattanova.handlers.ServerChatHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ChattaNovaMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SecondChatGui extends AbstractGui {

    private static double historyPos = 0;
    private static boolean display = false;
    private static boolean displayPerm = false;
    private static int chatPosX;
    private static int chatPosY;
    private static int chatWidth;
    private static int chatHeight;

    @SubscribeEvent
    public static void onRenderGui(RenderGameOverlayEvent.Post event){
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        if(event.getType() == RenderGameOverlayEvent.ElementType.ALL && (display || displayPerm)){
            fill(event.getMatrixStack(), width - chatPosX, height - chatPosY, width - chatPosX + chatWidth, height - chatPosY - chatHeight, 0x7B000000);
            List<ITextComponent> textList = ServerChatHandler.returnRecentModifiedHistory(historyPos);

            if (textList.isEmpty()) {
                return;
            }

            for (int i = 0; i < textList.size(); i++) {
                int chatWidth = width - (chatPosX - 5);
                int chatHeight = (height - chatPosY - 10) - (10 * i);
                if(textList.get(i).getStyle().getColor() != null) {
                    Minecraft.getInstance().font.draw(event.getMatrixStack(), textList.get(i), chatWidth, chatHeight, textList.get(i).getStyle().getColor().getValue());
                } else{
                    Minecraft.getInstance().font.draw(event.getMatrixStack(), textList.get(i), chatWidth, chatHeight, 0xFFFFFFFF);
                }

            }
        }
    }

    public static void setHistoryPos(double delta){
        if(delta > 1.0){
            delta = 1.0;
        }
        if(delta < -1.0){
            delta = -1.0;
        }
        historyPos = MathHelper.clamp(historyPos+delta, 0, MathHelper.clamp( ServerChatHandler.returnModifiedComponentList().size() -
                (MathHelper.floor((chatHeight / 10) - 1)), 0 , 100));
    }

    public static void setHistoryPos(){
        historyPos = 0;
    }

    public static void setDisplay(boolean bool){
        display = bool;
    }

    public static void setDisplayPerm(boolean bool){
        displayPerm = bool;
    }

    public static void setChatPosX(int pos){
        chatPosX = pos;
    }

    public static void setChatPosY(int pos){
        chatPosY = pos;
    }

    public static void setChatWidth(int width){
        chatWidth = width;
    }

    public static void setChatHeight(int height){
        chatHeight = height;
    }

    public static int getChatWidth(){
        return chatWidth;
    }

    public static int getChatHeight(){
        return chatHeight;
    }
}
