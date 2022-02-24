package com.github.seventeen.automessage.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// {int id: ["text string", int delayBetween, bool Enabled]}

public class CommandCreator {
    private Map<Integer, List> messages = new ArrayList<>();
    
    //maps the user inputted string id to the synctask int id
    //idConversion.add(id, syncdelayedtask())
    private Map<String, Integer> idConversion = new ArrayList<>();
        
    public static void createCommands() {
        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("automessage").executes(context -> {
                    if (MinecraftClient.getInstance().player == null) {
                        return 1;
                    }
                    MinecraftClient.getInstance().player.sendChatMessage("Testing123");
                    return 0;
                }).then(ClientCommandManager.literal("list").executes(context -> {
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("§c§lList of auto messages:"));
                    return 0;
                })).then(ClientCommandManager.literal("add")
                        .then(ClientCommandManager.argument("Id", StringArgumentType.string())
                        .then(ClientCommandManager.argument("Delay", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("Content", StringArgumentType.greedyString())
                                .executes(context -> {
                                    // json.put(id, [delay, content, enabled])
                                }))
                        .executes(context -> {
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("/automessage add <id> <delay> <content>"));
                    return 0;
                }))
        );
    }
}
