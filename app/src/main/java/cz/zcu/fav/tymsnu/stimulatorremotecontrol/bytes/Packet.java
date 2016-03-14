package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

import java.nio.ByteBuffer;

public class Packet {

    private Code code;
    private byte[] value;

    public Packet(Code code) {
        this.code = code;
        this.value = new byte[]{0x00, code.getCode(), 0x00};
    }

    public Packet(Code code, byte[] data) {
        this.code = code;
        ByteBuffer buffer = ByteBuffer.allocate(2 + data.length)
                .put(ByteBuffer.allocate(2).put(1, code.getCode())).put((data));

        buffer.put(0, (byte)(buffer.capacity() - 2));
        this.value = buffer.array();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(byte a : value){
            sb.append(String.format("0x%02X ", a));
        }

        return value.length + "B | " + code.getDescription() + " | " + sb.toString();
    }

    public byte[] getValue() {
        return value;
    }
}
