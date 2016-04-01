package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.CVEPFileJSONHandler;


public class CVEPFactory implements IFactory<ConfigurationCVEP> {

    private final IReadWrite<ConfigurationCVEP> handler = new CVEPFileJSONHandler();

    @Override
    public ConfigurationCVEP build(String name) {
        return new ConfigurationCVEP(name);
    }

    @Override
    public IReadWrite getReadWriteAcces() {
        return handler;
    }
}
