package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AItem;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP.Output;

public final class Screen3 extends AScreen
        implements AdapterView.OnItemSelectedListener, View.OnClickListener, Observer {

    private static final String TAG = "screen3";


    private static final int PULSE_UP = 0;
    private static final int PULSE_DOWN = 1;
    private static final int DISTRIBUTION_VALUE = 2;
    private static final int DISTRIBUTION_DELAY = 3;
    private static final int BRIGHTNESS = 4;

    private String outText;

    //private EditText[] inputs;
    private final EditText[] inputs = new EditText[8];
    private final View[] views = new View[8];

    private LinearLayout outputContainer;
    private Spinner spinner;
    private int outputTypeIndex;
    private boolean notifyLock = false;
    private int visible = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_universal_screen_3, container, false);

        spinner = (Spinner) v.findViewById(R.id.universal_screen_3_spinner_output_type);
        spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.erp_screen_3_output_types)));
        spinner.setOnItemSelectedListener(this);

        Button btnSave = (Button) v.findViewById(R.id.universal_screen_3_button_save_output);
        btnSave.setOnClickListener(this);

        outputContainer = (LinearLayout) v.findViewById(R.id.universal_screen_3_linearlayout);
        //inputs = new EditText[1];

        outText = getResources().getString(R.string.erp_screen_3_output);

        fillInputs();

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

    // Při změně schématu
    @Override
    public void update(Observable observable, Object object) {
        if (notifyLock) return;

        if (object == null) return;

        spinner.setSelection(outputTypeIndex);

        changeValues();
    }

    // Při výběru položky ze spinneru
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        outputTypeIndex = position;

        changeValues();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Save button click
    @Override
    public void onClick(View v) {
        ConfigurationERP configuration = manager.getSelectedItem();
        if (configuration == null)
            return;

        final List<Output> outputs = configuration.getOutputList();

        writeValues(outputs);
    }

    private void changeValues() {
        ConfigurationERP configuration = manager.getSelectedItem();
        if (configuration == null) {
            Log.i(TAG, "Konfigurace == null");
            return;
        }

        final List<Output> outputs = configuration.getOutputList();
        int outputCount = configuration.getOutputCount();

        if (outputCount != visible)
            rearangeInputs(outputCount);

        readValues(outputs);
    }

    private void rearangeInputs(int configOutputCount) {
        if (configOutputCount > visible) {
            for (int i = visible; i < configOutputCount; i++) {
                views[i].setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = --visible; i >= configOutputCount; i--) {
                views[i].setVisibility(View.INVISIBLE);
            }
        }

        visible = configOutputCount;
    }

    private void fillInputs() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < 8; i++) {
            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.control_labeled_input, null);
            TextView title = (TextView) layout.findViewById(R.id.labeled_input_title);
            EditText input = (EditText) layout.findViewById(R.id.labeled_input_value);

            title.setText(i + outText);

            views[i] = layout;
            inputs[i] = input;

            outputContainer.addView(layout);
        }
    }

    private void readValues(List<Output> outputs) {
        switch (outputTypeIndex) {
            case PULSE_UP:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText("" + output.puls.getUp());
                }
                break;
            case PULSE_DOWN:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText("" + output.puls.getDown());
                }
                break;

            case DISTRIBUTION_VALUE:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText("" + output.distribution.getValue());
                }
                break;
            case DISTRIBUTION_DELAY:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText("" + output.distribution.getDelay());
                }
                break;

            case BRIGHTNESS:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText("" + output.getBrightness());
                }
                break;
        }
    }

    private void writeValues(List<Output> outputs) {
        switch (outputTypeIndex) {
            case PULSE_UP:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    int val = readValue(inputs[i], output.puls.getUp());
                    output.puls.setUp(val, new AItem.OnValueChanged() {
                        @Override
                        public void changed() {
                            notifyLock = true;
                        }
                    });
                }
                break;
            case PULSE_DOWN:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    int val = readValue(inputs[i], output.puls.getDown());
                    output.puls.setDown(val, new AItem.OnValueChanged() {
                        @Override
                        public void changed() {
                            notifyLock = true;
                        }
                    });
                }
                break;

            case DISTRIBUTION_VALUE:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    int val = readValue(inputs[i], output.distribution.getValue());
                    if (output.distribution.isValueInRange(val) && output.canUpdateDistribution(outputs, val)) {
                        output.distribution.setValue(val, new AItem.OnValueChanged() {
                            @Override
                            public void changed() {
                                notifyLock = true;
                            }
                        });
                    }
                    else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.exception_out_of_range), Snackbar.LENGTH_SHORT).show();
                        notifyLock = false;
                        break;
                    }
                }
                break;
            case DISTRIBUTION_DELAY:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    int val = readValue(inputs[i], output.distribution.getDelay());
                        output.distribution.setDelay(val, new AItem.OnValueChanged() {
                            @Override
                            public void changed() {
                                notifyLock = true;
                            }
                        });
                }
                break;

            case BRIGHTNESS:
                for (int i = 0; i < visible; i++) {
                    int val = readValue(inputs[i]);
                    Output output = outputs.get(i);
                    if (output.isBrightnessInRange(val)) {
                        output.setBrightness(val, new AItem.OnValueChanged() {
                            @Override
                            public void changed() {
                                notifyLock = true;
                            }
                        });
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.exception_out_of_range), Snackbar.LENGTH_SHORT).show();
                        notifyLock = false;
                        break;
                    }
                }
                break;
        }
        if (notifyLock) {
            manager.notifySelectedItemInternalChange();
            notifyLock = false;
            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.values_were_saved, manager.getSelectedItem().getName()), Snackbar.LENGTH_SHORT).show();
        }
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
