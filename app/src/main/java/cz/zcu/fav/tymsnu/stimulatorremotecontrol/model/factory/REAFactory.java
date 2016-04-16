package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationREA;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.REAFileJSONHandler;

public class REAFactory implements IFactory<ConfigurationREA> {

    private final IReadWrite<ConfigurationREA> handler = new REAFileJSONHandler();

    @Override
    public ConfigurationREA build(String name) {
        return new ConfigurationREA(name);
    }

    @Override
    public IReadWrite<ConfigurationREA> getReadWriteAcces() {
        return handler;
    }
}
