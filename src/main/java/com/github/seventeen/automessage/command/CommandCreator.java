package com.github.seventeen.automessage.command;

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
    public static Map<String, List<Object>> messages = new HashMap<>();
        
    public static void createCommands() {
        ClientCommandManager.DISPATCHER.register(
                ClientCommandManager.literal("automessage").executes(ctx -> {
                    if (MinecraftClient.getInstance().player == null) {return 1;}

                    MinecraftClient.getInstance().player.sendChatMessage("/automessage <add|remove|start|stop|list>");
                    return 0;
                }).then(ClientCommandManager.literal("list").executes(ctx -> {
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("§c§lList of auto messages:"));
                    if (messages.isEmpty()) {
                        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("§aNone. Create one with /automessage add."));
                        return 0;
                    }
                    for (Map.Entry<String, List<Object>> entry : messages.entrySet()) {
                        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(entry.getKey()));
                    }
                    return 0;
                })).then(ClientCommandManager.literal("add")
                        .then(ClientCommandManager.argument("Id", StringArgumentType.string())
                        .then(ClientCommandManager.argument("Delay", IntegerArgumentType.integer())
                        .then(ClientCommandManager.argument("Content", StringArgumentType.greedyString())
                                .executes(ctx -> {
                                    if (StringArgumentType.getString(ctx, "Id").equalsIgnoreCase("all")) {
                                        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Cannot use 'all' as a message id."));
                                        return 1;
                                    }
                                    ArrayList<Object> list = new ArrayList<>();
                                    list.add(0);
                                    list.add(StringArgumentType.getString(ctx, "Content"));
                                    list.add(IntegerArgumentType.getInteger(ctx, "Delay"));
                                    list.add(false);
                                    messages.put(StringArgumentType.getString(ctx, "Id"), list);
                                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Created automessage with id " + StringArgumentType.getString(ctx, "Id")));
                                    return 0;
                        }))))
                .executes(context -> {
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("/automessage add <id> <delay> <content>"));
                    return 0;
                })).then(ClientCommandManager.literal("start")
                        .then(ClientCommandManager.argument("Id", StringArgumentType.string()).executes(ctx -> {
                            if (messages.get(StringArgumentType.getString(ctx, "Id")) == null) {
                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Automessage with id " + StringArgumentType.getString(ctx, "Id") + " not found."));
                                return 1;
                            }
                            if ((Boolean) messages.get(StringArgumentType.getString(ctx,"Id")).get(3)) {
                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("That automessage is already started."));
                                return 0;
                            }
                            messages.get(StringArgumentType.getString(ctx,"Id")).set(3, true);
                            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Started automessage with id " + StringArgumentType.getString(ctx, "Id")));
                            return 0;
                        }))).then(ClientCommandManager.literal("stop")
                        .then(ClientCommandManager.argument("Id", StringArgumentType.string()).executes(ctx -> {
                            if (messages.get(StringArgumentType.getString(ctx, "Id")) == null) {
                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Automessage with id " + StringArgumentType.getString(ctx, "Id") + " not found."));
                                return 1;
                            }
                            if (!((Boolean) messages.get(StringArgumentType.getString(ctx,"Id")).get(3))) {
                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("That automessage is already stopped."));
                                return 0;
                            }
                            messages.get(StringArgumentType.getString(ctx,"Id")).set(3, false);
                            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Stopped automessage with id " + StringArgumentType.getString(ctx, "Id")));
                            return 0;
                }))).then(ClientCommandManager.literal("remove")
                    .then(ClientCommandManager.argument("Id", StringArgumentType.string()).executes(ctx -> {
                        if (messages.get(StringArgumentType.getString(ctx, "Id")) == null) {
                            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Automessage with id " + StringArgumentType.getString(ctx, "Id") + " not found."));
                            return 1;
                        }
                        messages.remove(StringArgumentType.getString(ctx,"Id"));
                            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Removed automessage with id " + StringArgumentType.getString(ctx, "Id")));
                        return 0;
                }))).then(ClientCommandManager.literal("clear").executes(ctx -> {
                    messages.clear();
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Cleared all automessages."));
                    return 0;
                })).then(ClientCommandManager.literal("set")
                        .then(ClientCommandManager.literal("delay")
                                .then(ClientCommandManager.argument("Id", StringArgumentType.string())
                                .then(ClientCommandManager.argument("Delay", IntegerArgumentType.integer())
                                        .executes(ctx -> {
                                            if (messages.get(StringArgumentType.getString(ctx, "Id")) == null) {
                                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Automessage with id " + StringArgumentType.getString(ctx, "Id") + " not found."));
                                                return 1;
                                            }
                                            messages.get(StringArgumentType.getString(ctx, "Id")).set(2, IntegerArgumentType.getInteger(ctx, "Delay"));
                                            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Set delay of automessage with id " + StringArgumentType.getString(ctx, "Id") + " to " + IntegerArgumentType.getInteger(ctx, "Delay")));
                                            return 0;
                                        }))))
                        .then(ClientCommandManager.literal("content")
                                .then(ClientCommandManager.argument("Id", StringArgumentType.string())
                                        .then(ClientCommandManager.argument("Content", StringArgumentType.greedyString()).executes(ctx -> {
                                            if (messages.get(StringArgumentType.getString(ctx, "Id")) == null) {
                                                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Automessage with id " + StringArgumentType.getString(ctx, "Id") + " not found."));
                                                return 1;
                                            }
                                            messages.get(StringArgumentType.getString(ctx, "Id")).set(1, StringArgumentType.getString(ctx, "Content"));
                                            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Set content of automessage with id " + StringArgumentType.getString(ctx, "Id") + " to " + StringArgumentType.getString(ctx, "Content")));
                                            return 0;
                }))))).then(ClientCommandManager.literal("startall").executes(ctx -> {
                    for (Map.Entry<String, List<Object>> entry : messages.entrySet()) {
                        entry.getValue().set(3, true);
                    }
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Started all automessages."));
                    return 0;
                })).then(ClientCommandManager.literal("stopall").executes(ctx -> {
                    for (Map.Entry<String, List<Object>> entry : messages.entrySet()) {
                        entry.getValue().set(3, false);
                    }
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Stopped all automessages."));
                    return 0;
                }))
        );
    }
}
