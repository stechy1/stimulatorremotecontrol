package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler;

import android.util.JsonWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Output;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;

public class SchemeJSONHandler implements IReadWrite {

    // region variables
    private static final String TAG_OUTPUT_COUNT = "output-count";
    private static final String TAG_EDGE = "edge";
    private static final String TAG_RANDOM = "random";
    private static final String TAG_OUTPUTS = "outputs";
    private static final String TAG_PULS = "puls";
    private static final String TAG_PULS_UP = "up";
    private static final String TAG_PULS_DOWN = "down";
    private static final String TAG_DISTRIBUTION = "distribution";
    private static final String TAG_DISTRIBUTION_VALUE = "value";
    private static final String TAG_DISTRIBUTION_DELAY = "delay";
    private static final String TAG_BRIGHTNESS = "brightness";
    // endregion

    private Scheme scheme = null;

    public SchemeJSONHandler() {}

    public SchemeJSONHandler(Scheme scheme) {
        this.scheme = scheme;
    }


    // region write
    @Override
    public void write(OutputStream outputStream) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream));
        writer.setIndent("  ");

        writer.beginObject();
        writer.name(TAG_OUTPUT_COUNT).value(scheme.getOutputCount());
        writer.name(TAG_EDGE).value(scheme.getEdge().ordinal());
        writer.name(TAG_RANDOM).value(scheme.getRandom().ordinal());

        writeOutputs(writer, scheme.getOutputList());
        writer.endObject();

        writer.close();
    }

    /**
     * Zapíše jeden výstup
     * @param w Reference na JSON writer
     * @param output Reference na výstup, který se má zapsat
     * @throws IOException
     */
    private void writeOutput(JsonWriter w, Output output) throws IOException {
        w.beginObject();

        w.name(TAG_PULS);
        w.beginObject();
        w.name(TAG_PULS_UP).value(output.puls.getUp());
        w.name(TAG_PULS_DOWN).value(output.puls.getDown());
        w.endObject();

        w.name(TAG_DISTRIBUTION);
        w.beginObject();
        w.name(TAG_DISTRIBUTION_VALUE).value(output.distribution.getValue());
        w.name(TAG_DISTRIBUTION_DELAY).value(output.distribution.getDelay());
        w.endObject();

        w.name(TAG_BRIGHTNESS).value(output.getBrightness());

        w.endObject();
    }
    /**
     * Zapíše všechny výstupy
     * @param w Reference na JSON writer
     * @param outputs Kolekce výstupů
     * @throws IOException
     */
    private void writeOutputs(JsonWriter w, List<Output> outputs) throws IOException {
        w.name(TAG_OUTPUTS);
        w.beginArray();

        for (Output output : outputs) {
            writeOutput(w, output);
        }

        w.endArray();
    }
    // endregion

    // region read
    @Override
    public void read(InputStream inputStream) throws IOException {

    }
    // endregion
}
