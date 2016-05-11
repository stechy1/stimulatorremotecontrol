package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.json.FVEPHandler;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public class ConfigurationFactory implements IFactory<ConfigurationFVEP> {

    private final IReadWrite<ConfigurationFVEP> handler = new FVEPHandler();

    @Override
    public ConfigurationFVEP build(String name) {
        return new ConfigurationFVEP(name);
    }

    @Override
    public IReadWrite<ConfigurationFVEP> getReadWriteAcces() {
        return handler;
    }
}
