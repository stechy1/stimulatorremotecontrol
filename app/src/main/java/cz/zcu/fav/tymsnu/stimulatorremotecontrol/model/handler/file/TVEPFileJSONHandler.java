package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public class TVEPFileJSONHandler implements IReadWrite<ConfigurationTVEP> {
    @Override
    public void write(OutputStream outputStream, ConfigurationTVEP item) throws IOException {

    }

    @Override
    public void read(InputStream inputStream, ConfigurationTVEP item) throws IOException {

    }
}
