package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vrbik on 22.04.2016.
 */
public class PacketTest {

    @Test(expected=IllegalArgumentException.class)
    public void testPacketConstructor1negative() throws Exception{
        new Packet(null);
        System.out.println("Chyba: lze vyvořit Packet s kódem co je null");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPacketConstructor2negative() throws Exception{
        new Packet(null, new byte[]{0x01});
        System.out.println("Chyba: lze vyvořit Packet s kódem co je null");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPacketConstructor3negative() throws Exception{
        new Packet(Codes.OUTPUT0_BRIGHTNESS, null);
        System.out.println("Chyba: lze vyvořit Packet s datama null, pro tento účel se používá jiný konstruktor");
    }

    @Test
    public void testToStringpositive() throws Exception {
        Packet testPacket = new Packet(Codes.OUTPUT0_BRIGHTNESS, new byte[]{0x05});
        System.out.println(testPacket);
        assertTrue(testPacket.toString().equals("3B | OUTPUT0_BRIGHTNESS | 0x01 0x1C 0x05"));
    }
}