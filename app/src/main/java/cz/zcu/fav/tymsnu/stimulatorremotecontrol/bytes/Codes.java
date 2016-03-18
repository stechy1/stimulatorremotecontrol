package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

/**
 * Třída reprezentující komunikační protokol,
 * některé hodnoty mají následovníky, pokud se stejný parametr nastavuje více výstupům
 */
public final class Codes {

    public static final Code REFRESH = new Code((byte) 0x00, "RERESH");
    public static final Code OUTPUT_ON = new Code((byte) 0x01, "OUTPUT_ON");
    public static final Code OUTPUT_OFF = new Code((byte) 0x02, "RERESH");
    public static final Code GET_OUTPUT_STATUS = new Code((byte) 0x03, "GET_OUTPUT_STATUS");
    public static final Code RANDOMNESS_ON = new Code((byte) 0x04, "RANDOMNESS_ON"); //TODO nejasnosti ohledně posílání parametru RANDOM 0-4
    public static final Code RANDOMNESS_OFF = new Code((byte) 0x05, "RANDOMNESS_OFF");
    public static final Code GET_RANDOMNESS_STATUS = new Code((byte) 0x06, "GET_RANDOMNESS_STATUS");
    public static final Code EDGE = new Code((byte) 0x21, "EDGE");

    public static final Code OUTPUT7_DURATION = new Code((byte) 0x32, "OUTPUT7_DURATION");
    public static final Code OUTPUT7_PAUSE = new Code((byte) 0x33, "OUTPUT7_PAUSE");
    public static final Code OUTPUT7_DISTRIBUTION = new Code((byte) 0x37, "OUTPUT7_DISTRIBUTION");
    public static final Code OUTPUT7_BRIGHTNESS = new Code((byte) 0x3B, "OUTPUT7_BRIGHTNESS"); //TODO nejasný kód
    public static final Code OUTPUT7_FREQ = new Code((byte) 0x40, "OUTPUT7_FREQ");              //TODO nejasnost
    public static final Code OUTPUT7_MIDDLE_PERIOD = new Code((byte) 0x41, "OUTPUT7_MIDDLE_PERIOD"); //TODO nejasnost

    public static final Code OUTPUT6_DURATION = new Code((byte) 0x30, "OUTPUT6_DURATION", OUTPUT7_DURATION);
    public static final Code OUTPUT6_PAUSE = new Code((byte) 0x31, "OUTPUT6_PAUSE", OUTPUT7_PAUSE);
    public static final Code OUTPUT6_DISTRIBUTION = new Code((byte) 0x36, "OUTPUT6_DISTRIBUTION", OUTPUT7_DISTRIBUTION);
    public static final Code OUTPUT6_BRIGHTNESS = new Code((byte) 0x3A, "OUTPUT6_BRIGHTNESS", OUTPUT7_BRIGHTNESS); //TODO nejasný kód
    public static final Code OUTPUT6_FREQ = new Code((byte) 0x3E, "OUTPUT6_FREQ", OUTPUT7_FREQ);              //TODO nejasnost
    public static final Code OUTPUT6_MIDDLE_PERIOD = new Code((byte) 0x3F, "OUTPUT6_MIDDLE_PERIOD", OUTPUT7_MIDDLE_PERIOD); //TODO nejasnost

    public static final Code OUTPUT5_DURATION = new Code((byte) 0x2E, "OUTPUT5_DURATION", OUTPUT6_DURATION);
    public static final Code OUTPUT5_PAUSE = new Code((byte) 0x2F, "OUTPUT5_PAUSE", OUTPUT6_PAUSE);
    public static final Code OUTPUT5_DISTRIBUTION = new Code((byte) 0x35, "OUTPUT5_DISTRIBUTION", OUTPUT6_DISTRIBUTION);
    public static final Code OUTPUT5_BRIGHTNESS = new Code((byte) 0x39, "OUTPUT5_BRIGHTNESS", OUTPUT6_BRIGHTNESS); //TODO nejasný kód
    public static final Code OUTPUT5_FREQ = new Code((byte) 0x3C, "OUTPUT5_FREQ", OUTPUT6_FREQ);              //TODO nejasnost
    public static final Code OUTPUT5_MIDDLE_PERIOD = new Code((byte) 0x3D, "OUTPUT5_MIDDLE_PERIOD", OUTPUT6_MIDDLE_PERIOD); //TODO nejasnost

    public static final Code OUTPUT4_DURATION = new Code((byte) 0x2C, "OUTPUT4_DURATION", OUTPUT5_DURATION);
    public static final Code OUTPUT4_PAUSE = new Code((byte) 0x2D, "OUTPUT4_PAUSE", OUTPUT5_PAUSE);
    public static final Code OUTPUT4_DISTRIBUTION = new Code((byte) 0x34, "OUTPUT4_DISTRIBUTION", OUTPUT5_DISTRIBUTION);
    public static final Code OUTPUT4_BRIGHTNESS = new Code((byte) 0x38, "OUTPUT4_BRIGHTNESS", OUTPUT5_BRIGHTNESS); //TODO nejasný kód
    public static final Code OUTPUT4_FREQ = new Code((byte) 0x3A, "OUTPUT4_FREQ", OUTPUT5_FREQ);              //TODO nejasnost
    public static final Code OUTPUT4_MIDDLE_PERIOD = new Code((byte) 0x3B, "OUTPUT4_MIDDLE_PERIOD", OUTPUT5_MIDDLE_PERIOD); //TODO nejasnost

    public static final Code OUTPUT3_DURATION = new Code((byte) 0x16, "OUTPUT3_DURATION", OUTPUT4_DURATION);
    public static final Code OUTPUT3_PAUSE = new Code((byte) 0x17, "OUTPUT3_PAUSE", OUTPUT4_PAUSE);
    public static final Code OUTPUT3_DISTRIBUTION = new Code((byte) 0x1B, "OUTPUT3_DISTRIBUTION", OUTPUT4_DISTRIBUTION);
    public static final Code OUTPUT3_BRIGHTNESS = new Code((byte) 0x1F, "OUTPUT3_BRIGHTNESS", OUTPUT4_BRIGHTNESS);
    public static final Code OUTPUT3_FREQ = new Code((byte) 0x28, "OUTPUT3_FREQ", OUTPUT4_FREQ);
    public static final Code OUTPUT3_MIDDLE_PERIOD = new Code((byte) 0x29, "OUTPUT3_MIDDLE_PERIOD", OUTPUT4_MIDDLE_PERIOD);

    public static final Code OUTPUT2_DURATION = new Code((byte) 0x14, "OUTPUT2_DURATION", OUTPUT3_DURATION);
    public static final Code OUTPUT2_PAUSE = new Code((byte) 0x15, "OUTPUT2_PAUSE", OUTPUT3_PAUSE);
    public static final Code OUTPUT2_DISTRIBUTION = new Code((byte) 0x1A, "OUTPUT2_DISTRIBUTION", OUTPUT3_DISTRIBUTION);
    public static final Code OUTPUT2_BRIGHTNESS = new Code((byte) 0x1E, "OUTPUT2_BRIGHTNESS", OUTPUT3_BRIGHTNESS);
    public static final Code OUTPUT2_FREQ = new Code((byte) 0x26, "OUTPUT2_FREQ", OUTPUT3_FREQ);
    public static final Code OUTPUT2_MIDDLE_PERIOD = new Code((byte) 0x27, "OUTPUT2_MIDDLE_PERIOD", OUTPUT3_MIDDLE_PERIOD);

    public static final Code OUTPUT1_DURATION = new Code((byte) 0x12, "OUTPUT1_DURATION", OUTPUT2_DURATION);
    public static final Code OUTPUT1_PAUSE = new Code((byte) 0x13, "OUTPUT1_PAUSE", OUTPUT2_PAUSE);
    public static final Code OUTPUT1_DISTRIBUTION = new Code((byte) 0x19, "OUTPUT1_DISTRIBUTION", OUTPUT2_DISTRIBUTION);
    public static final Code OUTPUT1_BRIGHTNESS = new Code((byte) 0x1D, "OUTPUT1_BRIGHTNESS", OUTPUT2_BRIGHTNESS);
    public static final Code OUTPUT1_FREQ = new Code((byte) 0x24, "OUTPUT1_FREQ", OUTPUT2_FREQ);
    public static final Code OUTPUT1_MIDDLE_PERIOD = new Code((byte) 0x25, "OUTPUT1_MIDDLE_PERIOD", OUTPUT2_MIDDLE_PERIOD);

    public static final Code OUTPUT0_DURATION = new Code((byte) 0x10, "OUTPUT0_DURATION", OUTPUT1_DURATION);
    public static final Code OUTPUT0_PAUSE = new Code((byte) 0x11, "OUTPUT0_PAUSE", OUTPUT1_PAUSE);
    public static final Code OUTPUT0_DISTRIBUTION = new Code((byte) 0x18, "OUTPUT0_DISTRIBUTION", OUTPUT1_DISTRIBUTION);
    public static final Code OUTPUT0_BRIGHTNESS = new Code((byte) 0x1C, "OUTPUT0_BRIGHTNESS", OUTPUT1_BRIGHTNESS);
    public static final Code OUTPUT0_FREQ = new Code((byte) 0x22, "OUTPUT0_FREQ", OUTPUT1_FREQ);
    public static final Code OUTPUT0_MIDDLE_PERIOD = new Code((byte) 0x23, "OUTPUT0_MIDDLE_PERIOD", OUTPUT1_MIDDLE_PERIOD);


    //TODO dodělat zbylé konstanty

    private Codes() {
    }



}
