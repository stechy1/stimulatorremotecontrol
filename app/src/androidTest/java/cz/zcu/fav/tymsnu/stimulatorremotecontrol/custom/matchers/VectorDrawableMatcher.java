package cz.zcu.fav.tymsnu.stimulatorremotecontrol.custom.matchers;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher pro kontrolu stejných VectorDrawable tříd
 */
public class VectorDrawableMatcher extends TypeSafeMatcher<View> {

    private final int expectedId;
    String resourceName;

    public VectorDrawableMatcher(int expectedId) {
        super(View.class);
        this.expectedId = expectedId;
    }

    @Override
    protected boolean matchesSafely(View target) {
        if (!(target instanceof ImageView)) {
            return false;
        }
        ImageView imageView = (ImageView) target;
        if (expectedId < 0) {
            return imageView.getDrawable() == null;
        }
        Resources resources = target.getContext().getResources();
        Drawable expectedDrawable = ResourcesCompat.getDrawable(resources, expectedId, null);
        resourceName = resources.getResourceEntryName(expectedId);

        if (expectedDrawable == null)
            return false;

        return imageView.getDrawable().getConstantState().equals(expectedDrawable.getConstantState());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with vector drawable from resource id: ");
        description.appendValue(expectedId);
        if (resourceName != null) {
            description.appendText("[");
            description.appendText(resourceName);
            description.appendText("]");
        }
    }
}
