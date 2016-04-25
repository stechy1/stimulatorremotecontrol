package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

import java.nio.ByteBuffer;

/**
 * Třída reprezentující jeden packet
 */
public class Packet {

    /**
     * Pevná velikost packetu
     */
    private static final int PACKET_SIZE = 64;

    private Code code;
    private byte[] value = new byte[PACKET_SIZE];
    private int usedBytes;

    /**
     * Konstruktor pro bezdatové packety, vyhodí vyjímku když jako parametr přijde null
     * @param code typ packetu
     */
    public Packet(Code code) {
        if(code == null) throw new IllegalArgumentException();

        this.code = code;
        fillPacket(new byte[]{0x00, code.getCode()});
    }

    /**
     * Konstruktor pro packety s daty, když přijde nějaký null parametr -> vyhodí vyjímku
     * @param code typ packetu
     * @param data data packetu
     */
    public Packet(Code code, byte[] data) {
        if(code == null || data == null) throw new IllegalArgumentException();

        this.code = code;
        ByteBuffer buffer = ByteBuffer.allocate(2 + data.length)
                .put(ByteBuffer.allocate(2).put(1, code.getCode())).put((data));

        buffer.put(0, (byte) (buffer.capacity() - 2));
        fillPacket(buffer.array());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < usedBytes; i++) {
            if(i == usedBytes - 1) sb.append(String.format("0x%02X", value[i]));
            else sb.append(String.format("0x%02X ", value[i]));
        }
        return usedBytes + "B | " + code.getDescription() + " | " + sb.toString();
    }

    /**
     * Naplní packet obsahem a ostatní bajty nastaví na 0x00
     * @param content nenulový obsah packetu
     */
    private void fillPacket(byte[] content){
        usedBytes = content.length;
        for (int i = 0; i < PACKET_SIZE; i++) {
            if(i < usedBytes)
                value[i] = content[i];
            else
                value[i] = 0x00;
        }
    }

    /**
     * Vrátí bajtové pole reprezentující packet, toto se používá při BT posílání
     * @return bajtové pole
     */
    public byte[] getValue() {
        return value;
    }
}
