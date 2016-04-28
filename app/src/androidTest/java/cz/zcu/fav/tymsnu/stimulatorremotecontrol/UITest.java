package cz.zcu.fav.tymsnu.stimulatorremotecontrol;

import android.support.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.bci.BCITests;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.erp.ERPTests;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.rea.REATests;


/**
 * Hlavní třída pro spuštění testů pro GUI
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ERPTests.class,
        BCITests.class,
        REATests.class
})
@LargeTest
public class UITest {
}
