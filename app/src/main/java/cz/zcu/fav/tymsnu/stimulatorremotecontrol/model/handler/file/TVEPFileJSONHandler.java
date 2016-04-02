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

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public class TVEPFileJSONHandler implements IReadWrite<ConfigurationTVEP> {

    // region Variables
    private static final String TAG_OUTPUT_COUNT = "output-count";
    private static final String TAG_PATTERNS = "patterns";
    private static final String TAG_PATTERN_VALUE = "pattern_value";
    private static final String TAG_PATTERN_LENGHT = "pattern_lenght";
    private static final String TAG_PULS_SKEW = "puls_skew";
    private static final String TAG_PULS_LENGHT = "puls_lenght";
    private static final String TAG_BRIGHTNESS = "brightness";

    // region write
    @Override
    public void write(OutputStream outputStream, ConfigurationTVEP item) throws IOException {
        JsonWriter w = new JsonWriter(new OutputStreamWriter(outputStream));
        w.setIndent("  ");

        w.beginObject();
        w.name(TAG_OUTPUT_COUNT).value(item.getOutputCount());
        w.name(TAG_PATTERN_LENGHT).value(item.getPatternLength());
        w.name(TAG_PULS_SKEW).value(item.getPulsSkew());
        w.name(TAG_PULS_LENGHT).value(item.getPulsLength());
        w.name(TAG_BRIGHTNESS).value(item.getBrightness());

        writePatterns(w, item.getPatternList());
        w.endObject();

        w.close();
    }

    /**
     * zapíše všechny patterny
     * @param w JSON writer
     * @param patterns Kolekce patternů
     * @throws IOException
     */
    private void writePatterns(JsonWriter w, List<ConfigurationTVEP.Pattern> patterns) throws IOException{
        w.name(TAG_PATTERNS);
        w.beginArray();

        for(ConfigurationTVEP.Pattern a : patterns){
            w.beginObject();

            w.name(TAG_PATTERN_VALUE).value(a.getValue());

            w.endObject();
        }

        w.endArray();
    }

    // region read
    @Override
    public void read(InputStream inputStream, ConfigurationTVEP item) throws IOException {
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
            item.setPatternLength(itemObject.getInt(TAG_PATTERN_LENGHT));
            item.setPulsSkew(itemObject.getInt(TAG_PULS_SKEW));
            item.setPulsLength(itemObject.getInt(TAG_PULS_LENGHT));
            item.setBrightness(itemObject.getInt(TAG_BRIGHTNESS));

            JSONArray patternArray = itemObject.getJSONArray(TAG_PATTERNS);
            readPatterns(patternArray, item);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * přečte všechny patterny
     * @param patterns JSON kolekce patternů
     * @param c konfigurace do které se načítá
     * @throws JSONException
     */
    private void readPatterns(JSONArray patterns, ConfigurationTVEP c) throws JSONException{
        List<ConfigurationTVEP.Pattern> patternList = c.getPatternList();
        patternList.clear();

        for (int i = 0; i < patterns.length(); i++) {
            JSONObject patternObject = patterns.getJSONObject(i);
            patternList.add(new ConfigurationTVEP.Pattern(patternObject.getInt(TAG_PATTERN_VALUE)));
        }
    }


}
