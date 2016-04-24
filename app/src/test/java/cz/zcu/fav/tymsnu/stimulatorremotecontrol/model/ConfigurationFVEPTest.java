package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

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
    public void testOutputCountValidityPositive() throws Exception {
        assertEquals("Chyba: parametr velikost kolekce s výstupy neodpovídá parametru output count", configuration.getOutputCount(), configuration.outputList.size());
    }

    @Test
    public void testDuplicate() throws Exception {
        ConfigurationFVEP duplicated = configuration.duplicate("duplicated");
        assertEquals("Chyba: počet výstupů není stejný", configuration.getOutputCount(), duplicated.getOutputCount());
        assertEquals("Chyba: kolekce výstupů není stejná", configuration.outputList, duplicated.outputList);
    }

    @Test
    public void testRearangeOutputsPositive1() throws Exception {
        ConfigurationFVEP config = new ConfigurationFVEP("config");
        config.setOutputCount(AConfiguration.MIN_OUTPUT_COUNT);
        assertEquals("Chyba: počet výstupů v listu se nerovná parametru počet výstupů", AConfiguration.MIN_OUTPUT_COUNT, config.outputList.size());
    }

    @Test
    public void testRearangeOutputsPositive2() throws Exception {
        ConfigurationFVEP config = new ConfigurationFVEP("config");
        config.setOutputCount(AConfiguration.MAX_OUTPUT_COUNT);
        assertEquals("Chyba: počet výstupů v listu se nerovná parametru počet výstupů", AConfiguration.MAX_OUTPUT_COUNT, config.outputList.size());
    }

    @Test
    public void testBuilderPositive() throws Exception {
        int outputCount = 1;
        ConfigurationFVEP config = new ConfigurationFVEP.Builder("config")
                .outpuCount(outputCount)
                .outputList(Collections.singletonList(
                        new ConfigurationFVEP.Output.Builder().build()
                ))
                .build();

        assertEquals("Chyba: počet výstupů neodpovídá nastavené hodnotě", outputCount, config.getOutputCount());
        assertEquals("Chyba: velikost kolekce výstupů neodpovídá nastavené hodnotě", outputCount, config.outputList.size());
    }

    @Test
    public void testBuilderNegative() throws Exception {
        List<ConfigurationFVEP.Output> singleList = Collections.singletonList(new ConfigurationFVEP.Output());
        int outputCount = 1;
        ConfigurationFVEP config = new ConfigurationFVEP.Builder("config")
                .outpuCount(outputCount)
                .outputList(null)
                .build();

        assertEquals("Chyba: hodnota parametru outputList se nastavila na null", singleList, config.outputList);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputConstructNegative1() throws Exception {
        new ConfigurationFVEP.Output(null, 0, 0, 0);
        System.out.println("Chyba: hodnota parametru puls se nastavila na null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputConstructNegative2() throws Exception {
        new ConfigurationFVEP.Output(null);
        System.out.println("Chyba: lze vytvořit kopii outputu z null objektu");
    }

    @Test
    public void testOutputSetFrequencyPositive1() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 0;
        output.setFrequency(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru frequency", newValue, output.getFrequency());
    }

    @Test
    public void testOutputSetFrequencyPositive2() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 128;
        output.setFrequency(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru frequency", newValue, output.getFrequency());
    }

    @Test
    public void testOutputSetFrequencyPositive3() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 255;
        output.setFrequency(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru frequency", newValue, output.getFrequency());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputSetFrequencyNegative1() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = -1;
        output.setFrequency(newValue);
        System.out.println("Chyba: nastavila se hodnota, která je mimo interval");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputSetFrequencyNegative2() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 256;
        output.setFrequency(newValue);
        System.out.println("Chyba: nastavila se hodnota, která je mimo interval");
    }

    @Test
    public void testOutputSetDutyCyclePositive1() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 0;
        output.setDutyCycle(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru duty cycle", newValue, output.getDutyCycle());
    }

    @Test
    public void testOutputSetDutyCyclePositive2() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 50;
        output.setDutyCycle(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru duty cycle", newValue, output.getDutyCycle());
    }

    @Test
    public void testOutputSetDutyCyclePositive3() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 100;
        output.setDutyCycle(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota parametru duty cycle", newValue, output.getDutyCycle());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputSetDutyCycleNegative1() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = -1;
        output.setDutyCycle(newValue);
        System.out.println("Chyba: hodnota byla nastavena na hodnotu mimo rozsah");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputSetDutyCycleNegative2() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 101;
        output.setDutyCycle(newValue);
        System.out.println("Chyba: hodnota byla nastavena na hodnotu mimo rozsah");
    }

    @Test
    public void testOutputSetBrightnessPositive1() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 0;
        output.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", newValue, output.getBrightness());
    }

    @Test
    public void testOutputSetBrightnessPositive2() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 50;
        output.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", newValue, output.getBrightness());
    }

    @Test
    public void testOutputSetBrightnessPositive3() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 100;
        output.setBrightness(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota jasu", newValue, output.getBrightness());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputSetBrightnessNegative1() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = -1;
        output.setBrightness(newValue);
        System.out.println("Chyba: jas byl nastaven na hodnotu mimo rozsah");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutputSetBrightnessNegative2() throws Exception {
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output();
        int newValue = 101;
        output.setBrightness(newValue);
        System.out.println("Chyba: jas byl nastaven na hodnotu mimo rozsah");
    }

    @Test
    public void testOutputBuilder() throws Exception {
        int frequency = 10;
        int dutyCycle = 20;
        int brightness = 30;
        ConfigurationFVEP.Output output = new ConfigurationFVEP.Output.Builder()
                .frequency(frequency)
                .duty_cycle(dutyCycle)
                .brightness(brightness)
                .build();

        assertEquals("Chyba: parametr neodpovídá nastavené hodnotě", frequency, output.getFrequency());
        assertEquals("Chyba: parametr neodpovídá nastavené hodnotě", dutyCycle, output.getDutyCycle());
        assertEquals("Chyba: parametr neodpovídá nastavené hodnotě", brightness, output.getBrightness());
    }

    @Test
    public void testOutputPulsSetUp() throws Exception {
        ConfigurationFVEP.Puls puls = new ConfigurationFVEP.Puls();
        int newValue = 10;
        puls.setUp(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota argumentu up", newValue, puls.getUp());
    }

    @Test
    public void testOutputPulsSetDown() throws Exception {
        ConfigurationFVEP.Puls puls = new ConfigurationFVEP.Puls();
        int newValue = 10;
        puls.setDown(newValue);
        assertEquals("Chyba: nastavila se špatná hodnota argumentu down", newValue, puls.getDown());
    }

    @Test
    public void testOutputPulsBuilder() throws Exception {
        int up = 10;
        int down = 20;
        ConfigurationFVEP.Puls puls = new ConfigurationFVEP.Puls.Builder()
                .up(up)
                .down(down)
                .build();

        assertEquals("Chyba: parametr neodpovídá nastavené hodnotě", up, puls.getUp());
        assertEquals("Chyba: parametr neodpovídá nastavené hodnotě", down, puls.getDown());
    }
}