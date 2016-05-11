package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.json.ERPHandler;

public class ERPFactory implements IFactory<ConfigurationERP> {

    private final IReadWrite<ConfigurationERP> handler = new ERPHandler();

    @Override
    public ConfigurationERP build(String name) {
        return new ConfigurationERP(name);
    }

    @Override
    public IReadWrite<ConfigurationERP> getReadWriteAcces() {
        return handler;
    }
}
