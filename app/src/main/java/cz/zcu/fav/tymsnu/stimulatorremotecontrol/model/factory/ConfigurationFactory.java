package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Configuration;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.ConfigurationJSONHandler;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public class ConfigurationFactory implements IFactory<Configuration> {

    private final IReadWrite handler = new ConfigurationJSONHandler();

    @Override
    public Configuration build(String name) {
        return new Configuration(name);
    }

    @Override
    public IReadWrite getReadWriteAcces() {
        return handler;
    }
}
