package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testovací třída pro testovaní třídy Packet
 */
public class PacketTest {

    @Test(expected=IllegalArgumentException.class)
    public void testPacketConstructorNegative1() throws Exception{
        new Packet(null);
        System.out.println("Chyba: lze vyvořit Packet s kódem co je null");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPacketConstructorNegative2() throws Exception{
        new Packet(null, new byte[]{0x01});
        System.out.println("Chyba: lze vyvořit Packet s kódem co je null");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testPacketConstructorNegative3() throws Exception{
        new Packet(Codes.OUTPUT0_BRIGHTNESS, null);
        System.out.println("Chyba: lze vyvořit Packet s datama null, pro tento účel se používá jiný konstruktor");
    }

    @Test
    public void testToStringPositive() throws Exception {
        String expected = "3B | OUTPUT0_BRIGHTNESS | 0x01 0x1C 0x05";
        Packet testPacket = new Packet(Codes.OUTPUT0_BRIGHTNESS, new byte[]{0x05});
        System.out.println(testPacket);
        assertEquals("Chyba: textová reprezentace packetu neodpovídá předpokladu", expected, testPacket.toString().trim());
    }
}