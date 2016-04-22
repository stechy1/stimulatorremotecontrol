package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Testovací třída pro třídu ConfigurationCVEP
 */
public class ConfigurationCVEPTest {

    private static final String CONFIG_NAME = "config";


    private ConfigurationCVEP configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new ConfigurationCVEP(CONFIG_NAME);
    }

    @Test
    public void testOutputCountValidityPositive() throws Exception {
        assertEquals("Chyba: parametr velikost kolekce s výstupy neodpovídá parametru output count", configuration.getOutputCount(), configuration.patternList.size());
    }

    @Test
    public void testDuplicate() throws Exception {
        String newName = "duplicated";
        ConfigurationCVEP duplicated = configuration.duplicate(newName);

        assertNotEquals("Chyba: název duplikované konfigurace se shoduje s názvem originální konfigurace", configuration.getName(), duplicated.getName());
        assertEquals("Chyba: počet výstupů není stejný", configuration.getOutputCount(), duplicated.getOutputCount());
        assertEquals("Chyba: hodnota parametru Puls length není stejná", configuration.getPulsLength(), duplicated.getPulsLength());
        assertEquals("Chyba: hodnota parametru Bit shift není stejná", configuration.getBitShift(), duplicated.getBitShift());
        assertEquals("Chyba: hodnota parametru Brightness není stejná", configuration.getBrightness(), duplicated.getBrightness());
        assertEquals("Chyba: parametr Main pattern není stejný", configuration.getMainPattern(), duplicated.getMainPattern());
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
    public void testSetPulsLengthPositive() throws Exception {
        int newValue = 10;
        configuration.setPulsLength(newValue);
        assertEquals("Chyba: parametr neodpovídá nastavené hodnotě", newValue, configuration.getPulsLength());
    }

    @Test
    public void testSetBitShift() throws Exception {
        int sameValue = configuration.getBitShift();
        configuration.setBitShift(sameValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru bit shift", sameValue, configuration.getBitShift());
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
    public void testSetMainPattern() throws Exception {
        int newValue = 2049;
        configuration.setMainPattern(newValue);
        assertEquals("Chyba: hlavní pattern se nastavila na špatnou hodnotu", newValue, configuration.getMainPattern().getValue());
    }

    @Test
    public void testBuilderPositive() throws Exception {
        String name = "config";
        int outputCount = 1;
        int pulsLength = 5;
        int bitShift = 3;
        int brightness = 50;
        int mainPattern = 2049;
        List<ConfigurationCVEP.Pattern> patternList = Collections.singletonList(
                new ConfigurationCVEP.Pattern()
        );
        ConfigurationCVEP config = new ConfigurationCVEP.Builder(name)
                .outputCount(outputCount)
                .pulsLength(pulsLength)
                .bitShift(bitShift)
                .brightness(brightness)
                .mainPattern(mainPattern)
                .build();

        assertEquals("Chyba: název konfigurace nění stejný", name, config.getName());
        assertEquals("Chyba: počet výstupů není stejný", outputCount, config.getOutputCount());
        assertEquals("Chyba: hodnota parametru Puls length není stejná", pulsLength, config.getPulsLength());
        assertEquals("Chyba: hodnota parametru Bit shift není stejná", bitShift, config.getBitShift());
        assertEquals("Chyba: hodnota parametru Brightness není stejná", brightness, config.getBrightness());
        assertEquals("Chyba: parametr Main pattern není stejný", mainPattern, config.getMainPattern().getValue());
        assertEquals("Chyba: kolekce patternů není stejná", patternList, config.patternList);
    }

    @Test
    public void testPatternShiftLeftPositive() throws Exception {
        ConfigurationCVEP.Pattern pattern = new ConfigurationCVEP.Pattern(2);
        pattern.shiftLeft();
        assertEquals("Chyba: bitovu posun doleva nefunguje", 4, pattern.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPatternShiftLeftNegative1() throws Exception {
        ConfigurationCVEP.Pattern pattern = new ConfigurationCVEP.Pattern(2);
        pattern.shiftLeft(-1);
        System.out.println("Chyba: je dovolen záporný bitový posun");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPatternShiftLeftNegative2() throws Exception {
        ConfigurationCVEP.Pattern pattern = new ConfigurationCVEP.Pattern(2);
        pattern.shiftLeft(32);
        System.out.println("Chyba: je dovolen záporný bitový posun");
    }

    @Test
    public void testPatternShiftRightPositive() throws Exception {
        ConfigurationCVEP.Pattern pattern = new ConfigurationCVEP.Pattern(2);
        pattern.shiftRight();
        assertEquals("Chyba: bitovu posun doleva nefunguje", 1, pattern.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPatternShiftRightNegative1() throws Exception {
        ConfigurationCVEP.Pattern pattern = new ConfigurationCVEP.Pattern(2);
        pattern.shiftRight(-1);
        System.out.println("Chyba: je dovolen záporný bitový posun");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPatternShiftRightNegative2() throws Exception {
        ConfigurationCVEP.Pattern pattern = new ConfigurationCVEP.Pattern(2);
        pattern.shiftRight(32);
        System.out.println("Chyba: je dovolen záporný bitový posun");
    }
}