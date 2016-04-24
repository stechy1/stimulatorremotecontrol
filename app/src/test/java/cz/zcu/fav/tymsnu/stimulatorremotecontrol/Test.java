package cz.zcu.fav.tymsnu.stimulatorremotecontrol;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.CodeTest;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.DataConvertorTest;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.PacketTest;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfigurationTest;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEPTest;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERPTest;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFVEPTest;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationREATest;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEPTest;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.RangeUtilsParametrizedTest;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.RangeUtilsSingleTest;

/**
 * Hlavní testovací třída, spouští všechny ostatní testy
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AConfigurationTest.class,
        ConfigurationERPTest.class,
        ConfigurationFVEPTest.class,
        ConfigurationTVEPTest.class,
        ConfigurationCVEPTest.class,
        ConfigurationREATest.class,
        RangeUtilsSingleTest.class,
        RangeUtilsParametrizedTest.class,
        PacketTest.class,
        DataConvertorTest.class,
        CodeTest.class
})
public class Test {
}
