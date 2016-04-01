package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;


public class CVEPFileJSONHandler implements IReadWrite<ConfigurationCVEP> {

    @Override
    public void write(OutputStream outputStream, ConfigurationCVEP item) throws IOException {

    }

    @Override
    public void read(InputStream inputStream, ConfigurationCVEP item) throws IOException {

    }
}
