package cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Testovací třída pro otestování třídy RangeUtils
 */
public class RangeUtilsSingleTest {

    @Test(expected = InvocationTargetException.class)
    public void testRangeUtilsConstruct() throws Exception {
        Constructor<RangeUtils> c = RangeUtils.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        System.out.println("Chyba: lze vytvořit privátní konstruktor");
    }

}