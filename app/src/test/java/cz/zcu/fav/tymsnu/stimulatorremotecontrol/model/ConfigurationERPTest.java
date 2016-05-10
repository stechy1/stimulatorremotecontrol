package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


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
    public void testOutputCountValidityPositive() throws Exception {
        assertEquals("Chyba: parametr velikost kolekce s výstupy neodpovídá parametru output count", configuration.getOutputCount(), configuration.outputList.size());
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
    public void testSetOutPositive() throws Exception {
        int newValue = 5;
        configuration.setOut(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru out", newValue, configuration.getOut());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetOutNegative() throws Exception {
        int newValue = -1;
        configuration.setOut(newValue);
        fail("Chyba: nastavila se záporná hodnota parametru out");
    }

    @Test
    public void testSetWaitPositive() throws Exception {
        int newValue = 5;
        configuration.setWait(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru wait", newValue, configuration.getWait());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetWaitNegative() throws Exception {
        int newValue = -1;
        configuration.setWait(newValue);
        fail("Chyba: nastavila se záporná hodnota parametru wait");
    }

    @Test
    public void testSetOutputCountPositive() throws Exception {
        int sameValue = configuration.getOutputCount();
        configuration.setOutputCount(sameValue);
        assertEquals("Chyba: nastavila se špatná hodnota počtu výstupů", sameValue, configuration.getOutputCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEdgeNegative() throws Exception {
        configuration.setEdge(null);
        System.out.println("Chyba: parametr Edge se nastavil na null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRandomNegative() throws Exception {
        configuration.setRandom(null);
        System.out.println("Chyba: parametr Random se nastavil na null");
    }

    @Test
    public void testEdgeValueOfPositive1() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hranu", ConfigurationERP.Edge.LEADING, ConfigurationERP.Edge.valueOf(ConfigurationERP.Edge.LEADING.ordinal()));
    }

    @Test
    public void testEdgeValueOfPositive2() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hranu", ConfigurationERP.Edge.FALLING, ConfigurationERP.Edge.valueOf(ConfigurationERP.Edge.FALLING.ordinal()));
    }

    @Test
    public void testEdgeValueOfPositive3() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hodnotu", ConfigurationERP.Edge.LEADING, ConfigurationERP.Edge.valueOf(2));
    }

    @Test
    public void testRandomValueOfPositive1() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hodnotu", ConfigurationERP.Random.SHORT, ConfigurationERP.Random.valueOf(ConfigurationERP.Random.SHORT.ordinal()));
    }

    @Test
    public void testRandomValueOfPositive2() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hodnotu", ConfigurationERP.Random.LONG, ConfigurationERP.Random.valueOf(ConfigurationERP.Random.LONG.ordinal()));
    }

    @Test
    public void testRandomValueOfPositive3() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hodnotu", ConfigurationERP.Random.SHORT_LONG, ConfigurationERP.Random.valueOf(ConfigurationERP.Random.SHORT_LONG.ordinal()));
    }

    @Test
    public void testRandomValueOfPositive4() throws Exception {
        assertEquals("Chyba: metoda nevrátila správnou hodnotu", ConfigurationERP.Random.OFF, ConfigurationERP.Random.valueOf(4));
    }

    @Test
    public void testBuilderPositive() throws Exception {
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
    public void testBuilderNegative() throws Exception {
        List<ConfigurationERP.Output> singleList = Collections.singletonList(new ConfigurationERP.Output());
        ConfigurationERP config = new ConfigurationERP.Builder("builder")
                .outputCount(1)
                .random(null)
                .edge(null)
                .outputList(null)
                .build();

        assertEquals("Chyba: hodnota parametru edge se nastavila na null", ConfigurationERP.Edge.FALLING, config.getEdge());
        assertEquals("Chyba: hodnota parametru random se nastavila na null", ConfigurationERP.Random.OFF, config.getRandom());
        assertEquals("Chyba: hodnota parametru outputList se nastavila na null", singleList, config.outputList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputConstructNegative1() throws Exception {
        new ConfigurationERP.Output(null, new ConfigurationERP.Output.Distribution(), ConfigurationERP.Output.DEF_BRIGHTNESS);
        System.out.println("Chyba: byla očekávána vyjímka IllegalArgumentException, protože parametr Puls je null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputConstructNegative2() throws Exception {
        new ConfigurationERP.Output(new ConfigurationERP.Output.Puls(), null, ConfigurationERP.Output.DEF_BRIGHTNESS);
        System.out.println("Chyba: byla očekávána vyjímka IllegalArgumentException, protože parametr Puls je null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputConstructNegative3() throws Exception {
        ConfigurationERP.Output.clone(null);
        System.out.println("Chyba: lze vytvořit kopii outputu z null objektu");
    }

    @Test
    public void testOutputCanUpdateDistributionPositive() throws Exception {
        ConfigurationERP config = new ConfigurationERP.Builder("config")
                .outputCount(2)
                .outputList(Arrays.asList(
                        new ConfigurationERP.Output.Builder()
                                .distribution(new ConfigurationERP.Output.Distribution(50, 0))
                                .build(),
                        new ConfigurationERP.Output.Builder()
                                .distribution(new ConfigurationERP.Output.Distribution(25, 0))
                                .build()
                ))
                .build();
        assertTrue("Chyba: hodnota distribution se může nastavit tak, aby nevyhovovala předpisům",
                config.outputList.get(1).canUpdateDistribution(config.outputList, 50));
    }

    @Test
    public void testOutputCanUpdateDistributionNegative() throws Exception {
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
        assertFalse("Chyba: hodnota distribution se může nastavit tak, aby nevyhovovala předpisům",
                config.outputList.get(1).canUpdateDistribution(config.outputList, 51));
    }

    @Test
    public void testOutputSetBrightnessPositive1() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int newValue = 0;
        output.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", newValue, output.getBrightness());
    }

    @Test
    public void testOutputSetBrightnessPositive2() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int newValue = 50;
        output.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", newValue, output.getBrightness());
    }

    @Test
    public void testOutputSetBrightnessPositive3() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int newValue = 100;
        output.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", newValue, output.getBrightness());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputSetBrightnessNegative1() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int newValue = -1;
        output.setBrightness(newValue);
        System.out.println("Chyba: jas byl nastaven na hodnotu mimo rozsah");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputSetBrightnessNegative2() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int newValue = 101;
        output.setBrightness(newValue);
        System.out.println("Chyba: jas byl nastaven na hodnotu mimo rozsah");
    }

    @Test
    public void testOutputPulsSetUpPositive() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int upValue = 15;
        output.puls.setUp(upValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru up", upValue, output.puls.getUp());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputPulsSetUpNegative() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int upValue = -1;
        output.puls.setUp(upValue);
        fail("Chyba: nastavila se záporná hodnota parametru up");
    }

    @Test
    public void testOutputPulsSetDownPositive() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int downValue = 15;
        output.puls.setDown(downValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru down", downValue, output.puls.getDown());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputPulsSetDownNegative() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int upValue = -1;
        output.puls.setDown(upValue);
        fail("Chyba: nastavila se záporná hodnota parametru down");
    }

    @Test
    public void testOutputDistributionSetValuePositive1() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int value = 0;
        output.distribution.setValue(value);
        assertEquals("Chyba: nastavila se špatná hodnota parametru value", value, output.distribution.getValue());
    }

    @Test
    public void testOutputDistributionSetValuePositive2() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int value = 50;
        output.distribution.setValue(value);
        assertEquals("Chyba: nastavila se špatná hodnota parametru value", value, output.distribution.getValue());
    }

    @Test
    public void testOutputDistributionSetValuePositive3() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int value = 100;
        output.distribution.setValue(value);
        assertEquals("Chyba: nastavila se špatná hodnota parametru value", value, output.distribution.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputDistributionSetValueNegative1() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int value = -1;
        output.distribution.setValue(value);
        System.out.println("Chyba: hodnota byla nastavena na hodnotu mimo rozsah");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputDistributionSetValueNegative2() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int value = 101;
        output.distribution.setValue(value);
        System.out.println("Chyba: hodnota byla nastavena na hodnotu mimo rozsah");
    }

    @Test
    public void testOutputDistributionSetDelayPositive() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int delay = 15;
        output.distribution.setDelay(delay);
        assertEquals("Chyba: nastavila se špatná hodnota parametru delay", delay, output.distribution.getDelay());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputDistributionSetDelayNegative() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output();
        int delay = -1;
        output.distribution.setDelay(delay);
        fail("Chyba: nastavila se záporná hodnota parametru delay");
    }

    @Test
    public void testOutputBuilderPositive() throws Exception {
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
    public void testOutputBuilderNegative() throws Exception {
        ConfigurationERP.Output output = new ConfigurationERP.Output.Builder()
                .puls(null)
                .distribution(null)
                .build();

        assertNotEquals("Chyba: hodnota parametru puls se nastavila na null", null, output.puls);
        assertNotEquals("Chyba: hodnota parametru distribution se nastavila na null", null, output.distribution);
    }
}