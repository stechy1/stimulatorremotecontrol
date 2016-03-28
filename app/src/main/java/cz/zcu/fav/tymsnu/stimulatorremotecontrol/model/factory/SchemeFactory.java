package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.SchemeFileJSONHandler;

public class SchemeFactory implements IFactory<ConfigurationERP> {

    private final IReadWrite handler = new SchemeFileJSONHandler();

    @Override
    public ConfigurationERP build(String name) {
        return new ConfigurationERP(name);
    }

    @Override
    public IReadWrite getReadWriteAcces() {
        return handler;
    }
}
