package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.vi.swipenumberpicker.OnValueChangeListener;
import com.vi.swipenumberpicker.SwipeNumberPicker;

import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfiguration;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.EditTextReader;

public final class Screen2 extends AScreen
        implements Observer, OnValueChangeListener, View.OnClickListener {

    private static final String TAG = "Screen2";

    private EditText outEditText;
    private EditText waitEditText;
    private Spinner randomSpinner;
    private Spinner edgeSpinner;
    private SwipeNumberPicker numberPicker;

    private boolean notifyLock;
    private boolean editTextChanged;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_erp_screen_2, container, false);

        outEditText = (EditText) v.findViewById(R.id.erp_screen_2_eidt_text_out);
        waitEditText = (EditText) v.findViewById(R.id.erp_screen_2_edit_text_wait);

        randomSpinner = (Spinner) v.findViewById(R.id.erp_screen_2_spinner_random);
        edgeSpinner = (Spinner) v.findViewById(R.id.erp_screen_2_spinner_edge);

        randomSpinner.setOnItemSelectedListener(new RandomSpinnerListener());
        edgeSpinner.setOnItemSelectedListener(new EdgeSpinnerListener());

        numberPicker = (SwipeNumberPicker) v.findViewById(R.id.erp_screen_2_swipe_number_picker);
        numberPicker.setMaxValue(8);
        numberPicker.setMinValue(1);
        numberPicker.setValue(1, false);
        numberPicker.setOnValueChangeListener(this);

        Button btnSaveAll = (Button) v.findViewById(R.id.erp_screen_2_button_save_all);
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

    private final class RandomSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ConfigurationERP configuration = manager.getSelectedItem();

            if (configuration == null)
                return;

            configuration.setRandom(ConfigurationERP.Random.valueOf(position), new AConfiguration.OnValueChanged() {
                @Override
                public void changed() {
                    notifyLock = true;
                    manager.notifySelectedItemInternalChange();
                }
            });

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private final class EdgeSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ConfigurationERP configuration = manager.getSelectedItem();

            if (configuration == null)
                return;

            configuration.setEdge(ConfigurationERP.Edge.valueOf(position), new AConfiguration.OnValueChanged() {
                @Override
                public void changed() {
                    notifyLock = true;
                    manager.notifySelectedItemInternalChange();
                }
            });
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    public void update(Observable observable, Object object) {
        if (object == null)
            return;

        if (notifyLock) {
            notifyLock = false;
            return;
        }

        ConfigurationERP configuration = (ConfigurationERP) object;

        outEditText.setText(String.valueOf(configuration.getOut()));
        waitEditText.setText(String.valueOf(configuration.getWait()));
        randomSpinner.setSelection(configuration.getRandom().ordinal());
        edgeSpinner.setSelection(configuration.getEdge().ordinal());
        numberPicker.setValue(configuration.getOutputCount(), true);
    }

    @Override
    public boolean onValueChange(SwipeNumberPicker view, int oldValue, int newValue) {
        ConfigurationERP configuration = manager.getSelectedItem();

        if (configuration == null)
            return false;

        configuration.setOutputCount(newValue, new AConfiguration.OnValueChanged() {
            @Override
            public void changed() {
                manager.notifySelectedItemInternalChange();
                manager.notifyValueChanged();
            }
        });

        return true;
    }

    @Override
    public void onClick(View v) {
        ConfigurationERP configuration = manager.getSelectedItem();

        if (configuration == null)
            return;

        configuration.setOut(EditTextReader.readValue(outEditText, configuration.getOut()), new AConfiguration.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                editTextChanged = true;
                manager.notifySelectedItemInternalChange();
            }
        });

        configuration.setWait(EditTextReader.readValue(waitEditText, configuration.getWait()), new AConfiguration.OnValueChanged() {
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


}
