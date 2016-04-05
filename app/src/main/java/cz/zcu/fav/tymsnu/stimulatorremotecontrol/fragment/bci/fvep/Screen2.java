package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vi.swipenumberpicker.OnValueChangeListener;
import com.vi.swipenumberpicker.SwipeNumberPicker;

import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AItem;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFVEP;

public class Screen2 extends AScreen implements OnValueChangeListener, Observer {

    private SwipeNumberPicker numberPicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_fvep_screen_2, container, false);

        numberPicker = (SwipeNumberPicker) v.findViewById(R.id.fvep_screen_2_swipe_number_picker);
        numberPicker.setMaxValue(ConfigurationFVEP.MAX_OUTPUT_COUNT);
        numberPicker.setMinValue(ConfigurationFVEP.MIN_OUTPUT_COUNT);
        numberPicker.setValue(1, false);
        numberPicker.setOnValueChangeListener(this);

        ConfigurationFVEP configuration = manager.getSelectedItem();
        if (configuration != null)
            readValues(configuration);

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

    private void readValues(ConfigurationFVEP configuration) {
        int current = numberPicker.getValue();
        if (current == configuration.getOutputCount())
            return;

        numberPicker.setValue(configuration.getOutputCount(), false);
    }

    @Override
    public boolean onValueChange(SwipeNumberPicker picker, int oldVal, int newVal) {
        ConfigurationFVEP configuration = manager.getSelectedItem();
        if (configuration == null)
            return false;

        configuration.setOutputCount(newVal, new AItem.OnValueChanged() {
            @Override
            public void changed() {
                manager.notifySelectedItemInternalChange();
                manager.notifyValueChanged();
            }
        });

        return true;
    }

    // Při aktualizaci datasetu v manageru (Změna schématu, změna nastavení výstupů...)
    @Override
    public void update(Observable observable, Object data) {
        ConfigurationFVEP configuration = (ConfigurationFVEP) data;
        if (configuration == null) {
            numberPicker.setValue(numberPicker.getMinValue(), false);
            return;
        }

        readValues(configuration);
    }
}
