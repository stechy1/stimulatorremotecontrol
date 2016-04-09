package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.tvep;

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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AItem;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.EditTextReader;

public class Screen2 extends AScreen implements Observer, MySeekBar.OnMySeekBarValueChangeListener, View.OnClickListener {

    private SwipeNumberPicker numberPickerOutputCount;
    private SwipeNumberPicker numberPickerPatternLength;
    private EditText editTextPulsLength;
    private EditText editTextBitSkew;
    private MySeekBar seekBarBrightness;

    private boolean notifyLock;
    private boolean editTextChanged;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_tvep_screen_2, container, false);

        numberPickerOutputCount = (SwipeNumberPicker) v.findViewById(R.id.bci_tvep_screen_2_swipe_number_picker_output_count);
        numberPickerOutputCount.setMinValue(1);
        numberPickerOutputCount.setMaxValue(8);
        numberPickerOutputCount.setOnValueChangeListener(new OnNumberPickerOutputCountListener());

        numberPickerPatternLength = (SwipeNumberPicker) v.findViewById(R.id.bci_tvep_screen_2_swipe_number_picker_pattern_length);
        numberPickerPatternLength.setMinValue(1);
        numberPickerPatternLength.setMaxValue(16);
        numberPickerPatternLength.setOnValueChangeListener(new OnNumberPickerPatternLengthListener());

        editTextPulsLength = (EditText) v.findViewById(R.id.bci_tvep_screen_2_edit_text_puls_length);
        editTextBitSkew = (EditText) v.findViewById(R.id.bci_tvep_screen_2_time_between);

        seekBarBrightness = (MySeekBar) v.findViewById(R.id.bci_tvep_screen_2_seek_bar_brightness);
        seekBarBrightness.setOnMySeekBarValueChangeListener(this);

        Button btnSaveValues = (Button) v.findViewById(R.id.tvep_button_save_values);
        btnSaveValues.setOnClickListener(this);

        //manager.addObserver(this);

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

    // region SeekBarListener
    @Override
    public void onChange (int value) {
        ConfigurationTVEP configuration = manager.getSelectedItem();
        if (configuration == null)
            return;

        configuration.setBrightness(value, new AItem.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                manager.notifySelectedItemInternalChange();
            }
        });
    }
    // endregion

    // Save all btn click
    @Override
    public void onClick(View v) {
        ConfigurationTVEP configuration = manager.getSelectedItem();

        if (configuration == null)
            return;

        configuration.setPulsLength(EditTextReader.readValue(editTextPulsLength, configuration.getPulsLength()), new AItem.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                editTextChanged = true;
                manager.notifySelectedItemInternalChange();
            }
        });
        configuration.setPulsSkew(EditTextReader.readValue(editTextBitSkew, configuration.getPulsSkew()), new AItem.OnValueChanged() {
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

    // Manager onUpdate
    @Override
    public void update(Observable observable, Object data) {
        if (data == null)
            return;

        if (notifyLock) {
            notifyLock = false;
            return;
        }

        ConfigurationTVEP configuration = (ConfigurationTVEP) data;

        numberPickerOutputCount.setValue(configuration.getOutputCount(), false);
        numberPickerPatternLength.setValue(configuration.getPatternLength(), false);
        editTextPulsLength.setText(String.valueOf(configuration.getPulsLength()));
        editTextBitSkew.setText(String.valueOf(configuration.getPulsSkew()));
        seekBarBrightness.setValue(configuration.getBrightness());
    }

    private final class OnNumberPickerOutputCountListener implements OnValueChangeListener {

        @Override
        public boolean onValueChange(SwipeNumberPicker picker, int oldVal, int newVal) {
            ConfigurationTVEP configuration = manager.getSelectedItem();
            if (configuration == null)
                return false;

            configuration.setOutputCount(newVal, new AItem.OnValueChanged() {
                @Override
                public void changed() {
                    notifyLock = true;
                    manager.notifySelectedItemInternalChange();
                    manager.notifyValueChanged();
                }
            });

            return true;
        }
    }

    private final class OnNumberPickerPatternLengthListener implements OnValueChangeListener {

        @Override
        public boolean onValueChange(SwipeNumberPicker picker, int oldVal, int newVal) {
            ConfigurationTVEP configuration = manager.getSelectedItem();
            if (configuration == null)
                return false;

            configuration.setPatternLength(newVal, new AItem.OnValueChanged() {
                @Override
                public void changed() {
                    notifyLock = true;
                    manager.notifySelectedItemInternalChange();
                    manager.notifyValueChanged();
                }
            });

            return true;
        }
    }
}
