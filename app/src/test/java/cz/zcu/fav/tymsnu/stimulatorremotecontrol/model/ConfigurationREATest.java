package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testovací třída pro třídu ConfigurationREA
 */
public class ConfigurationREATest {

    private static final String CONFIG_NAME = "config";

    private ConfigurationREA configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new ConfigurationREA(CONFIG_NAME);
    }

    @Test
    public void testDuplicate() throws Exception {
        String name = "duplicated";
        ConfigurationREA duplicated = configuration.duplicate(name);

        assertEquals("Chyba: počet výstupů není stejný", configuration.getOutputCount(), duplicated.getOutputCount());
        assertEquals("Chyba: hodnota parametru CycleCount není stejná", configuration.getCycleCount(), duplicated.getCycleCount());
        assertEquals("Chyba: hodnota parametru WaitFixed není stejná", configuration.getWaitFixed(), duplicated.getWaitFixed());
        assertEquals("Chyba: hodnota parametru WaitRandom není stejná", configuration.getWaitRandom(), duplicated.getWaitRandom());
        assertEquals("Chyba: hodnota parametru MissTime není stejná", configuration.getMissTime(), duplicated.getMissTime());
        assertEquals("Chyba: hodnota parametru Brightness není stejná", configuration.getBrightness(), duplicated.getBrightness());
        assertEquals("Chyba: hodnota parametru OnFail není stejná", configuration.getOnFail(), duplicated.getOnFail());
        assertEquals("Chyba: hodnota parametru Sex není stejná", configuration.getSex(), duplicated.getSex());
        assertEquals("Chyba: hodnota parametru A není stejná", configuration.getA(), duplicated.getA());
        assertEquals("Chyba: hodnota parametru H není stejná", configuration.getH(), duplicated.getH());
        assertEquals("Chyba: hodnota parametru W není stejná", configuration.getW(), duplicated.getW());
    }

    @Test
    public void testSexPositive1() throws Exception {
        assertEquals("Chyba: pohlaví se neshoduje", ConfigurationREA.Sex.MALE, ConfigurationREA.Sex.valueOf(ConfigurationREA.Sex.MALE.ordinal()));
    }

    @Test
    public void testSexPositive2() throws Exception {
        assertEquals("Chyba: pohlaví se neshoduje", ConfigurationREA.Sex.FEMALE, ConfigurationREA.Sex.valueOf(ConfigurationREA.Sex.FEMALE.ordinal()));
    }

    @Test
    public void testSexNegative() throws Exception {
        assertEquals("Chyba: pohlaví se neshoduje", ConfigurationREA.Sex.MALE, ConfigurationREA.Sex.valueOf(ConfigurationREA.Sex.FEMALE.ordinal() + 1));
    }

    @Test
    public void testBuilderPositive() throws Exception {
        String name = "builder";
        int outputCount = 1;
        int cycleCount = 1;
        int waitFixed = 3;
        int waitRandom = 3;
        int missTime = 5;
        int brightness = 50;
        int onFail = 1;
        ConfigurationREA.Sex sex = ConfigurationREA.Sex.FEMALE;
        int a = 21;
        int h = 169;
        int w = 60;

        ConfigurationREA config = new ConfigurationREA.Builder(name)
                .outputCount(outputCount)
                .cycleCount(cycleCount)
                .waitFixed(waitFixed)
                .waitRandom(waitRandom)
                .missTime(missTime)
                .brightness(brightness)
                .onFail(onFail)
                .sex(sex)
                .a(a)
                .h(h)
                .w(w)
                .build();

        assertEquals("Chyba: počet výstupů není stejný", outputCount, config.getOutputCount());
        assertEquals("Chyba: hodnota parametru CycleCount není stejná", cycleCount, config.getCycleCount());
        assertEquals("Chyba: hodnota parametru WaitFixed není stejná", waitFixed, config.getWaitFixed());
        assertEquals("Chyba: hodnota parametru WaitRandom není stejná", waitRandom, config.getWaitRandom());
        assertEquals("Chyba: hodnota parametru MissTime není stejná", missTime, config.getMissTime());
        assertEquals("Chyba: hodnota parametru Brightness není stejná", brightness, config.getBrightness());
        assertEquals("Chyba: hodnota parametru OnFail není stejná", onFail, config.getOnFail());
        assertEquals("Chyba: hodnota parametru Sex není stejná", sex, config.getSex());
        assertEquals("Chyba: hodnota parametru A není stejná", a, config.getA());
        assertEquals("Chyba: hodnota parametru H není stejná", h, config.getH());
        assertEquals("Chyba: hodnota parametru W není stejná", w, config.getW());

    }

    @Test
    public void testBuilderNegative() throws Exception {
        ConfigurationREA config = new ConfigurationREA.Builder("name")
                .sex(null)
                .build();

        assertEquals("Chyba: parametr sex je nastaven na hodnotu null", ConfigurationREA.Sex.MALE, config.getSex());
    }
}