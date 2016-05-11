package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragments.erp;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.activity.MainActivity;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.action.NavigationViewActions.navigateTo;
import static cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.comparators.ImageComparator.withVectorDrawable;
import static cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.comparators.ListViewComparator.withNoData;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Třída pro otestování funkčnosti konfigurací pro ERP
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
public class ERPFunctionTest {

    @Rule
    public ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        // Otevřít postranní menu
        onView(withId(R.id.activity_drawer_layout)).perform(DrawerActions.open());
        // Kliknout na položku ERP
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_item_1));
    }

    @Test
    public void testCreateNewConfiguration() throws Exception {
        // Vytvoření nové konfigurace
        // Klikneme na tlačítko nová konfigurace
        onView(withId(R.id.universal_screen_1_btn_new_configuration)).perform(click());

        // Kontrola, že se zobrazí dialog pro zadání názvu nové konfigurace
        onView(withText(R.string.context_set_name)).check(matches(isDisplayed()));

        // Napišeme název konfigurace do příslušného inputu
        onView(withHint("nazev konfigurace")).perform(typeText("test"));

        // Potvrdíme tlačítkem OK
        onView(withText("OK")).perform(click());

        // Kontrola, že nová konfigurace není vybraná
        onView(allOf(
                withId(R.id.control_list_view_image),
                withParent(withId(R.id.control_scheme_view_item)))
        ).check(matches(withVectorDrawable(R.drawable.checkbox_blank_outline)));
        // Kontrola, že se u nové konfigurace zobrazuje počet výstupů s hodnotou 4

        // Dlouze kliknu na konfiguraci, která se jmenuje test
        onData(anything()).inAdapterView(withId(R.id.universal_screen_1_container_listview)).onChildView(withText("test")).perform(longClick());

        // Kliknu na tlačítko smazat
        onView(withText(R.string.delete)).perform(click());

        // Ověřím, že v listView nebude smazaná konfigurace
        onView(withId(R.id.universal_screen_1_container_listview)).check(matches(withNoData()));
        //onData(anything()).inAdapterView(withId(R.id.universal_screen_1_container_listview)).check(matches(withNoData()));
    }
}
