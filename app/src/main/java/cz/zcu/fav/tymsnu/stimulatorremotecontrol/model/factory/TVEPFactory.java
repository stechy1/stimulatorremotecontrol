package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.TVEPFileJSONHandler;


public class TVEPFactory implements IFactory<ConfigurationTVEP> {

    private IReadWrite<ConfigurationTVEP> handler = new TVEPFileJSONHandler();

    @Override
    public ConfigurationTVEP build(String name) {
        return new ConfigurationTVEP(name);
    }

    @Override
    public IReadWrite getReadWriteAcces() {
        return handler;
    }
}
