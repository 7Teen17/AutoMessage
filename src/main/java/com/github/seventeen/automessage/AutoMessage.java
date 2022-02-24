package com.github.seventeen.automessage;

import com.github.seventeen.automessage.command.CommandCreator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;

public class AutoMessage implements ModInitializer {
    public static final String MOD_ID = "automessage";
    @Override
    public void onInitialize() {
        CommandCreator.createCommands();

    }
}
