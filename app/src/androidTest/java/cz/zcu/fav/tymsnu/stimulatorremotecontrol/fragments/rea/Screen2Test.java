package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.rea;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.MainActivity;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.action.NavigationViewActions.navigateTo;

/**
 * Testovací třída pro otestování GUI v REA 2. screenu
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class Screen2Test {

    @Rule
    public ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        // Otevřít postranní menu
        onView(withId(R.id.activity_drawer_layout)).perform(DrawerActions.open());
        // Kliknout na položku REA
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_item_3));
        // Swipe na další screen
        onView(withId(R.id.universal_view_pager)).perform(swipeLeft());
    }

    @Test
    public void testCheckControls() throws Exception {
        onView(withId(R.id.rea_screen_2_swipe_number_picker_output_count)).check(matches(isDisplayed()));
        onView(withId(R.id.rea_screen_2_edit_text_cycle_count)).check(matches(isDisplayed()));
        onView(withId(R.id.rea_screen_2_edit_text_wait_fixed)).check(matches(isDisplayed()));
        onView(withId(R.id.rea_screen_2_edit_text_wait_random)).check(matches(isDisplayed()));
        onView(withId(R.id.rea_screen_2_edit_text_miss_time)).check(matches(isDisplayed()));
        onView(withId(R.id.rea_screen_2_seek_bar_brightness)).check(matches(isDisplayed()));
        onView(withId(R.id.universal_title)).check(matches(withText(R.string.screen_title_settings_1)));
    }

}
