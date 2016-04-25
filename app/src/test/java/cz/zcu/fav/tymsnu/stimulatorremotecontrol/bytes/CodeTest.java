package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CodeTest {

    Code CODE_1, CODE_2;

    @Before
    public void setUp() throws Exception {
        CODE_2 = new Code((byte) 0x12, "CODE_2");
        CODE_1 = new Code((byte) 0x10, "CODE_1", CODE_2);
    }

    @Test
    public void testGetCode() throws Exception {
        assertEquals("Chyba: hodnoty se nerovnají", 0x10, CODE_1.getCode());
    }

    @Test
    public void testGetDescription() throws Exception {
        assertEquals("Chyba: název kódu se neshoduje", "CODE_1", CODE_1.getDescription());
    }

    @Test
    public void testGetNext() throws Exception {

    }
}