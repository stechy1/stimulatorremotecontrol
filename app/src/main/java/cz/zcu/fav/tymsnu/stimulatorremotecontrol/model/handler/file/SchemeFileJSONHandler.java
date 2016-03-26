package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file;

import android.util.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Output;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWriteScheme;

public class SchemeFileJSONHandler implements IReadWriteScheme {

    // region variables
    private static final String TAG_OUTPUT_COUNT = "output-count";
    private static final String TAG_EDGE = "edge";
    private static final String TAG_RANDOM = "random";
    private static final String TAG_OUTPUTS = "outputs";
    private static final String TAG_OUTPUT_NAME = "name";
    private static final String TAG_PULS = "puls";
    private static final String TAG_PULS_UP = "up";
    private static final String TAG_PULS_DOWN = "down";
    private static final String TAG_DISTRIBUTION = "distribution";
    private static final String TAG_DISTRIBUTION_VALUE = "value";
    private static final String TAG_DISTRIBUTION_DELAY = "delay";
    private static final String TAG_BRIGHTNESS = "brightness";
    // endregion


    public SchemeFileJSONHandler() {}



    // region write
    @Override
    public void write(OutputStream outputStream, Scheme scheme) throws IOException {
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

        w.name(TAG_OUTPUT_NAME).value(output.getName());

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
    public void read(InputStream inputStream, Scheme scheme) throws IOException {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null)
            builder.append(line);
        reader.close();

        String src = builder.toString();

        try {
            JSONObject schemeObject = new JSONObject(src);

            scheme.setOutputCount(schemeObject.getInt(TAG_OUTPUT_COUNT));
            scheme.setEdge(Scheme.Edge.valueOf(schemeObject.getInt(TAG_EDGE)));
            scheme.setRandom(Scheme.Random.valueOf(schemeObject.getInt(TAG_RANDOM)));

            JSONArray outputArray = schemeObject.getJSONArray(TAG_OUTPUTS);
            readOutputs(outputArray, scheme);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void readOutputs(JSONArray outputs, Scheme scheme) throws JSONException {
        List<Output> outputList = scheme.getOutputList();
        outputList.clear();
        int length = outputs.length();
        for (int i = 0; i < length; i++) {
            JSONObject outputObject = outputs.getJSONObject(i);
            readOutput(outputObject, outputList);
        }
    }

    private void readOutput(JSONObject outputObject, List<Output> outputList) throws JSONException {
        String name = outputObject.getString(TAG_OUTPUT_NAME);

        JSONObject pulsObject = outputObject.getJSONObject(TAG_PULS);
        JSONObject distObject = outputObject.getJSONObject(TAG_DISTRIBUTION);

        int pulsUp = pulsObject.getInt(TAG_PULS_UP);
        int pulsDown = pulsObject.getInt(TAG_PULS_DOWN);
        Output.Puls puls = new Output.Puls(pulsUp, pulsDown);

        int distValue = distObject.getInt(TAG_DISTRIBUTION_VALUE);
        int distDelay = distObject.getInt(TAG_DISTRIBUTION_DELAY);
        Output.Distribution dist = new Output.Distribution(distValue, distDelay);

        int brightness = outputObject.getInt(TAG_BRIGHTNESS);

        Output output = new Output(name, puls, dist, brightness);
        outputList.add(output);
    }
    // endregion
}