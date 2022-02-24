package com.github.seventeen.automessage.command;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;

public class CommandCreator {

    public static void createCommands() {
        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("automessage").executes(context -> {
                    if (MinecraftClient.getInstance().player == null) {
                        return 1;
                    }
                    MinecraftClient.getInstance().player.sendChatMessage("Testing123");
                    return 0;
                })
        );
    }
}
