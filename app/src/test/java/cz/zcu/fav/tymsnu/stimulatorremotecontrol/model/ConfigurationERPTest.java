package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


/**
 * Testovací třída pro testování ERP konfigurace
 */

public class ConfigurationERPTest {

    @Parameterized.Parameter
    public int brightnessInRangeValue;
    @Parameterized.Parameter(value = 1)
    public boolean brightnessInRangeExpected;

    private static final String CONFIG_NAME = "default";

    private ConfigurationERP configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new ConfigurationERP(CONFIG_NAME);
    }

    @Test
    public void testDuplicatePositive() throws Exception {
        String newName = "duplicated";
        ConfigurationERP duplicated = configuration.duplicate(newName);

        assertEquals("Chyba: počet výstupů není stejný", configuration.getOutputCount(), duplicated.getOutputCount());
        assertEquals("Chyba: hodnota parametru Out není stejná", configuration.getOut(), duplicated.getOut());
        assertEquals("Chyba: hodnota parametru Eait není stejná", configuration.getWait(), duplicated.getWait());
        assertEquals("Chyba: hodnota parametru Edge není stejná", configuration.getEdge(), duplicated.getEdge());
        assertEquals("Chyba: hodnota parametru Random není stejná", configuration.getRandom(), duplicated.getRandom());
        assertEquals("Chyba: kolekce výstupů není stejná", configuration.outputList, duplicated.outputList);
    }

    @Test
    public void testRearangeOutputs() throws Exception {
        ConfigurationERP config = new ConfigurationERP("config");
        config.setOutputCount(AConfiguration.MIN_OUTPUT_COUNT);
        assertEquals("Chyba: počet výstupů v listu se nerovná parametru počet výstupů", config.getOutputCount(), config.outputList.size());
    }

    @Test
    public void testSetOut() throws Exception {
        int newValue = 5;
        configuration.setOut(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru out", newValue, configuration.getOut());
    }

    @Test
    public void testSetWait() throws Exception {
        int newValue = 5;
        configuration.setWait(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru wait", newValue, configuration.getWait());
    }

    @Test
    public void testSetOutputCount() throws Exception {
        int sameValue = configuration.getOutputCount();
        configuration.setOutputCount(sameValue);
        assertEquals("Chyba: nastavila se špatná hodnota počtu výstupů", sameValue, configuration.getOutputCount());
    }

    @Test
    public void testSetEdge() throws Exception {
        ConfigurationERP.Edge old = configuration.getEdge();
        configuration.setEdge(null);
        assertEquals("Chyba: parametr Edge se nastavil na null", old, configuration.getEdge());
    }

    @Test
    public void testSetRandom() throws Exception {
        ConfigurationERP.Random old = configuration.getRandom();
        configuration.setRandom(null);
        assertEquals("Chyba: parametr Random se nastavil na null", old, configuration.getRandom());
    }

    @Test
    public void testEdgeValueOf1() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hranu", ConfigurationERP.Edge.FALLING, ConfigurationERP.Edge.valueOf(ConfigurationERP.Edge.FALLING.ordinal()));
    }

    @Test
    public void testEdgeValueOf2() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hodnotu", ConfigurationERP.Edge.LEADING, ConfigurationERP.Edge.valueOf(2));
    }

    @Test
    public void testRandomValueOf1() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hodnotu", ConfigurationERP.Random.SHORT, ConfigurationERP.Random.valueOf(ConfigurationERP.Random.SHORT.ordinal()));
    }

    @Test
    public void testRandomValueOf2() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hodnotu", ConfigurationERP.Random.LONG, ConfigurationERP.Random.valueOf(ConfigurationERP.Random.LONG.ordinal()));
    }

    @Test
    public void testRandomValueOf3() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hodnotu", ConfigurationERP.Random.SHORT_LONG, ConfigurationERP.Random.valueOf(ConfigurationERP.Random.SHORT_LONG.ordinal()));
    }

    @Test
    public void testRandomValueOf4() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hodnotu", ConfigurationERP.Random.OFF, ConfigurationERP.Random.valueOf(4));
    }

    @Test
    public void testOutputCanUpdateDistribution() throws Exception {
        ConfigurationERP config = new ConfigurationERP(
                "config",
                2,
                ConfigurationERP.DEF_OUT,
                ConfigurationERP.DEF_WAIT,
                ConfigurationERP.Edge.FALLING,
                ConfigurationERP.Random.OFF,
                Arrays.asList(
                        new ConfigurationERP.Output(
                                new ConfigurationERP.Output.Puls(
                                        ConfigurationERP.Output.Puls.DEF_UP,
                                        ConfigurationERP.Output.Puls.DEF_DOWN),
                                new ConfigurationERP.Output.Distribution(
                                        50,
                                        ConfigurationERP.Output.Distribution.DEF_DELAY
                                ), ConfigurationERP.Output.DEF_BRIGHTNESS),
                        new ConfigurationERP.Output(
                                new ConfigurationERP.Output.Puls(
                                        ConfigurationERP.Output.Puls.DEF_UP,
                                        ConfigurationERP.Output.Puls.DEF_DOWN),
                                new ConfigurationERP.Output.Distribution(
                                        50,
                                        ConfigurationERP.Output.Distribution.DEF_DELAY
                                ), ConfigurationERP.Output.DEF_BRIGHTNESS)
                )
        );

        assertEquals("Chyba: hodnota distribution se může nastavit tak, aby nevyhovovala předpisům",
                false, config.outputList.get(1).canUpdateDistribution(config.outputList, 51));
    }

    @Test
    public void testOutputSetBrightness() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int newValue = 20;
        output.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", newValue, output.getBrightness());
    }

    @Test
    public void testOutputSetUp() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int upValue = 15;
        output.puls.setUp(upValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru up", upValue, output.puls.getUp());
    }

    @Test
    public void testOutputSetDown() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int downValue = 15;
        output.puls.setDown(downValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru down", downValue, output.puls.getDown());
    }

    @Test
    public void testOutputSetValue() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int value = 15;
        output.distribution.setValue(value);
        assertEquals("Chyba: nastavila se špatná hodnota parametru value", value, output.distribution.getValue());
    }

    @Test
    public void testOutputSetDelay() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int delay = 15;
        output.distribution.setDelay(delay);
        assertEquals("Chyba: nastavila se špatná hodnota parametru delay", delay, output.distribution.getDelay());
    }
}