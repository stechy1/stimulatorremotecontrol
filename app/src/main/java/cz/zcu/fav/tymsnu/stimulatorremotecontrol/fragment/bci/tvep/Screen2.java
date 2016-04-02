package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.tvep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AItem;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;

public class Screen2 extends AScreen implements Observer, SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private NumberPicker numberPickerOutputCount;
    private NumberPicker numberPickerPatternLength;
    private EditText editTextPulsLength;
    private EditText editTextBitSkew;
    private SeekBar seekBarBrightness;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_tvep_screen_2, container, false);

        numberPickerOutputCount = (NumberPicker) v.findViewById(R.id.bci_tvep_screen_2_number_picker_output_count);
        numberPickerOutputCount.setMinValue(1);
        numberPickerOutputCount.setMaxValue(8);
        numberPickerOutputCount.setOnValueChangedListener(new OnNumberPickerOutputCountListener());

        numberPickerPatternLength = (NumberPicker) v.findViewById(R.id.bci_tvep_screen_2_number_picker_pattern_length);
        numberPickerPatternLength.setMinValue(1);
        numberPickerPatternLength.setMaxValue(16);
        numberPickerPatternLength.setOnValueChangedListener(new OnNumberPickerPatternLengthListener());

        editTextPulsLength = (EditText) v.findViewById(R.id.bci_tvep_screen_2_edit_text_puls_length);
        editTextBitSkew = (EditText) v.findViewById(R.id.bci_tvep_screen_2_bit_skew);

        seekBarBrightness = (SeekBar) v.findViewById(R.id.bci_tvep_screen_2_seek_bar_brightness);
        seekBarBrightness.setMax(100);
        seekBarBrightness.setOnSeekBarChangeListener(this);

        Button btnSaveValues = (Button) v.findViewById(R.id.tvep_button_save_values);
        btnSaveValues.setOnClickListener(this);

        manager.addObserver(this);

        return v;
    }

    // region SeekBarListener
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser)
            return;

        ConfigurationTVEP configuration = manager.getSelectedItem();
        if (configuration == null)
            return;

        configuration.setBrightness(progress, new AItem.OnValueChanged() {
            @Override
            public void changed() {
                manager.notifySelectedItemInternalChange();
            }
        });
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
    // endregion

    // Save all btn click
    @Override
    public void onClick(View v) {
        ConfigurationTVEP configuration = manager.getSelectedItem();

        if (configuration == null)
            return;

        configuration.setPulsLength(readValue(editTextPulsLength, configuration.getPulsLength()), new AItem.OnValueChanged() {
            @Override
            public void changed() {
                manager.notifySelectedItemInternalChange();
            }
        });
        configuration.setPulsSkew(readValue(editTextBitSkew, configuration.getPulsSkew()), new AItem.OnValueChanged() {
            @Override
            public void changed() {
                manager.notifySelectedItemInternalChange();
            }
        });
    }

    // Manager onUpdate
    @Override
    public void update(Observable observable, Object data) {
        if (data == null) {
            return;
        }

        ConfigurationTVEP configuration = (ConfigurationTVEP) data;

        numberPickerOutputCount.setValue(configuration.getOutputCount());
        numberPickerPatternLength.setValue(configuration.getPatternLength());
        editTextPulsLength.setText(configuration.getPulsLength() + "");
        editTextBitSkew.setText(configuration.getPulsSkew() + "");
        seekBarBrightness.setProgress(configuration.getBrightness());
    }

    /**
     * Přečte hodnotu z inputu
     * Pokud se nepodaří hodnotu naparsovat, tak vrátí 0
     * @param input Vstup
     * @return číslo
     */
    private int readValue(EditText input) {return readValue(input, 0);}

    /**
     * Přečte hodnotu z inputu
     * Pokud se nepodaří hodnotu naparsovat, tak vrátí výchozí hodnotu
     * @param input Vstup
     * @param def Výchozí hodnota
     * @return číslo
     */
    private int readValue(EditText input, int def) {
        String text = input.getText().toString();
        try {
            return Integer.parseInt(text);
        } catch (Exception ex) {
            input.setText("" + def);
            return def;
        }
    }

    private final class OnNumberPickerOutputCountListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            ConfigurationTVEP configuration = manager.getSelectedItem();
            if (configuration == null)
                return;

            configuration.setOutputCount(newVal, new AItem.OnValueChanged() {
                @Override
                public void changed() {
                    manager.notifySelectedItemInternalChange();
                    manager.notifyValueChanged();
                }
            });
        }
    }

    private final class OnNumberPickerPatternLengthListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            ConfigurationTVEP configuration = manager.getSelectedItem();
            if (configuration == null)
                return;

            configuration.setPatternLength(newVal, new AItem.OnValueChanged() {
                @Override
                public void changed() {
                    manager.notifySelectedItemInternalChange();
                    manager.notifyValueChanged();
                }
            });
        }
    }
}
