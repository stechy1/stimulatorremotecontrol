package cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

/**
 * Třída pro otestování třídy DataConvertor
 */
public class DataConvertorTest {

    @Test(expected = InstantiationException.class)
    public void testDataConvertorConstruct() throws Exception{
        Constructor<DataConvertor> c = DataConvertor.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        System.out.println("Chyba: lze vytvořit privátní konstruktor");
    }

    @Test(expected = InvocationTargetException.class)
    public void testIntTo1B1negative() throws Exception {
        DataConvertor.intTo1B(256);
        System.out.println("Chyba: lze převést číslo 256 na bajt, což je větší než 1B");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntTo1B2negative() throws Exception {
        DataConvertor.intTo1B(-1);
        System.out.println("Chyba: lze převést číslo -1 na bajt");
    }

    @Test
    public void testIntTo1B3positive() throws Exception {
        byte a = DataConvertor.intTo1B(10)[0];
        assertEquals("Chyba: číslo 10 není správně převedeno", a, (byte)10);
    }

    @Test
    public void testIntTo2B1positive() throws Exception {
        byte[] a = DataConvertor.intTo2B(10);
        assertEquals("Chyba: číslo 10 není správně převedeno", a[1], (byte)10);
    }

    @Test
    public void testIntTo2B2positive() throws Exception {
        byte[] a = DataConvertor.intTo2B(1000);
        assertArrayEquals("Chyba: číslo 1000 není správně převedeno", a,new byte[]{(byte)3, (byte)232});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntTo2B3negative() throws Exception {
        DataConvertor.intTo2B(65536);
        System.out.println("Chyba: lze převést číslo 65536 na 2B, což je větší než 2B");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntTo2B4negative() throws Exception {
        DataConvertor.intTo2B(-1);
        System.out.println("Chyba: lze převést číslo -1 na 2B");
    }

    @Test
    public void testMilisecondsTo2B1positive() throws Exception {
        byte[] a = DataConvertor.milisecondsTo2B(100.0);
        assertArrayEquals("Chyba: číslo 100.0 [ms] není správně převedeno", a,new byte[]{(byte)3, (byte)232});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMilisecondsTo2B2negative() throws Exception {
        DataConvertor.milisecondsTo2B(-1);
        System.out.println("Chyba: lze převést -1 [ms] na 2B");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMilisecondsTo2B3negative() throws Exception {
        DataConvertor.milisecondsTo2B(6553.6);
        System.out.println("Chyba: lze převést 6553.6 [ms] na 2B");
    }
}