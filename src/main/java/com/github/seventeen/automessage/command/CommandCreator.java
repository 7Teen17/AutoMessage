package com.github.seventeen.automessage.command;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// {int id: [int taskId, "text string", int delayBetween, bool Enabled]}

public class CommandCreator {
    private Map<Integer, List> messages = new HashMap<>();
        
    public static void createCommands() {
        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("automessage").executes(ctx -> {
                    if (MinecraftClient.getInstance().player == null) {return 1;}

                    MinecraftClient.getInstance().player.sendChatMessage("/automessage <add|remove|start|stop|list>");
                    return 0;
                }).then(ClientCommandManager.literal("list").executes(ctx -> {
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("§c§lList of auto messages:"));
                    return 0;
                })).then(ClientCommandManager.literal("add")
                        .then(ClientCommandManager.argument("Id", StringArgumentType.string()))
                        .then(ClientCommandManager.argument("Delay", IntegerArgumentType.integer()))
                        .then(ClientCommandManager.argument("Content", StringArgumentType.greedyString())
                                .executes(ctx -> {
                                    //add a check for "all" id so that start command works
                                    ArrayList<Object> list = new ArrayList<>();
                                    list.add(null);
                                    list.add(StringArgumentType.getString(ctx, "Content"));
                                    list.add(IntegerArgumentType.getInteger(ctx, "Delay"));
                                    list.add(true);
                                    return 0;
                        }))
                .executes(context -> {
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("/automessage add <id> <delay> <content>"));
                    return 0;
                })).then(ClientCommandManager.literal("start")
                        .then(ClientCommandManager.argument("Id", StringArgumentType.string())).executes(ctx -> {
                            //repeating task generation here
                            return 0;
                })).then(ClientCommandManager.literal("stop")
                        .then(ClientCommandManager.argument("Id", StringArgumentType.string())).executes(ctx -> {
                            // stop code here
                            return 0;
                        }))
        );
    }
}
