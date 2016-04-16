package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfiguration;

public interface IReadWrite<T extends AConfiguration<T>> {

    void write(OutputStream outputStream, T item) throws IOException;

    void read(InputStream inputStream, T item) throws IOException;

}
