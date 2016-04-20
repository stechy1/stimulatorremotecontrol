package cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * Testovací třída pro otestování třídy RangeUtils
 */
public class RangeUtilsSingleTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testRangeUtilsConstruct() throws Exception {
        Constructor<RangeUtils> c = RangeUtils.class.getDeclaredConstructor();
        c.setAccessible(true);
        RangeUtils u = c.newInstance(); // Hello sailor
    }

}