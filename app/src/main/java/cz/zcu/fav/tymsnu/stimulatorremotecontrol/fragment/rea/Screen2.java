package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.rea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vi.swipenumberpicker.OnValueChangeListener;
import com.vi.swipenumberpicker.SwipeNumberPicker;

import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.control.MySeekBar;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleScreen;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfiguration;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationREA;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.EditTextReader;

public class Screen2 extends ASimpleScreen<ConfigurationREA>
        implements Observer, View.OnClickListener, OnValueChangeListener, MySeekBar.OnMySeekBarValueChangeListener {

    private SwipeNumberPicker swipeNumberPicker;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private MySeekBar mySeekBar;

    private boolean notifyLock;
    private boolean editTextChanged;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rea_screen_2, container, false);

        swipeNumberPicker = (SwipeNumberPicker) v.findViewById(R.id.rea_screen_2_swipe_number_picker_output_count);
        swipeNumberPicker.setMinValue(1);
        swipeNumberPicker.setMaxValue(8);
        swipeNumberPicker.setOnValueChangeListener(this);

        editText1 = (EditText) v.findViewById(R.id.rea_screen_2_edit_text_cycle_count);
        editText2 = (EditText) v.findViewById(R.id.rea_screen_2_edit_text_wait_fixed);
        editText3 = (EditText) v.findViewById(R.id.rea_screen_2_edit_text_wait_random);
        editText4 = (EditText) v.findViewById(R.id.rea_screen_2_edit_text_miss_time);

        mySeekBar = (MySeekBar) v.findViewById(R.id.rea_screen_2_seek_bar_brightness);
        mySeekBar.setOnMySeekBarValueChangeListener(this);

        Button btnSaveAll = (Button) v.findViewById(R.id.rea_screen_2_button_save_all);
        btnSaveAll.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        manager.addObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        manager.deleteObserver(this);
    }


    @Override
    public void update(Observable observable, Object data) {
        if (data == null)
            return;

        if (notifyLock) {
            notifyLock = false;
            return;
        }

        ConfigurationREA configuration = (ConfigurationREA) data;

        swipeNumberPicker.setValue(configuration.getOutputCount(), false);
        editText1.setText(String.valueOf(configuration.getCycleCount()));
        editText2.setText(String.valueOf(configuration.getWaitFixed()));
        editText3.setText(String.valueOf(configuration.getWaitRandom()));
        editText4.setText(String.valueOf(configuration.getMissTime()));

        mySeekBar.setValue(configuration.getBrightness());
    }

    // On button save all click
    @Override
    public void onClick(View v) {
        ConfigurationREA configuration = manager.getSelectedItem();

        if (configuration == null)
            return;

        configuration.setCycleCount(EditTextReader.readValue(editText1, configuration.getCycleCount()), new AConfiguration.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                editTextChanged = true;
                manager.notifySelectedItemInternalChange();
            }
        });
        configuration.setWaitFixed(EditTextReader.readValue(editText2, configuration.getWaitFixed()), new AConfiguration.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                editTextChanged = true;
                manager.notifySelectedItemInternalChange();
            }
        });
        configuration.setWaitRandom(EditTextReader.readValue(editText3, configuration.getWaitRandom()), new AConfiguration.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                editTextChanged = true;
                manager.notifySelectedItemInternalChange();
            }
        });
        configuration.setMissTime(EditTextReader.readValue(editText4, configuration.getMissTime()), new AConfiguration.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                editTextChanged = true;
                manager.notifySelectedItemInternalChange();
            }
        });

        if (editTextChanged) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.values_were_saved, configuration.getName()), Snackbar.LENGTH_SHORT).show();
            editTextChanged = false;
        }
    }

    // On swipe number picker value change
    @Override
    public boolean onValueChange(SwipeNumberPicker view, int oldValue, int newValue) {
        ConfigurationREA configuration = manager.getSelectedItem();

        if (configuration == null)
            return false;

        configuration.setOutputCount(newValue, new AConfiguration.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                manager.notifySelectedItemInternalChange();
            }
        });

        return true;
    }

    // On my seek bar value change
    @Override
    public void onChange(int value) {
        ConfigurationREA configuration = manager.getSelectedItem();

        if (configuration == null)
            return;

        configuration.setBrightness(value, new AConfiguration.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                manager.notifySelectedItemInternalChange();
            }
        });
    }
}
