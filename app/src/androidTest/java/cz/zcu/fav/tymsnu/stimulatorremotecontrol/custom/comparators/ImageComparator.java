package cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.comparators;

import android.view.View;

import org.hamcrest.Matcher;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.matchers.DrawableMatcher;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.matchers.VectorDrawableMatcher;


/**
 * Pomocná knihovní třída pro porovnání obrázků
 */
public class ImageComparator {

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> withVectorDrawable(final int resourceId) {
        return new VectorDrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }

    public static Matcher<View> noVectorDrawable() {
        return new VectorDrawableMatcher(-1);
    }
}
