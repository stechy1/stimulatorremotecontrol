package cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.comparators;


import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ListViewComparator {

    public static Matcher<View> withNoData() {
        return withListSize(0);
    }

    public static Matcher<View> withListSize(final int size) {
        return new TypeSafeMatcher<View>() {
            @Override public boolean matchesSafely (final View view) {
                return view instanceof ListView && ((ListView) view).getChildCount() == size;
            }

            @Override public void describeTo (final Description description) {
                description.appendText("ListView should have " + size + " items");
            }
        };
    }

    /*public static Matcher<View> containsItemWithText(final String text) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                if (listView.getChildCount() == 0)
                    return false;

                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("ListView should contains item with text " + text);
            }
        };
    }*/
}
