package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationREA;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public class REAFileJSONHandler implements IReadWrite<ConfigurationREA> {
    @Override
    public void write(OutputStream outputStream, ConfigurationREA item) throws IOException {

    }

    @Override
    public void read(InputStream inputStream, ConfigurationREA item) throws IOException {

    }
}
