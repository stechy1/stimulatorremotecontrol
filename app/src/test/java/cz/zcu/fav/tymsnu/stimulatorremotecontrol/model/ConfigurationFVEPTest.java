package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testovací třída
 */
public class ConfigurationFVEPTest {

    private static final String CONFIG_NAME = "default";

    private ConfigurationFVEP configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new ConfigurationFVEP(CONFIG_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegative() throws Exception {
        new ConfigurationFVEP("argException", AConfiguration.DEF_OUTPUT_COUNT, null);
        System.out.println("Chyba: byla očekávána vyjímka IllegalArgumentException, protože parametr outputList je null");
    }

    @Test
    public void testDuplicate() throws Exception {
        ConfigurationFVEP duplicated = configuration.duplicate("duplicated");
        assertEquals("Chyba: počet výstupů není stejný", configuration.getOutputCount(), duplicated.getOutputCount());
        assertEquals("Chyba: kolekce výstupů není stejná", configuration.outputList, duplicated.outputList);
    }

    @Test
    public void testRearangeOutputsPositive1() throws Exception {
        ConfigurationERP config = new ConfigurationERP("config");
        config.setOutputCount(AConfiguration.MIN_OUTPUT_COUNT);
        assertEquals("Chyba: počet výstupů v listu se nerovná parametru počet výstupů", config.getOutputCount(), config.outputList.size());
    }

    @Test
    public void testRearangeOutputsPositive2() throws Exception {
        ConfigurationERP config = new ConfigurationERP("config");
        config.setOutputCount(AConfiguration.MAX_OUTPUT_COUNT);
        assertEquals("Chyba: počet výstupů v listu se nerovná parametru počet výstupů", config.getOutputCount(), config.outputList.size());
    }

    @Test
    public void testOutputSetFrequencyPositive1() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 10;
        output.setFrequency(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru frequency", newValue, output.getFrequency());
    }

    @Test
    public void testOutputSetDutyCycle() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 10;
        output.setDutyCycle(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru duty cycle", newValue, output.getDutyCycle());
    }

    @Test
    public void testOutputSetBrightness() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 10;
        output.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru brightness", newValue, output.getBrightness());
    }
}