package cz.zcu.fav.tymsnu.stimulatorremotecontrol;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.closeDrawer;
import static android.support.test.espresso.contrib.DrawerActions.openDrawer;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.action.NavigationViewActions.navigateTo;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HelloWorldEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testDrawerTest() throws Exception {
        // Otevřít postranní menu
        openDrawer(R.id.activity_drawer_layout);
        // Zkontrolovat, že je ovevřený
        onView(withId(R.id.activity_drawer_layout)).check(matches(isOpen()));
        // Kliknout na položku ERP
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_item_1));
        onView(withId(R.id.universal_title)).check(matches(withText(R.string.screen_title_schema)));

        openDrawer(R.id.activity_drawer_layout);
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_item_2_1));
        onView(withId(R.id.universal_title)).check(matches(withText(R.string.screen_title_fvep)));

        openDrawer(R.id.activity_drawer_layout);
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_item_2_2));
        onView(withId(R.id.universal_title)).check(matches(withText(R.string.screen_title_tvep)));

        openDrawer(R.id.activity_drawer_layout);
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_item_2_3));
        onView(withId(R.id.universal_title)).check(matches(withText(R.string.screen_title_cvep)));

        openDrawer(R.id.activity_drawer_layout);
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_item_3));
        onView(withId(R.id.universal_title)).check(matches(withText(R.string.screen_title_rea)));

        closeDrawer(R.id.activity_drawer_layout);
    }
}
