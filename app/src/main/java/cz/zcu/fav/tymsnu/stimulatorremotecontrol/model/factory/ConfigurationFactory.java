package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.FVEPFileJSONHandler;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public class ConfigurationFactory implements IFactory<ConfigurationFVEP> {

    private final IReadWrite<ConfigurationFVEP> handler = new FVEPFileJSONHandler();

    @Override
    public ConfigurationFVEP build(String name) {
        return new ConfigurationFVEP(name);
    }

    @Override
    public IReadWrite<ConfigurationFVEP> getReadWriteAcces() {
        return handler;
    }
}
