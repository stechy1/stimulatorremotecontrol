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

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationREA;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWrite;

public class REAFileJSONHandler implements IReadWrite<ConfigurationREA> {

    //region Variables
    private static final String TAG_OUTPUT_COUNT = "output-count";
    private static final String TAG_CYCLE_COUNT = "cycle-count";
    private static final String TAG_WAIT_FIXED = "wait-fixed";
    private static final String TAG_WAIT_RANDOM = "wait-random";
    private static final String TAG_MISS_TIME = "miss-time";
    private static final String TAG_BRIGHTNESS = "brightness";
    private static final String TAG_ON_FAIL = "on_fail";
    private static final String TAG_M = "m";
    private static final String TAG_F = "f";
    private static final String TAG_A = "a";
    private static final String TAG_H = "h";
    private static final String TAG_W = "w";
    // endregion

    // region Write
    @Override
    public void write(OutputStream outputStream, ConfigurationREA item) throws IOException {
        JsonWriter w = new JsonWriter(new OutputStreamWriter(outputStream));
        w.setIndent("  ");

        w.beginObject();
        w.name(TAG_OUTPUT_COUNT).value(item.getOutputCount());
        w.name(TAG_CYCLE_COUNT).value(item.getCycleCount());
        w.name(TAG_WAIT_FIXED).value(item.getWaitFixed());
        w.name(TAG_WAIT_RANDOM).value(item.getWaitRandom());
        w.name(TAG_MISS_TIME).value(item.getMissTime());
        w.name(TAG_BRIGHTNESS).value(item.getBrightness());
        w.name(TAG_ON_FAIL).value(item.getOnFail());
        w.name(TAG_M).value(item.getM());
        w.name(TAG_F).value(item.getF());
        w.name(TAG_A).value(item.getA());
        w.name(TAG_H).value(item.getH());
        w.name(TAG_W).value(item.getW());
        w.endObject();

        w.close();
    }
    // endregion

    // region Read
    @Override
    public void read(InputStream inputStream, ConfigurationREA item) throws IOException {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null)
            builder.append(line);
        reader.close();

        String src = builder.toString();

        try{
            JSONObject itemObject = new JSONObject(src);
            item.setOutputCount(itemObject.getInt(TAG_OUTPUT_COUNT));
            item.setCycleCount(itemObject.getInt(TAG_CYCLE_COUNT));
            item.setWaitFixed(itemObject.getInt(TAG_WAIT_FIXED));
            item.setWaitRandom(itemObject.getInt(TAG_WAIT_RANDOM));
            item.setMissTime(itemObject.getInt(TAG_MISS_TIME));
            item.setBrightness(itemObject.getInt(TAG_BRIGHTNESS));
            item.setOnFail(itemObject.getInt(TAG_ON_FAIL));
            item.setM(itemObject.getBoolean(TAG_M));
            item.setF(itemObject.getBoolean(TAG_F));
            item.setA(itemObject.getInt(TAG_A));
            item.setH(itemObject.getInt(TAG_H));
            item.setW(itemObject.getInt(TAG_W));
        } catch(JSONException e){
            e.printStackTrace();
        }
    }
    // endregion

}
