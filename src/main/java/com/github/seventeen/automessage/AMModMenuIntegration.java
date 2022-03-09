package com.github.seventeen.automessage;


import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class AMModMenuIntegration implements ModMenuApi {
    
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("automessage.config.title"));

            builder.setSavingRunnable(() -> {
                //save to config file here

            });

            ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("automessage.config.category.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            final String[] currentValue = {null};
            general.addEntry(entryBuilder.startStrField(new TranslatableText("automessage.config.option.testoption"), currentValue[0])
                .setDefaultValue("Testing default value")
                .setTooltip(new TranslatableText("More explanation of test value"))
                .setSaveConsumer(newValue -> currentValue[0] = newValue)
                .build());

            return builder.build();
        };
    }
}