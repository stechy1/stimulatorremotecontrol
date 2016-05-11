package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file.json;

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

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public class FVEPHandler implements IReadWrite<ConfigurationFVEP> {

    // region Variables
    private static final String TAG_OUTPUT_COUNT = "output-count";
    private static final String TAG_OUTPUTS = "outputs";
    private static final String TAG_OUTPUT_NAME = "name";
    private static final String TAG_PULS = "puls";
    private static final String TAG_PULS_UP = "up";
    private static final String TAG_PULS_DOWN = "down";
    private static final String TAG_FREQUENCY = "frequency";
    private static final String TAG_DUTY_CYCLE = "duty-cycle";
    private static final String TAG_BRIGHTNESS = "brightness";
    // endregion

    // region Write
    @Override
    public void write(OutputStream outputStream, ConfigurationFVEP configuration) throws IOException {
        JsonWriter w = new JsonWriter(new OutputStreamWriter(outputStream));
        w.setIndent("  ");

        w.beginObject();
        w.name(TAG_OUTPUT_COUNT).value(configuration.getOutputCount());

        writeOutputs(w, configuration.outputList);
        w.endObject();

        w.close();

    }

    /**
     * Zapíše jeden výstup
     * @param w Reference na JSON writer
     * @param output Reference na výstup, který se má zapsat
     * @throws IOException
     */
    private void writeOutput(JsonWriter w, ConfigurationFVEP.Output output) throws IOException {
        w.beginObject();

        w.name(TAG_PULS);
        w.beginObject();
        w.name(TAG_PULS_UP).value(output.puls.getUp());
        w.name(TAG_PULS_DOWN).value(output.puls.getDown());
        w.endObject();

        w.name(TAG_FREQUENCY).value(output.getFrequency());
        w.name(TAG_DUTY_CYCLE).value(output.getDutyCycle());
        w.name(TAG_BRIGHTNESS).value(output.getBrightness());

        w.endObject();
    }
    /**
     * Zapíše všechny výstupy
     * @param w Reference na JSON writer
     * @param outputs Kolekce výstupů
     * @throws IOException
     */
    private void writeOutputs(JsonWriter w, List<ConfigurationFVEP.Output> outputs) throws IOException {
        w.name(TAG_OUTPUTS);
        w.beginArray();

        for (ConfigurationFVEP.Output output : outputs) {
            writeOutput(w, output);
        }

        w.endArray();
    }
    // endregion

    // region Read
    @Override
    public void read(InputStream inputStream, ConfigurationFVEP configuration) throws IOException {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null)
            builder.append(line);
        reader.close();

        String src = builder.toString();

        try {
            JSONObject itemObject = new JSONObject(src);

            configuration.setOutputCount(itemObject.getInt(TAG_OUTPUT_COUNT));

            JSONArray outputArray = itemObject.getJSONArray(TAG_OUTPUTS);
            readOutputs(outputArray, configuration);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void readOutput(JSONObject outputObject, List<ConfigurationFVEP.Output> outputs) throws JSONException {
        JSONObject pulsObject = outputObject.getJSONObject(TAG_PULS);

        int pulsUp = pulsObject.getInt(TAG_PULS_UP);
        int pulsDown = pulsObject.getInt(TAG_PULS_DOWN);
        ConfigurationFVEP.Puls puls = new ConfigurationFVEP.Puls(pulsUp, pulsDown);

        int frequency = outputObject.getInt(TAG_FREQUENCY);
        int dutyCycle = outputObject.getInt(TAG_DUTY_CYCLE);
        int brightness = outputObject.getInt(TAG_BRIGHTNESS);

        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output(puls, frequency, dutyCycle, brightness);
        outputs.add(output);
    }
    private void readOutputs(JSONArray outputs, ConfigurationFVEP c) throws JSONException {
        List<ConfigurationFVEP.Output> outputList = c.outputList;
        outputList.clear();
        int length = outputs.length();
        for (int i = 0; i < length; i++) {
            JSONObject outputObject = outputs.getJSONObject(i);
            readOutput(outputObject, outputList);
        }
    }
    // endregion
}
