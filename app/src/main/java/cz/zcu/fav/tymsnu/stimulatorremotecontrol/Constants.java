package cz.zcu.fav.tymsnu.stimulatorremotecontrol;


public class Constants {

    // Typy zpráv, které procházy přes BluetoothCommunicationService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";


    public static class Fragments {
        public static final int HOME     = 0;
        public static final int ERP      = 1;

        public static final int SETTINGS = 7;
    }

    public static final int ERP_SCREEN_COUNT = 3;

}
