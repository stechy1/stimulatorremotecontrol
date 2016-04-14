package cz.zcu.fav.tymsnu.stimulatorremotecontrol;


public class Constants {

    // Typy zpráv, které procházy přes BluetoothCommunicationService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_SHOW = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    public static final int ERP_SCREEN_COUNT = 3;

    // Konstanty představující jednotlivé fragmenty v aplikaci
    public static final int FRAGMENT_ERP      =  0;
    public static final int FRAGMENT_FVEP     =  1;
    public static final int FRAGMENT_TVEP     =  2;
    public static final int FRAGMENT_CVEP     =  3;
    public static final int FRAGMENT_REA      =  4;
    public static final int FRAGMENT_AUT      =  5;
    public static final int FRAGMENT_BIO      =  6;
    public static final int FRAGMENT_TEST     =  7;
    public static final int FRAGMENT_SETTINGS =  8;
    public static final int FRAGMENT_HELP     =  9;
    public static final int FRAGMENT_ABOUT    = 10;

    public static final String FOLDER_ERP  = "erp";
    public static final String FOLDER_BCI  = "bci";
    public static final String FOLDER_FVEP = "fvep";
    public static final String FOLDER_CVEP = "cvep";
    public static final String FOLDER_TVEP = "tvep";
    public static final String FOLDER_REA  = "rea";

}
