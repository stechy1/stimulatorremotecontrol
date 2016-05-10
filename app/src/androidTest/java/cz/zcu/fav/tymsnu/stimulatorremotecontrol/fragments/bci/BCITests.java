package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.bci;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.bci.cvep.CVEPTests;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.bci.fvep.FVEPTests;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.bci.tvep.TVEPTests;

/**
 * Hlavní třída pro testování BCI GUI
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        FVEPTests.class,
        TVEPTests.class,
        CVEPTests.class,
})
public class BCITests {
}
