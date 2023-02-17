package com.woof.chattanova.handlers;

import com.woof.chattanova.gui.SecondChatGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;

import java.util.ArrayList;
import java.util.List;


public class ServerChatHandler {

    private static List<ITextComponent> componentList = new ArrayList<>();
    private static List<ITextComponent> modifiedComponents = new ArrayList<>();
    private static int storedMessages;

    public static void addToComponentList(ITextComponent message){
        componentList.add(0, message);
        if(componentList.size() > storedMessages){
            componentList.remove(storedMessages);
        }
        modifiedComponents = modifyTextComponentList(SecondChatGui.getChatWidth()-5, componentList, Minecraft.getInstance().font);
    }

    public static List<ITextComponent> returnComponentList(){
        return componentList;
    }

    public static List<ITextComponent> returnModifiedComponentList(){
        return modifiedComponents;
    }

    public static List<ITextComponent> returnRecentModifiedHistory(double history){
        List<ITextComponent> returnList = new ArrayList<>();
        int converted = (int)history;
        int maxMessages = MathHelper.floor((SecondChatGui.getChatHeight() / 10) - 1);

        if(modifiedComponents.isEmpty()){
            return returnList;
        }

        for(int i = converted; i < modifiedComponents.size(); i++){
            returnList.add(modifiedComponents.get(i));
            if(i == converted + maxMessages){
                return returnList;
            }
        }
        return returnList;
    }


    private static List<ITextComponent> modifyTextComponentList(int maxWidth, List<ITextComponent> originalList, FontRenderer font) {
        List<ITextComponent> newList = new ArrayList<>();
        for(ITextComponent originalComponent : originalList){
            if(font.width(originalComponent) <= maxWidth){
                newList.add(originalComponent);
                continue;
            }
            // method that breaks component into its siblings and splits where necessary
            List<ITextComponent> splitList = splitComponent(maxWidth, originalComponent, font);
            newList.addAll(splitList);
        }
        return newList;
    }

    private static List<ITextComponent> splitComponent(int maxWidth, ITextComponent originalComponent, FontRenderer font){
        List<ITextComponent> siblings = originalComponent.getSiblings();
        List<ITextComponent> returnList = new ArrayList<>();
        if(siblings.isEmpty()){
            return new ArrayList<>(modifiedSplitter(maxWidth, originalComponent, font));
        }

        int runningWidth = 0;
        int finalChecker = 0;
        TextComponent storageComponent = new StringTextComponent("");
        for(ITextComponent sibling : siblings){
            finalChecker++;
            Style siblingStyle = sibling.getStyle();
            int siblingWidth = font.width(sibling);

            if(maxWidth - runningWidth < maxWidth * 0.2){
                returnList.add(0, storageComponent);
                storageComponent = new StringTextComponent("");
                runningWidth = 0;
            }

            if(runningWidth + siblingWidth <= maxWidth){
                runningWidth += siblingWidth;
                storageComponent.append(sibling); //this may need to have withStyle added
                if(finalChecker == siblings.size()){
                    returnList.add(0, storageComponent);
                }
                continue;
            }

            int remainingWidth = maxWidth - runningWidth;
            List<ITextComponent> storageList = new ArrayList<>(modifiedSplitter(remainingWidth, sibling, font));
            if(storageList.size() <= 1){
                storageComponent.append(storageList.get(0));
                returnList.add(0, storageComponent);
                storageComponent = new StringTextComponent("");
                runningWidth = 0;
                continue;
            }
            storageComponent.append(storageList.get(storageList.size()-1));
            returnList.add(0, storageComponent);
            storageComponent = new StringTextComponent("");
            runningWidth = 0;
            storageList.remove(storageList.size()-1);

            ITextComponent mergedComponent = listMerger(storageList, siblingStyle);
            if(font.width(mergedComponent) > maxWidth){
                storageList = modifiedSplitter(maxWidth, mergedComponent, font);
                for(int i = storageList.size()-1; i >= 1; i--){
                    returnList.add(0, storageList.get(i));
                }
                storageComponent.append(storageList.get(0));
                runningWidth += font.width(storageList.get(0));
            }else {
                storageComponent.append(mergedComponent);
                runningWidth = font.width(mergedComponent);
            }



            if(finalChecker == siblings.size()){
                returnList.add(0, storageComponent);
            }

        }
        return returnList;
    }

    private static List<ITextComponent> modifiedSplitter(int maxWidth, ITextComponent originalComponent, FontRenderer font){
        List<ITextComponent> returnList = new ArrayList<>();
        Style style = originalComponent.getStyle();
        List<ITextProperties> splitLines = font.getSplitter().splitLines(originalComponent, maxWidth, style);
        for(ITextProperties text : splitLines){
            String string = text.getString();
            TextComponent wrappedLine = new StringTextComponent("");
            wrappedLine.append(string).withStyle(style);
            returnList.add(0, wrappedLine);
        }
        return returnList;
    }

    private static ITextComponent listMerger(List<ITextComponent> list, Style style){
        TextComponent returnComponent = new StringTextComponent("");
        for(int i = list.size()-1; i >= 0; i--){
            returnComponent.append(list.get(i)).withStyle(style).append(" ");
        }
        return returnComponent;
    }

    public static void setStoredMessages(int messages){
        storedMessages = messages;
    }
}
