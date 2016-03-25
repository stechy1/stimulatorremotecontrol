package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFvep;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.ConfigurationFileJSONHandler;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public class ConfigurationFactory implements IFactory<ConfigurationFvep> {

    private final IReadWrite handler = new ConfigurationFileJSONHandler();

    @Override
    public ConfigurationFvep build(String name) {
        return new ConfigurationFvep(name);
    }

    @Override
    public IReadWrite getReadWriteAcces() {
        return handler;
    }
}
