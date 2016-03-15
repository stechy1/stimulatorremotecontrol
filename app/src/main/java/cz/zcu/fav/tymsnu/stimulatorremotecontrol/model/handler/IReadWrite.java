package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public interface IReadWrite {

    void write(FileOutputStream outputStream) throws IOException;

    void read(FileInputStream inputStream) throws IOException;
}
