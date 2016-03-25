package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AItem;

public interface IReadWrite {

    void write(OutputStream outputStream, AItem item) throws IOException;

    void read(InputStream inputStream, AItem item) throws IOException;

}
