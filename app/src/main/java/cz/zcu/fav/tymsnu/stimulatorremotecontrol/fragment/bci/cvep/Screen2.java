package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.cvep;

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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEP;

public class Screen2 extends AScreen
        implements OnValueChangeListener, View.OnClickListener, Observer, MySeekBar.OnMySeekBarValueChangeListener {

    private SwipeNumberPicker numberPicker;
    private EditText textViewPulsLength;
    private EditText textViewPulsSkew;
    private MySeekBar seekBar;

    private boolean notifyLock;
    private boolean editTextChanged;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_cvep_screen_2, container, false);

        numberPicker = (SwipeNumberPicker) v.findViewById(R.id.cvep_swipe_number_picker_output_count);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(8);
        numberPicker.setOnValueChangeListener(this);

        textViewPulsLength = (EditText) v.findViewById(R.id.cvep_edit_text_puls_length);
        textViewPulsSkew = (EditText) v.findViewById(R.id.cvep_edit_text_puls_skew);

        seekBar = (MySeekBar) v.findViewById(R.id.cvep_seekbar_brightness);
        seekBar.setOnMySeekBarValueChangeListener(this);

        Button btnSaveValues = (Button) v.findViewById(R.id.cvep_button_save_values);
        btnSaveValues.setOnClickListener(this);

        manager.addObserver(this);

        return v;
    }

    // Number picker onValueChange
    @Override
    public boolean onValueChange(SwipeNumberPicker picker, int oldVal, int newVal) {
        ConfigurationCVEP configuration = manager.getSelectedItem();

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

    // Save all onClick
    @Override
    public void onClick(View v) {
        ConfigurationCVEP configuration = manager.getSelectedItem();

        if (configuration == null)
            return;

        configuration.setPulsLength(readValue(textViewPulsLength, configuration.getPulsLength()), new AItem.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                editTextChanged = true;
                manager.notifySelectedItemInternalChange();
            }
        });
        configuration.setBitShift(readValue(textViewPulsSkew, configuration.getBitShift()), new AItem.OnValueChanged() {
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


    // region SeekBarListener
    @Override
    public void onChange(int value) {
        ConfigurationCVEP configuration = manager.getSelectedItem();
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

    // Manager onUpdate
    @Override
    public void update(Observable observable, Object data) {
        if (data == null) {
            return;
        }

        if (notifyLock) {
            notifyLock = false;
            return;
        }

        ConfigurationCVEP configuration = (ConfigurationCVEP) data;

        numberPicker.setValue(configuration.getOutputCount(), false);
        textViewPulsLength.setText(configuration.getPulsLength() + "");
        textViewPulsSkew.setText(configuration.getBitShift() + "");
        seekBar.setValue(configuration.getBrightness());
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

}
