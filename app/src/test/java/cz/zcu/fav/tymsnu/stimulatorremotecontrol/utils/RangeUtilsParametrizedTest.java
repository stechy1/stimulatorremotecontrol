package cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Testovací třída pro otestování správných rozsahů
 */
@RunWith(Parameterized.class)
public class RangeUtilsParametrizedTest {

    @Parameterized.Parameters(name = "{index}: vstupniHodnota={0} ocekavanyVysledek={1}")
    public static Collection<Object[]> rangeData() {
        return Arrays.asList(new Object[][]{
                {-1, -1, 1, false},
                {0, 0, 1, true},
                {1, 1, 1, true},
                {50, 50, 1, true},
                {99, 254, 1, true},
                {100, 255, 1, true},
                {101, 256, 1, false},

                {1, 256, 2, true},
                {1, 257, 2, true},
                {100, 65535, 2, true},
                {101, 65536, 2, false}
        });
    }

    @Parameterized.Parameter
    public int percentValue;
    @Parameterized.Parameter(1)
    public int byteValue;
    @Parameterized.Parameter(2)
    public int numOfBytes;
    @Parameterized.Parameter(3)
    public boolean expectedResult;

    @Test
    public void testIsInByteRangePositive1() throws Exception {
        assertEquals("Chyba: hodnota je mimo interval", expectedResult, RangeUtils.isInByteRange(byteValue, numOfBytes));
    }

    @Test
    public void testIsInPercentRange() throws Exception {
        assertEquals("Chyba: hodnota je mimo interval", expectedResult, RangeUtils.isInPercentRange(percentValue));
    }
}