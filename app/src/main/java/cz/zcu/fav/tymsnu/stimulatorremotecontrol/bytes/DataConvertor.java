package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

import java.nio.ByteBuffer;

/**
 * Třída pro převody hodnot do bajtové podoby,
 * používá se např. při reprezentaci čísel
 */
public final class DataConvertor {
    private DataConvertor(){}

    /**
     * Metoda převádí Int na jeden bajt
     * @param number číslo pro převod < 2^8
     * @return pole o jednom bajtu
     */
    public static byte[] intTo1B(int number){
        return ByteBuffer.allocate(1).put((byte)number).array();
    }

    /**
     * Metoda převádí Int na dva bajty, je tedy vhodná při vyšších hodnotách
     * @param number číslo pro převod < 2^16
     * @return pole o dvou bajtech
     */
    public static byte[] intTo2B(int number){
        return ByteBuffer.allocate(2).put(1, (byte) number).put(0,(byte)(number >> 8)).array();
    }

    /**
     * Metoda pro převod [0.1 ms] na 2 bajty
     * @param number číslo reprezentující počet 0.1 ms např 30.6 ms => 306*0.1 ms
     * @return pole od dvou bajtech
     */
    public static byte[] milisecondsTo2B(double number){
        return intTo2B((int)(10 * number));
    }
}
