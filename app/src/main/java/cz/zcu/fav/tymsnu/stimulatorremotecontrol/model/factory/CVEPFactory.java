package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.json.CVEPHandler;


public class CVEPFactory implements IFactory<ConfigurationCVEP> {

    private final IReadWrite<ConfigurationCVEP> handler = new CVEPHandler();

    @Override
    public ConfigurationCVEP build(String name) {
        return new ConfigurationCVEP(name);
    }

    @Override
    public IReadWrite<ConfigurationCVEP> getReadWriteAcces() {
        return handler;
    }
}
