package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.SchemeFileJSONHandler;

public class SchemeFactory implements IFactory<Scheme> {

    private final IReadWrite handler = new SchemeFileJSONHandler();

    @Override
    public Scheme build(String name) {
        return new Scheme(name);
    }

    @Override
    public IReadWrite getReadWriteAcces() {
        return handler;
    }
}
