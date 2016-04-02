package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file;

import android.util.JsonWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;


public class CVEPFileJSONHandler implements IReadWrite<ConfigurationCVEP> {

    // region Variables

    private static final String TAG_OUTPUT_COUNT = "output_count";
    private static final String TAG_PULSE_LENGHT = "pulse_lenght";
    private static final String TAG_BIT_SHIFT = "bit_shift"; //alias PULSE SKEW (
    private static final String TAG_BRIGHTNESS = "brightness";
    private static final String TAG_MAIN_PATTERN = "main_pattern";
    // endregion

    //region Write
    @Override
    public void write(OutputStream outputStream, ConfigurationCVEP item) throws IOException {
        JsonWriter w = new JsonWriter(new OutputStreamWriter(outputStream));
        w.setIndent("  ");

        w.beginObject();
        w.name(TAG_OUTPUT_COUNT).value(item.getOutputCount());

        w.beginObject();
        w.name(TAG_PULSE_LENGHT).value(item.getPulsLength());

        w.beginObject();
        w.name(TAG_BIT_SHIFT).value(item.getPulsSkew());

        w.beginObject();
        w.name(TAG_BRIGHTNESS).value(item.getBrightness());

        w.beginObject();
        w.name(TAG_MAIN_PATTERN).value(item.getMainPattern().getValue());
    }

    // region Read
    @Override
    public void read(InputStream inputStream, ConfigurationCVEP item) throws IOException {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null)
            builder.append(line);
        reader.close();

        String src = builder.toString();

        try {
            JSONObject itemObject = new JSONObject(src);

            item.setOutputCount(itemObject.getInt(TAG_OUTPUT_COUNT));
            item.setPulsLength(itemObject.getInt(TAG_PULSE_LENGHT));
            item.setPulsSkew(itemObject.getInt(TAG_BIT_SHIFT));
            item.setBrightness(itemObject.getInt(TAG_BRIGHTNESS));
            item.getMainPattern().setValue(itemObject.getInt(TAG_MAIN_PATTERN));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
