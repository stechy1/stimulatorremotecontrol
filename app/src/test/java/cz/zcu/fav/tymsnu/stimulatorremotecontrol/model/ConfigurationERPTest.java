package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Testovací třída pro testování ERP konfigurace
 */

public class ConfigurationERPTest {
    private static final String CONFIG_NAME = "default";

    private ConfigurationERP configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new ConfigurationERP(CONFIG_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructNegative1() throws Exception {
        new ConfigurationERP("argException", 1, 0, 0, null, ConfigurationERP.Random.OFF, new ArrayList<ConfigurationERP.Output>());
        System.out.println("Chyba: byla očekávána vyjímka IllegalArgumentException, protože parametr Edge je null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructNegative2() throws Exception {
        new ConfigurationERP("argException", 1, 0, 0, ConfigurationERP.Edge.FALLING, null, new ArrayList<ConfigurationERP.Output>());
        System.out.println("Chyba: byla očekávána vyjímka IllegalArgumentException, protože parametr Random je null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructNegative3() throws Exception {
        new ConfigurationERP("argException", 1, 0, 0, ConfigurationERP.Edge.FALLING, ConfigurationERP.Random.OFF, null);
        System.out.println("Chyba: byla očekávána vyjímka IllegalArgumentException, protože parametr outputList je null");
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
    public void testBuilderPositive1() throws Exception {
        int outputCount = 1;
        int out = 5;
        int wait = 45;
        ConfigurationERP.Edge edge = ConfigurationERP.Edge.FALLING;
        ConfigurationERP.Random random = ConfigurationERP.Random.LONG;
        ConfigurationERP config = new ConfigurationERP.Builder("builder")
                .outputCount(outputCount)
                .out(out)
                .wait(wait)
                .edge(edge)
                .random(random)
                .outputList(Collections.singletonList(
                        new ConfigurationERP.Output.Builder().build()
                ))
                .build();

        assertEquals("Chyba: počet výstupů neodpovídá nastavené hodnotě", outputCount, config.getOutputCount());
        assertEquals("Chyba: hodnota parametru out neodpovídá nastavené hodnotě", out, config.getOut());
        assertEquals("Chyba: hodnota parametru wait neodpovídá nastavené hodnotě", wait, config.getWait());
        assertEquals("Chyba: hodnota prametru edge neodpovídá nastavené hodnotě", edge, config.getEdge());
        assertEquals("Chyba: hodnota parametru random neodpovídá nastavené hodnotě", random, config.getRandom());
        assertEquals("Chyba: velikost kolekce výstupů neodpovídá nastavené hodnotě", outputCount, config.outputList.size());

    }

    @Test
    public void testBuilderPositive2() throws Exception {
        List<ConfigurationERP.Output> singleList = Collections.singletonList(new ConfigurationERP.Output());
        ConfigurationERP config = new ConfigurationERP.Builder("builder")
                .random(null)
                .edge(null)
                .outputList(null)
                .build();

        assertEquals("Chyba: hodnota parametru edge se nastavila na null", ConfigurationERP.Edge.FALLING, config.getEdge());
        assertEquals("Chyba: hodnota parametru random se nastavila na null", ConfigurationERP.Random.OFF, config.getRandom());
        assertEquals("Chyba: hodnota parametru outputList se nastavila na null", singleList, config.outputList);
    }

    @Test
    public void testOutputCanUpdateDistribution() throws Exception {
        ConfigurationERP config = new ConfigurationERP.Builder("config")
                .outputCount(2)
                .outputList(Arrays.asList(
                        new ConfigurationERP.Output.Builder()
                                .distribution(new ConfigurationERP.Output.Distribution(50, 0))
                                .build(),
                        new ConfigurationERP.Output.Builder()
                                .distribution(new ConfigurationERP.Output.Distribution(50, 0))
                                .build()
                ))
                .build();
        assertEquals("Chyba: hodnota distribution se může nastavit tak, aby nevyhovovala předpisům",
                false, config.outputList.get(1).canUpdateDistribution(config.outputList, 51));
    }

    @Test
    public void testOutputSetBrightnessPositive1() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output.Builder().brightness(5).build();
        int sameValue = 20;
        output.setBrightness(sameValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", sameValue, output.getBrightness());
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

    @Test
    public void testOutputBuilderPositive1() throws Exception {
        ConfigurationERP.Output.Puls puls = new ConfigurationERP.Output.Puls();
        ConfigurationERP.Output.Distribution distribution = new ConfigurationERP.Output.Distribution();
        int brightness = 50;
        ConfigurationERP.Output output = new ConfigurationERP.Output.Builder()
                .puls(puls)
                .distribution(distribution)
                .brightness(brightness)
                .build();

        assertEquals("Chyba: hodnota parametru puls neodpovídá nastavené hodnotě", puls, output.puls);
        assertEquals("Chyba: hodnota parametru distribution neodpovídá nastavené hodnotě", distribution, output.distribution);
        assertEquals("Chyba: hodnota parametru brightness neodpovídá nastavené hodnotě", brightness, output.getBrightness());
    }

    @Test
    public void testOutputBuilderPositive2() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output.Builder()
                .puls(null)
                .distribution(null)
                .build();

        assertNotEquals("Chyba: hodnota parametru puls se nastavila na null", null, output.puls);
        assertNotEquals("Chyba: hodnota parametru distribution se nastavila na null", null, output.distribution);
    }
}