package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

import java.nio.ByteBuffer;

public final class DataConvertor {
    private DataConvertor(){}

    public static byte[] intTo1B(int number){
        return ByteBuffer.allocate(1).put((byte)number).array();
    }

    public static byte[] intTo2B(int number){
        return ByteBuffer.allocate(2).put(1, (byte) number).put(0,(byte)(number >> 8)).array();
    }

    public static byte[] milisecondsTo2B(double number){
        return intTo2B((int)(10 * number));
    }
}
