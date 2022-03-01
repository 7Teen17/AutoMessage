package com.github.seventeen.automessage;

import com.github.seventeen.automessage.command.CommandCreator;
import net.minecraft.client.MinecraftClient;

import java.util.List;
import java.util.Map;

public class MessageHandler {
    public static void startMessages() {
        if (MinecraftClient.getInstance().player == null) {return;}
        for (Map.Entry<String, List<Object>> entry : CommandCreator.messages.entrySet()) {
            entry.getValue().set(0, (Integer) entry.getValue().get(0) + 1);
            if ((Integer) entry.getValue().get(0) >= (Integer) entry.getValue().get(2)) {
                entry.getValue().set(0, 0);
                if ((Boolean) entry.getValue().get(3)) {
                    MinecraftClient.getInstance().player.sendChatMessage((String) entry.getValue().get(1));
                }
            }
        }
    }
}
