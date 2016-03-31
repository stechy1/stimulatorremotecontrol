package cz.zcu.fav.tymsnu.stimulatorremotecontrol.control;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

public class PatternControl extends FlowLayout{

    private static final String TAG = "PatternControl";


    public static final int MIN_BIT_COUNT = 1;
    public static final int MAX_BIT_COUNT = 32;
    public static final int DEF_CONTROL_WIDTH = 800;

    private final int mMaxWidth;
    private final List<CheckBox> bitCheckBoxes = new ArrayList<>(MAX_BIT_COUNT);
    private final Context context;

    private ValueChangeListener mListener;
    private int mBitCount;
    private int mValue;


    public PatternControl(Context context) {
        this(context, null);
    }

    public PatternControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PatternControl);
        int bitPerRow = a.getInt(R.styleable.PatternControl_bit_per_row, DEF_CONTROL_WIDTH);
        mBitCount = a.getInt(R.styleable.PatternControl_bit_count, 0);
        mValue = a.getInt(R.styleable.PatternControl_value, 0);

        mMaxWidth = bitPerRow * 96;

        rearangeCheckboxes();
        updateView();
        a.recycle();
    }

    /**
     * Upraví počet checkboxů tak, aby odpovídal vlastnosti @bitCount
     */
    private void rearangeCheckboxes() {
        int count = bitCheckBoxes.size();
        Log.i(TAG, "Rearange checkboxes");

        if (mBitCount > count) {
            for (int i = count; i < mBitCount; i++) {
                final int tmp = i;
                CheckBox checkBox = new CheckBox(context);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setBit(tmp, isChecked ? 1 : 0);
                    }
                });
                bitCheckBoxes.add(checkBox);
                addView(checkBox);
            }
        } else if (mBitCount < count) {
            for (int i = count - 1; i > mBitCount - 1; i--) {
                bitCheckBoxes.remove(i);
                removeViewAt(i);
            }
        }
    }

    private void updateView() {
        for (int i = 0; i < mBitCount; i++) {
            CheckBox checkBox = bitCheckBoxes.get(i);
            checkBox.setChecked(isBitOne(i));
        }
    }

    private void updateCheckbox(int index, int val) {
        bitCheckBoxes.get(index).setChecked(val == 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (mMaxWidth < measuredWidth) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, measureMode);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Vrátí počet bitů v patternu
     *
     * @return počet bitů
     */
    public int getBitCount() {
        return mBitCount;
    }

    /**
     * Nastaví počet bitů patternu
     * Po nastavení se upraví počet checkboxů
     *
     * @param bitCount Počet bitů
     */
    public void setBitCount(int bitCount) {
        if (bitCount < MIN_BIT_COUNT || bitCount > MAX_BIT_COUNT || bitCount == this.mBitCount)
            return;

        this.mBitCount = bitCount;
        rearangeCheckboxes();
    }

    /**
     * Vrátí hodnotu reprezentující celý pattern
     * Dekadicky nemá žádný význam, důležitá je binární podoba čisla
     *
     * @return Hodnota patternu
     */
    public int getValue() {
        return mValue;
    }

    /**
     * Nastaví hodnotu patternu
     * Důležitá je binární podoba čísla
     * 1 - aktivní, 0 - neaktivní
     * @param value Hodnota patternu
     */
    public void setValue(int value) {
        if (this.mValue == value)
            return;

        int oldValue = mValue;
        this.mValue = value;

        updateView();
        if (mListener != null)
            mListener.change(oldValue, value);
    }

    /**
     * Nastaví bit na konkrétní pozici
     * @param index Index zleva. Začíná se od nuly
     * @param value Hodnota. Přípustná je pouze 0 a 1. Cokoliv nenulového je bráno jako 1
     */
    public void setBit(int index, int value) {
        if (getBit(index) == value)
            return;

        int oldValue = this.mValue;
        if (value == 0)
            this.mValue &= ~(1 << mBitCount - index);
        else
            this.mValue |= (1 << mBitCount - index);

        updateCheckbox(index, value);
        if (mListener != null)
            mListener.change(oldValue, this.mValue);
    }

    /**
     * Vrátí bit na konkrétní pozici
     * @param index Pozice bitu
     * @return Hodnotu bitu na zadané pozici. Může vrátit pouze 0 nebo 1
     */
    public int getBit(int index) {
        return (mValue >> (mBitCount - index - 1)) & 1;
    }

    /**
     * Zjistí, zda-li bit na zadané pozici obsahuje hodnotu 0
     * @param index Zkoumaná pozice
     * @return True, pokud bit na zadané pozici obsahuje 0, jinak false
     */
    public boolean isBitZero(int index) {
        return getBit(index) == 0b0;
    }

    /**
     * Zjistí, zda-li bit na zadané pozici obsahuje hodnotu 1
     * @param index Zkoumaná pozice
     * @return True, pokud bit na zadané pozici obsahuje 1, jinak false
     */
    public boolean isBitOne(int index) {
        return getBit(index) == 0b1;
    }

    public ValueChangeListener getOnValueChangeListener() {
        return mListener;
    }

    public void setOnValueChangeListener(ValueChangeListener mListener) {
        this.mListener = mListener;
    }

    public interface ValueChangeListener {

        void change(int oldValue, int newValue);
    }
}
