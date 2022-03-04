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
                continue;
            })

            ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("automessage.config.category.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startStrField(new TranslatableText("automessage.config.option.testoption"), currentValue)
                .setDefaultValue("Testing default value")
                .setTooltip(new TranslatableText("More explanation of test value"))
                .setSaveConsumer(newValue -> currentValue = newValue)
                .build())

            Screen screen = builder.build();
            return screen;
        }
    }
}