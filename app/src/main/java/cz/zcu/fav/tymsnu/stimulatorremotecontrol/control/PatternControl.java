package cz.zcu.fav.tymsnu.stimulatorremotecontrol.control;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class PatternControl extends HorizontalScrollView {

    private static final String TAG = "PatternControl";


    public static final int MIN_BIT_COUNT = 1;
    public static final int MAX_BIT_COUNT = 32;

    private int bitCount;
    private final List<CheckBox> bitCheckBoxes = new ArrayList<>(MIN_BIT_COUNT);
    private final Context context;
    private final LinearLayout linearLayout;


    public PatternControl(Context context) {
        this(context, null);
    }

    public PatternControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        linearLayout = new LinearLayout(context, attrs);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        addView(linearLayout);
    }

    /**
     * Upraví počet checkboxů tak, aby odpovídal vlastnosti @bitCount
     */
    private void rearangeCheckboxes() {
        int count = bitCheckBoxes.size();
        Log.i(TAG, "Rearange checkboxes");

        if (bitCount > count) {
            for (int i = count; i < bitCount; i++) {
                CheckBox checkBox = new CheckBox(context);
                bitCheckBoxes.add(checkBox);
                linearLayout.addView(checkBox);
            }
        } else if (bitCount < count) {
            for (int i = count - 1; i > bitCount - 1; i--) {
                bitCheckBoxes.remove(i);
                linearLayout.removeViewAt(i);
            }
        }
    }


    /**
     * Vrátí počet bitů v patternu
     *
     * @return počet bitů
     */
    public int getBitCount() {
        return bitCount;
    }

    /**
     * Nastaví počet bitů patternu
     * Po nastavení se upraví počet checkboxů
     *
     * @param bitCount Počet bitů
     */
    public void setBitCount(int bitCount) {
        if (bitCount < MIN_BIT_COUNT || bitCount > MAX_BIT_COUNT || bitCount == this.bitCount)
            return;

        this.bitCount = bitCount;
        rearangeCheckboxes();
    }
}
