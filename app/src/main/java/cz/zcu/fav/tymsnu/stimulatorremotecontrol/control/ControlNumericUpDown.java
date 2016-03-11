package cz.zcu.fav.tymsnu.stimulatorremotecontrol.control;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.regex.Pattern;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

public class ControlNumericUpDown extends LinearLayout {

    private static final Pattern numPattern = Pattern.compile("^[0-9]+$");
    private static int counter = 0;

    //region Variables
    private final Object lock = new Object();
    private boolean canChange = true;

    private OnValueChangeListener onValueChangeListener;

    private int oldVal;
    private final EditText editText;
    private final TextView label;
    private int val;
    private int step ;
    private int minVal;
    private int maxVal;
    //endregion

    //region Constructors
    public ControlNumericUpDown(Context context) {
        this(context, null);
    }

    public ControlNumericUpDown(Context context, AttributeSet attrs) {
        this(context, attrs, "NumericUpDown_" + counter++, 0, 1, 0, 100, null);
    }

    private ControlNumericUpDown(Context context, AttributeSet attrs, String label, final int val, int step, int min, int max, OnValueChangeListener onValueChangeListener) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.control_numeric_up_down, this, true);

        editText = (EditText) v.findViewById(R.id.control_numeric_up_down_value);
        editText.setHint("LED");

        this.label = (TextView) v.findViewById(R.id.control_numeric_up_down_label);
        this.label.setText(label);

        editText.setText("" + val);
        setStep(step);
        setMinVal(min);
        setMaxVal(max);
        setOnValueChangeListener(onValueChangeListener);

        Button addBtn = (Button) v.findViewById(R.id.control_numeric_up_down_add);
        Button oddBtn = (Button) v.findViewById(R.id.control_numeric_up_down_odd);

        addBtn.setOnClickListener(addListener);
        oddBtn.setOnClickListener(oddListener);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!canChange)
                    return;

                if (numPattern.matcher(s).matches()) {
                    oldVal = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!canChange)
                    return;

                if (numPattern.matcher(s).matches()) {
                    int newVal = Integer.parseInt(s.toString());
                    if (oldVal != newVal)
                        setValueInternal(newVal, true, false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    //endregion


    private void setValueInternal(int val, boolean notify) {
        setValueInternal(val, notify, true);
    }

    //region Private Methods
    private void setValueInternal(int val, boolean notify, boolean viewValue) {
        if (this.val == val)
            return;

        oldVal = this.val;
        this.val = val;
        if (notify)
            notifyChange(oldVal, val);

        if (viewValue)
            viewValue(val);
    }

    private void notifyChange(int previous, int current) {
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(this, previous, current);
        }
    }

    private void viewValue(int val) {
        canChange = false;
        editText.setText("" + val);
        canChange = true;
    }
    //endregion

    // region Public methods

    // endregion


    //region Getters & Setters
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getMinVal() {
        return minVal;
    }

    public void setMinVal(int minVal) {
        this.minVal = minVal;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        setValueInternal(val, true);
    }

    public String getLabel() {
        return label.getText().toString();
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    //endregion

    //region Listeners
    private final OnClickListener addListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            synchronized (lock) {
                int val = getVal();
                if (val >= maxVal)
                    return;

                setValueInternal(++val, true);
            }
        }
    };

    private final OnClickListener oddListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            synchronized (lock) {
                int val = getVal();
                if (val <= minVal)
                    return;

                setValueInternal(--val, true);
            }
        }
    };
    //endregion

    public static class Builder {
        private String label = "NumericUpDown";
        private int step = 1;
        private int min = 0;
        private int max = 100;
        private int val = min;
        private OnValueChangeListener onValueChangeListener;

        public Builder() {}

        public ControlNumericUpDown build(Context context) {
            return new ControlNumericUpDown(context, null, label, val, step, min, max, onValueChangeListener);
        }
        public ControlNumericUpDown build(Context context, AttributeSet attrs) {
            return new ControlNumericUpDown(context, attrs, label, val, step, min, max, onValueChangeListener);
        }

        public Builder step(int step) {
            this.step = step;

            return this;
        }
        public Builder min(int min) {
            this.min = min;

            return this;
        }
        public Builder max(int max) {
            this.max = max;

            return this;
        }
        public Builder value(int val) {
            this.val = val;

            return this;
        }
        public Builder label(String label) {
            this.label = label;

            return this;
        }
        public Builder setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
            this.onValueChangeListener = onValueChangeListener;

            return this;
        }
    }

    public interface OnValueChangeListener {
        void onValueChange(ControlNumericUpDown numericUpDown, int oldVal, int newVal);
    }


}
