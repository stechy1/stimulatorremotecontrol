package cz.zcu.fav.tymsnu.stimulatorremotecontrol;

import android.support.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Hlavní třída pro spuštění testů pro GUI
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.erp.Screen1Test.class,
        cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.erp.Screen2Test.class,
        cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.erp.Screen3Test.class

})
@LargeTest
public class UITest {
}
