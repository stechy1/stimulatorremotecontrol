package cz.zcu.fav.tymsnu.stimulatorremotecontrol.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

public class MySeekBar extends RelativeLayout implements SeekBar.OnSeekBarChangeListener {

    private final TextView mActValue;
    private final SeekBar  mSeekBar;


    private OnMySeekBarValueChangeListener mListener;


    public MySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(getContext()).inflate(
                R.layout.control_my_seekbar, this);

        TypedArray a = getContext(). obtainStyledAttributes(attrs, R.styleable.MySeekBar);
        int maxValue = a.getInt(R.styleable.MySeekBar_max_value, 100);
        int actValue = a.getInt(R.styleable.MySeekBar_act_value, 0);

        mActValue = (TextView) findViewById(R.id.seekBar_value);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);

        mActValue.setText(actValue + "");
        mSeekBar.setMax(maxValue);
        mSeekBar.setProgress(actValue);

        mSeekBar.setOnSeekBarChangeListener(this);

        a.recycle();

    }

    public void setValue(int val) {
        if (mSeekBar.getProgress() == val)
            return;

        mSeekBar.setProgress(val);
    }

    public void setOnMySeekBarValueChangeListener(OnMySeekBarValueChangeListener listener) {
        mListener = listener;
    }

    // region SeekBar listeners
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mActValue.setText(progress + "");

        if (mListener != null)
            mListener.onChange(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
    // endregion

    public interface OnMySeekBarValueChangeListener {
        void onChange(int value);
    }
}
