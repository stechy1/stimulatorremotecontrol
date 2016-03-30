package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AItem;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP;

public final class Screen2 extends AScreen
        implements NumberPicker.OnValueChangeListener, Observer {

    private static final String TAG = "Screen2";

    private Spinner randomSpinner;
    private Spinner edgeSpinner;
    private NumberPicker numberPicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_erp_screen_2, container, false);

        randomSpinner = (Spinner) v.findViewById(R.id.erp_screen_2_spinner_random);
        edgeSpinner = (Spinner) v.findViewById(R.id.erp_screen_2_spinner_edge);

        numberPicker = (NumberPicker) v.findViewById(R.id.erp_screen_2_number_picker);
        numberPicker.setMaxValue(8);
        numberPicker.setMinValue(1);
        numberPicker.setValue(1);
        numberPicker.setOnValueChangedListener(this);

        ConfigurationERP configuration = manager.getSelectedItem();

        if (configuration != null)
            readValues(configuration);

        randomSpinner.setOnItemSelectedListener(randomSpinnerListener);
        edgeSpinner.setOnItemSelectedListener(edgeSpinnerListener);

        manager.addObserver(this);

        return v;
    }

    private void readValues(ConfigurationERP configuration) {
        randomSpinner.setSelection(configuration.getRandom().ordinal());
        edgeSpinner.setSelection(configuration.getEdge().ordinal());
        numberPicker.setValue(configuration.getOutputCount());
    }

    private final AdapterView.OnItemSelectedListener randomSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ConfigurationERP configuration = manager.getSelectedItem();

            if (configuration == null)
                return;

            configuration.setRandom(ConfigurationERP.Random.valueOf(position), new AItem.OnValueChanged() {
                @Override
                public void changed() {
                    manager.notifySelectedItemInternalChange();
                }
            });

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener edgeSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ConfigurationERP configuration = manager.getSelectedItem();

            if (configuration == null)
                return;

            configuration.setEdge(ConfigurationERP.Edge.valueOf(position), new AItem.OnValueChanged() {
                @Override
                public void changed() {
                    manager.notifySelectedItemInternalChange();
                }
            });
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void update(Observable observable, Object object) {
        if (object == null) {
            numberPicker.setValue(1);
            return;
        }

        ConfigurationERP configuration = (ConfigurationERP) object;

        readValues(configuration);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        ConfigurationERP configuration = manager.getSelectedItem();

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
