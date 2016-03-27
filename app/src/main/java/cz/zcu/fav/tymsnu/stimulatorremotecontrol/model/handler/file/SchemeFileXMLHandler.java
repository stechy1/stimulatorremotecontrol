package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.file;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme.Output;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.IReadWriteScheme;

public class SchemeFileXMLHandler implements IReadWriteScheme {

    // region variables
    private static final String NAMESPACE = "";
    private static final String TAG_ROOT = "scheme";
    private static final String TAG_OUTPUT_COUNT = "output-count";
    private static final String TAG_EDGE = "edge";
    private static final String TAG_RANDOM = "random";
    private static final String TAG_OUTPUTS = "outputs";
    private static final String TAG_OUTPUT = "output";
    private static final String TAG_PULS = "puls";
    private static final String TAG_PULS_UP = "up";
    private static final String TAG_PULS_DOWN = "down";
    private static final String TAG_DISTRIBUTION = "distribution";
    private static final String TAG_DISTRIBUTION_VALUE = "value";
    private static final String TAG_DISTRIBUTION_DELAY = "delay";
    private static final String TAG_BRIGHTNESS = "brightness";
    // endregion


    public SchemeFileXMLHandler() {
    }

    // region write
    @Override
    public void write(OutputStream outputStream, Scheme scheme) throws IOException {
        XmlSerializer s = Xml.newSerializer();
        s.setOutput(outputStream, "UTF-8");
        s.startDocument("UTF-8", true);
        s.startTag(NAMESPACE, TAG_ROOT);

        // Počet výstupů
        writeValue(s, TAG_OUTPUT_COUNT, String.valueOf(scheme.getOutputCount()));
        // Typ hrany
        writeValue(s, TAG_EDGE, String.valueOf(scheme.getEdge().ordinal()));
        // Typ náhodnosti
        writeValue(s, TAG_RANDOM, String.valueOf(scheme.getRandom().ordinal()));

        // Zápis všech výstupů
        writeOutputs(s, scheme.getOutputList());

        s.endTag(NAMESPACE, TAG_ROOT);
        s.endDocument();

        outputStream.close();

    }

    /**
     * Zapíše hodnotu do XML s výchozím namespacem
     * @param s Reference na XML serializer
     * @param tag Tag, do kterého se má zapsat
     * @param val Hodnota, která se má zapsat
     * @throws IOException
     */
    private void writeValue(XmlSerializer s, String tag, String val) throws IOException {
        writeValue(s, NAMESPACE, tag, val);
    }
    /**
     * Zapíše hodnotu do XML
     * @param s Reference na XML serializer
     * @param namespace Jmenný prostor
     * @param tag Tag, do kterého se má zapsat
     * @param val Hodnota, která se má zapsat
     * @throws IOException
     */
    private void writeValue(XmlSerializer s, String namespace, String tag, String val) throws IOException {
        s.startTag(namespace, tag);
        s.text(val);
        s.endTag(namespace, tag);
    }

    /**
     * Zapíše jeden výstup do XML podoby
     * @param s Reference na XML serializer
     * @param output Reference na výstup, který se má zapsat do XML
     * @throws IOException
     */
    private void writeOutput(XmlSerializer s, Output output) throws IOException {
        s.startTag(NAMESPACE, TAG_OUTPUT);

        s.startTag(NAMESPACE, TAG_PULS);
        writeValue(s, TAG_PULS_UP, String.valueOf(output.puls.getUp()));
        writeValue(s, TAG_PULS_DOWN, String.valueOf(output.puls.getDown()));
        s.endTag(NAMESPACE, TAG_PULS);

        s.startTag(NAMESPACE, TAG_DISTRIBUTION);
        writeValue(s, TAG_DISTRIBUTION_VALUE, String.valueOf(output.distribution.getValue()));
        writeValue(s, TAG_DISTRIBUTION_DELAY, String .valueOf(output.distribution.getDelay()));
        s.endTag(NAMESPACE, TAG_DISTRIBUTION);

        writeValue(s, TAG_BRIGHTNESS, String.valueOf(output.getBrightness()));
        s.endTag(NAMESPACE, TAG_OUTPUT);
    }
    /**
     * Zapíše všechny výstupy do XML
     * @param s Reference na XML serializer
     * @param outputs Kolekce výstupů
     * @throws IOException
     */
    private void writeOutputs(XmlSerializer s, List<Output> outputs) throws IOException {
        s.startTag(NAMESPACE, TAG_OUTPUTS);

        for (Output output : outputs)
            writeOutput(s, output);

        s.endTag(NAMESPACE, TAG_OUTPUTS);
    }
    // endregion

    // region read
    @Override
    public void read(InputStream inputStream, Scheme scheme) throws IOException, XmlPullParserException {
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(inputStream, "UTF-8");

        int event = pullParser.getEventType();

        while (event != XmlPullParser.END_DOCUMENT) {
            String tag = null;
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    tag = pullParser.getName();
                    Log.i("handler", "Oteviram tag: " + tag);
                    switch (tag) {
                        case TAG_ROOT:
                            break;
                        case TAG_OUTPUT_COUNT:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_EDGE:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_RANDOM:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_OUTPUTS:
                            //readOutputs(pullParser, scheme);
                            Log.i("handler", "Narazil jsem na vystupy");

                            break;

                        case TAG_OUTPUT:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_PULS:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_PULS_UP:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_PULS_DOWN:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_DISTRIBUTION:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_DISTRIBUTION_VALUE:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_DISTRIBUTION_DELAY:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_BRIGHTNESS:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tag = pullParser.getName();
                    Log.i("handler", "Zaviram tag: " + tag);
                    break;
            }

            event = pullParser.next();
        }
    }

    private void readOutputs(XmlPullParser pullParser, Scheme scheme) throws IOException, XmlPullParserException {
        String tag = null;
        int event = pullParser.next();
        int stopCounter = 0;
        while (stopCounter < 500) {
            switch (event) {
                case XmlPullParser.START_TAG:
                    tag = pullParser.getName();
                    switch (tag) {
                        case TAG_OUTPUT:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_PULS:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_PULS_UP:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_PULS_DOWN:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_DISTRIBUTION:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_DISTRIBUTION_VALUE:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_DISTRIBUTION_DELAY:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                        case TAG_BRIGHTNESS:
                            Log.i("handler", "Hodnota: " + pullParser.nextText());
                            break;
                    }
                    break;

                case XmlPullParser.END_TAG:
                    tag = pullParser.getName();
                    if (tag.equals(TAG_OUTPUTS))
                        return;
                    break;
            }
            stopCounter++;
        }
    }
    // endregion

}
