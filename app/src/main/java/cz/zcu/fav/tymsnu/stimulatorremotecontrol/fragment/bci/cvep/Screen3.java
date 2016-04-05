package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.cvep;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.control.PatternControl;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AItem;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEP;

public class Screen3 extends AScreen implements Observer, PatternControl.ValueChangeListener {

    private PatternControl patternControl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_cvep_screen_3, container, false);

        patternControl = (PatternControl) v.findViewById(R.id.cvep_pattern_control);
        patternControl.setOnValueChangeListener(this);

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

    @Override
    public void update(Observable observable, Object data) {
        if (data == null)
            return;

        ConfigurationCVEP configuration = (ConfigurationCVEP) data;

        patternControl.setValue(configuration.getMainPattern().getValue());
    }

    @Override
    public void change(int oldValue, int newValue) {
        ConfigurationCVEP configuration = manager.getSelectedItem();

        if (configuration == null)
            return;

        configuration.setMainPattern(newValue, new AItem.OnValueChanged() {
            @Override
            public void changed() {
                manager.notifySelectedItemInternalChange();
            }
        });
    }
}
