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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;

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

        Scheme scheme = (Scheme) schemeManager.getSelectedItem();

        if (scheme != null)
            readValues(scheme);

        randomSpinner.setOnItemSelectedListener(randomSpinnerListener);
        edgeSpinner.setOnItemSelectedListener(edgeSpinnerListener);

        schemeManager.addObserver(this);

        return v;
    }

    private void readValues(Scheme scheme) {
        randomSpinner.setSelection(scheme.getRandom().ordinal());
        edgeSpinner.setSelection(scheme.getEdge().ordinal());
        numberPicker.setValue(scheme.getOutputCount());
    }

    private final AdapterView.OnItemSelectedListener randomSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Scheme scheme = (Scheme) schemeManager.getSelectedItem();

            if (scheme == null)
                return;

            scheme.setRandom(Scheme.Random.valueOf(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final AdapterView.OnItemSelectedListener edgeSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Scheme scheme = (Scheme) schemeManager.getSelectedItem();

            if (scheme == null)
                return;

            scheme.setEdge(Scheme.Edge.valueOf(position));
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

        Scheme scheme = (Scheme) object;

        readValues(scheme);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Scheme scheme = (Scheme) schemeManager.getSelectedItem();

        if (scheme == null)
            return;

        scheme.setOutputCount(newVal);
        schemeManager.notifyValueChanged();
    }
}
