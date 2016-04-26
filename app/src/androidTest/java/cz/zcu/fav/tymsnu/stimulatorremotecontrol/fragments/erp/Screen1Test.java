package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.erp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.MainActivity;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.action.NavigationViewActions.navigateTo;

/**
 * Testovací třída pro testovaní GUI v ERP 1. screenu
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class Screen1Test {

    @Rule
    public ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        // Otevřít postranní menu
        openDrawer(R.id.activity_drawer_layout);
        // Kliknout na položku ERP
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_item_1));
    }

    @Test
    public void testCheckControls() throws Exception {
        onView(withId(R.id.universal_title)).check(matches(withText(R.string.screen_title_schema)));
        onView(withId(R.id.universal_screen_1_container_listview)).check(matches(isDisplayed()));
        onView(withId(R.id.universal_screen_1_btn_new_configuration)).check(matches(isDisplayed()));
        onView(withId(R.id.universal_screen_1_btn_save_all)).check(matches(isDisplayed()));
        onView(withId(R.id.universal_screen_1_btn_play)).check(matches(isDisplayed()));
        onView(withId(R.id.universal_screen_1_btn_stop)).check(matches(isDisplayed()));
    }

    @Test
    public void testNewConfigurationClick() throws Exception {
        // Klikneme na tlačítko nová konfigurace
        onView(withId(R.id.universal_screen_1_btn_new_configuration)).perform(click());

        // Kontrola, že se zobrazí dialog pro zadání názvu nové konfigurace
        onView(withText(R.string.context_set_name)).check(matches(isDisplayed()));

        onView(withHint("nazev konfigurace")).perform(typeText("test"));

        onView(withText("OK")).perform(click());
    }
}
