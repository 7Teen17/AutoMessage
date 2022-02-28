package com.github.seventeen.automessage;

import com.github.seventeen.automessage.command.CommandCreator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class AutoMessage implements ModInitializer {
    public static final String MOD_ID = "automessage";
    @Override
    public void onInitialize() {
        CommandCreator.createCommands();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            MessageHandler.startMessages();
        });
    }
}
