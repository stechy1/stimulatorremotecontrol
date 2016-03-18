package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;

public interface IReadWriteScheme {

    void write(OutputStream outputStream, Scheme scheme) throws IOException;

    // region read
    void read(InputStream inputStream, Scheme scheme) throws IOException, XmlPullParserException;
}
