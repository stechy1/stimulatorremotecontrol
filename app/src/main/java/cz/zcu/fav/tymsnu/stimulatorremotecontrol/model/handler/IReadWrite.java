package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IReadWrite {

    void write(OutputStream outputStream) throws IOException;

    void read(InputStream inputStream) throws IOException;
}
