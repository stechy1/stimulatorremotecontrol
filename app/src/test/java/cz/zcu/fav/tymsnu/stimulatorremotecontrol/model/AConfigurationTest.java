package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;


import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Testovací třída pro testování abstraktní konfigurace
 */
public class AConfigurationTest {

    private static final String CONFIG_NAME = "default";

    private AConfiguration<ConfigurationImpl> configuration;

    @Before
    public void setUp() {
        configuration = new ConfigurationImpl(CONFIG_NAME);
    }

    @Test
    public void testGetNamePositive() throws Exception {
        assertEquals("Chyba, metoda nevrátila očekávané jméno", CONFIG_NAME, configuration.getName());
    }

    @Test
    public void testSetNamePositive1() throws Exception {
        String newName = "myName";
        configuration.setName(newName);
        assertEquals("Chyba: metoda nenastavila požadované jméno", newName, configuration.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNameNegative4() throws Exception {
        configuration.setName(null);
        System.out.println("Chyba: metoda nastavila prázdné jméno");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNameNegative1() throws Exception {
        String newName = "";
        configuration.setName(newName);
        System.out.println("Chyba: metoda nastavila prázdné jméno");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNameNegative2() throws Exception {
        String newName = "můjNázev";
        configuration.setName(newName);
        System.out.println("Chyba: metoda nastavila chybné jméno");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNameNegative3() throws Exception {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 33; i++)
            sb.append((char) 65 + rand.nextInt(25));
        configuration.setName(sb.toString());
        System.out.println("Chyba: metoda nastavila jméno, které je příliš dlouhé");
    }

    @Test
    public void testGetOutputCountPositive() throws Exception {
        assertEquals("Chyba: metoda vrátíla špatný počet výstupů", AConfiguration.DEF_OUTPUT_COUNT, configuration.getOutputCount());
    }

    @Test
    public void testSetOutputCountPositive1() throws Exception {
        int newCount = 3;
        configuration.setOutputCount(newCount);
        assertEquals("Chyba: metoda nenastavila požadovaný počet výstupů", newCount, configuration.getOutputCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetOutputCountNegative2() throws Exception {
        int newCount = AConfiguration.MIN_OUTPUT_COUNT - 1;
        configuration.setOutputCount(newCount);
        System.out.println("Chyba: metoda nastavila nulový počet výstupů");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetOutputCountNegative3() throws Exception {
        int newCount = AConfiguration.MAX_OUTPUT_COUNT + 1;
        configuration.setOutputCount(newCount);
        System.out.println("Chyba: metoda nastavila počet výstupů, který je mimo rozsah");
    }

    @Test
    public void testDuplicatePositive() throws Exception {
        String newName = "duplicated";
        ConfigurationImpl duplicated = configuration.duplicate(newName);
        assertEquals("Chyba: duplikovaná konfigurace nemá stejný počet výstupů", duplicated.outputCount, configuration.outputCount);
    }

    @Test
    public void testEqualsPositive1() throws Exception {
        assertEquals("Chyba: objekty se neshodují v referencích", configuration, configuration);
    }

    @Test
    public void testEqualsPositive2() throws Exception {
        ConfigurationImpl config = new ConfigurationImpl(CONFIG_NAME);
        assertEquals("Chyba: objekty se neshodují", configuration, config);
    }

    @Test
    public void testEqualsPositive3() throws Exception {
        ConfigurationImpl config = new ConfigurationImpl("config");
        assertNotEquals("Chyba: objekty se schodují", configuration, config);

    }

    @Test
    public void testHashcodePositive() throws Exception {
        ConfigurationImpl config = new ConfigurationImpl(CONFIG_NAME);
        assertEquals("Chyba: konfigurace nemají stejný hash hod", configuration.hashCode(), config.hashCode());
    }

    @Test
    public void testHashCodeNegative() throws Exception {
        ConfigurationImpl config = new ConfigurationImpl("test");
        assertNotEquals("Chyba: konfigurace mají stejný", configuration.hashCode(), config.hashCode());

    }

    private static final class ConfigurationImpl extends AConfiguration<ConfigurationImpl> {

        public ConfigurationImpl(String name) {
            super(name);
        }

        public ConfigurationImpl(String name, int outputCount) {
            super(name, outputCount);
        }

        @Override
        public ConfigurationImpl duplicate(String newName) {
            return new ConfigurationImpl(newName, this.outputCount);
        }
    }
}