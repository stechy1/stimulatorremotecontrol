package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.cvep;

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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEP;

public class Screen2 extends AScreen implements NumberPicker.OnValueChangeListener, View.OnClickListener, Observer, SeekBar.OnSeekBarChangeListener {

    private NumberPicker numberPicker;
    private EditText textViewPulsLength;
    private EditText textViewPulsSkew;
    private SeekBar seekBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_cvep_screen_2, container, false);

        numberPicker = (NumberPicker) v.findViewById(R.id.cvep_number_picker_output_count);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(8);
        numberPicker.setOnValueChangedListener(this);

        textViewPulsLength = (EditText) v.findViewById(R.id.cvep_edit_text_puls_length);
        textViewPulsSkew = (EditText) v.findViewById(R.id.cvep_edit_text_puls_skew);

        seekBar = (SeekBar) v.findViewById(R.id.cvep_seekbar_brightness);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(this);

        Button btnSaveValues = (Button) v.findViewById(R.id.cvep_button_save_values);
        btnSaveValues.setOnClickListener(this);

        manager.addObserver(this);

        return v;
    }

    // Number picker onValueChange
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        ConfigurationCVEP configuration = manager.getSelectedItem();

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

    // Save all onClick
    @Override
    public void onClick(View v) {
        ConfigurationCVEP configuration = manager.getSelectedItem();

        if (configuration == null)
            return;

        configuration.setPulsLength(readValue(textViewPulsLength, configuration.getPulsLength()));
        configuration.setBitShift(readValue(textViewPulsSkew, configuration.getBitShift()));
    }


    // region SeekBarListener
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser)
            return;

        ConfigurationCVEP configuration = manager.getSelectedItem();
        if (configuration == null)
            return;

        configuration.setBrightness(progress, new AItem.OnValueChanged() {
            @Override
            public void changed() {
                manager.notifySelectedItemInternalChange();
                manager.notifyValueChanged();
            }
        });
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
    // endregion

    // Manager onUpdate
    @Override
    public void update(Observable observable, Object data) {
        if (data == null) {
            return;
        }

        ConfigurationCVEP configuration = (ConfigurationCVEP) data;

        numberPicker.setValue(configuration.getOutputCount());
        textViewPulsLength.setText(configuration.getPulsLength() + "");
        textViewPulsSkew.setText(configuration.getBitShift() + "");
        seekBar.setProgress(configuration.getBrightness());

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
