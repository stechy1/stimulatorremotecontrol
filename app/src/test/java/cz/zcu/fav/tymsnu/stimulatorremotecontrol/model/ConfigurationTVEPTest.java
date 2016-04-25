package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Testovací třída pro třídu ConfigurationTVEP
 */
public class ConfigurationTVEPTest {

    private static final String CONFIG_NAME = "config";


    private ConfigurationTVEP configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new ConfigurationTVEP(CONFIG_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructNegative() throws Exception {
        new ConfigurationTVEP("name", 1, 0, 0, 0, 0, null);
        System.out.println("Chyba: byla očekávána vyjímka IllegalArgumentException, protože parametr pattern list je null");
    }

    @Test
    public void testOutputCountValidityPositive() throws Exception {
        assertEquals("Chyba: parametr velikost kolekce s výstupy neodpovídá parametru output count", configuration.getOutputCount(), configuration.patternList.size());
    }

    @Test
    public void testDuplicatePositive() throws Exception {
        ConfigurationTVEP duplicated = configuration.duplicate("duplicated");

        assertNotEquals("Chyba: název duplikované konfigurace se shoduje s názvem originální konfigurace", configuration.getName(), duplicated.getName());
        assertEquals("Chyba: počet výstupů není stejný", configuration.getOutputCount(), duplicated.getOutputCount());
        assertEquals("Chyba: hodnota parametru Pattern length není stejná", configuration.getPatternLength(), duplicated.getPatternLength());
        assertEquals("Chyba: hodnota parametru Puls length není stejná", configuration.getPulsLength(), duplicated.getPulsLength());
        assertEquals("Chyba: hodnota parametru Puls skew není stejní", configuration.getPulsSkew(), duplicated.getPulsSkew());
        assertEquals("Chyba: hodnota parametru Brightness není stejná", configuration.getBrightness(), duplicated.getBrightness());
        assertEquals("Chyba: kolekce patternů není stejná", configuration.patternList, duplicated.patternList);
    }

    @Test
    public void testRearangeOutputsPositive1() throws Exception {
        configuration.setOutputCount(AConfiguration.MIN_OUTPUT_COUNT);
        assertEquals("Chyba: počet výstupů v listu se nerovná parametru počet výstupů", configuration.getOutputCount(), configuration.patternList.size());
    }

    @Test
    public void testRearangeOutputsPositive2() throws Exception {
        configuration.setOutputCount(AConfiguration.MAX_OUTPUT_COUNT);
        assertEquals("Chyba: počet výstupů v listu se nerovná parametru počet výstupů", configuration.getOutputCount(), configuration.patternList.size());
    }

    @Test
    public void testSetOutputCountPositive() throws Exception {
        int newValue = 6;
        configuration.setOutputCount(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota počtu výstupů", newValue, configuration.getOutputCount());
    }

    @Test
    public void testSetPatternLengthPositive1() throws Exception {
        configuration.setPatternLength(ConfigurationTVEP.MIN_PATTERN_LENGTH);
        assertEquals("Chyba: nastavila se špatná hodnota délky paternu", ConfigurationTVEP.MIN_PATTERN_LENGTH, configuration.getPatternLength());
    }

    @Test
    public void testSetPatternLengthPositive2() throws Exception {
        int newValue = 6;
        configuration.setPatternLength(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota délky paternu", newValue, configuration.getPatternLength());
    }

    @Test
    public void testSetPatternLengthPositive3() throws Exception {
        configuration.setPatternLength(ConfigurationTVEP.MAX_PATTERN_LENGTH);
        assertEquals("Chyba: nastavila se špatná hodnota délky paternu", ConfigurationTVEP.MAX_PATTERN_LENGTH, configuration.getPatternLength());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPatternLengthNegative1() throws Exception {
        configuration.setPatternLength(ConfigurationTVEP.MIN_PATTERN_LENGTH - 1);
        System.out.println("Chyba: nastavila se hodnota, která je mimo interval");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPatternLengthNegative2() throws Exception {
        configuration.setPatternLength(ConfigurationTVEP.MAX_PATTERN_LENGTH + 1);
        System.out.println("Chyba: nastavila se hodnota, která je mimo interval");
    }


    @Test
    public void testSetPulsLengthPositive() throws Exception {
        int newValue = 10;
        configuration.setPulsLength(newValue);
        assertEquals("Chyba: parametr neodpovídá nastavené hodnotě", newValue, configuration.getPulsLength());
    }

    @Test
    public void testSetPulsSkew() throws Exception {
        int newValue = 3;
        configuration.setPulsSkew(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru puls skew", newValue, configuration.getPulsSkew());
    }

    @Test
    public void testOutputSetBrightnessPositive1() throws Exception {
        int newValue = 0;
        configuration.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", newValue, configuration.getBrightness());
    }

    @Test
    public void testOutputSetBrightnessPositive2() throws Exception {
        int newValue = 50;
        configuration.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", newValue, configuration.getBrightness());
    }

    @Test
    public void testOutputSetBrightnessPositive3() throws Exception {
        int newValue = 100;
        configuration.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", newValue, configuration.getBrightness());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputSetBrightnessNegative1() throws Exception {
        int newValue = -1;
        configuration.setBrightness(newValue);
        System.out.println("Chyba: jas byl nastaven na hodnotu mimo rozsah");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputSetBrightnessNegative2() throws Exception {
        int newValue = 101;
        configuration.setBrightness(newValue);
        System.out.println("Chyba: jas byl nastaven na hodnotu mimo rozsah");
    }

    @Test
    public void testBuilderPositive() throws Exception {

        String name = "builder";
        int outputCount = 1;
        int patternLength = 2;
        int pulsLength = 6;
        int pulsSkew = 5;
        int brightness = 50;
        ConfigurationTVEP config = new ConfigurationTVEP.Builder(name)
                .outputCount(outputCount)
                .patternLength(patternLength)
                .pulsLength(pulsLength)
                .pulsSkew(pulsSkew)
                .brightness(brightness)
                .patternList(Collections.singletonList(
                        new ConfigurationTVEP.Pattern()
                ))
                .build();

        assertEquals("Chyba: název konfigurace je špatně nastaven", name, config.getName());
        assertEquals("Chyba: hodnota parametru output count neodpovídá nastavené hodnotě", outputCount, config.getOutputCount());
        assertEquals("Chyba: hodnota parametru Pattern length neodpovídá nastavené hodnotě", patternLength, config.getPatternLength());
        assertEquals("Chyba: hodnota parametru Puls length neodpovídá nastavené hodnotě", pulsLength, config.getPulsLength());
        assertEquals("Chyba: hodnota parametru Puls skew neodpovídá nastavené hodnotě", pulsSkew, config.getPulsSkew());
        assertEquals("Chyba: hodnota parametru brightness neodpovídá nastavené hodnotě", brightness, config.getBrightness());
        assertEquals("Chyba: velikost kolekce výstupů neodpovídá nastavené hodnotě", outputCount, config.patternList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPatternConstructNegative() throws Exception {
        ConfigurationTVEP.Pattern.clone(null);
        System.out.println("Chyba: lze vytvořit kopii patternu z null objektu");
    }

    @Test
    public void testPatternSevValuePositive() throws Exception {
        ConfigurationTVEP.Pattern pattern = new ConfigurationTVEP.Pattern();
        int newValue = 50;
        pattern.setValue(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru value", newValue, pattern.getValue());

    }
}