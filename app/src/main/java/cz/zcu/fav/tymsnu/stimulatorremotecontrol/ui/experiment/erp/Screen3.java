package cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.erp;

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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.ASimpleScreen;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfiguration;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP.Output;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.EditTextReader;

public final class Screen3 extends ASimpleScreen<ConfigurationERP>
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

        outText = getResources().getString(R.string.output_title);

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

        writeValues(configuration.outputList);
    }

    private void changeValues() {
        ConfigurationERP configuration = manager.getSelectedItem();
        if (configuration == null) {
            Log.i(TAG, "Konfigurace == null");
            return;
        }

        final List<Output> outputs = configuration.outputList;
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

            title.setText(String.valueOf(i + outText));

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
                    inputs[i].setText(String.valueOf(output.puls.getUp()));
                }
                break;
            case PULSE_DOWN:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText(String.valueOf(output.puls.getDown()));
                }
                break;

            case DISTRIBUTION_VALUE:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText(String.valueOf(output.distribution.getValue()));
                }
                break;
            case DISTRIBUTION_DELAY:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText(String.valueOf(output.distribution.getDelay()));
                }
                break;

            case BRIGHTNESS:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText(String.valueOf(output.getBrightness()));
                }
                break;
        }
    }

    private void writeValues(List<Output> outputs) {
        switch (outputTypeIndex) {
            case PULSE_UP:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    int val = EditTextReader.readValue(inputs[i], output.puls.getUp());
                    try {
                        output.puls.setUp(val, new AConfiguration.OnValueChanged() {
                            @Override
                            public void changed() {
                                notifyLock = true;
                            }
                        });
                    } catch (IllegalArgumentException ex) {
                        notifyLock = false;
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.value_out_of_range, val), Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            case PULSE_DOWN:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    int val = EditTextReader.readValue(inputs[i], output.puls.getDown());
                    try {
                    output.puls.setDown(val, new AConfiguration.OnValueChanged() {
                        @Override
                        public void changed() {
                            notifyLock = true;
                        }
                    });
                    } catch (IllegalArgumentException ex) {
                        notifyLock = false;
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.value_out_of_range, val), Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;

            case DISTRIBUTION_VALUE:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    int val = EditTextReader.readValue(inputs[i], output.distribution.getValue());
                    try {
                        if (output.canUpdateDistribution(outputs, val)) {
                            output.distribution.setValue(val, new AConfiguration.OnValueChanged() {
                                @Override
                                public void changed() {
                                    notifyLock = true;
                                }
                            });
                        }
                        else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.value_out_of_range), Snackbar.LENGTH_SHORT).show();
                            notifyLock = false;
                            break;
                        }
                    } catch (IllegalArgumentException ex) {
                        notifyLock = false;
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.value_out_of_range, val), Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            case DISTRIBUTION_DELAY:
                for (int i = 0; i < visible; i++) {
                    Output output = outputs.get(i);
                    int val = EditTextReader.readValue(inputs[i], output.distribution.getDelay());
                    try {
                        output.distribution.setDelay(val, new AConfiguration.OnValueChanged() {
                            @Override
                            public void changed() {
                                notifyLock = true;
                            }
                        });
                    } catch (IllegalArgumentException ex) {
                        notifyLock = false;
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.value_out_of_range, val), Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;

            case BRIGHTNESS:
                for (int i = 0; i < visible; i++) {
                    int val = EditTextReader.readValue(inputs[i]);
                    Output output = outputs.get(i);
                    try {
                        output.setBrightness(val, new AConfiguration.OnValueChanged() {
                            @Override
                            public void changed() {
                                notifyLock = true;
                            }
                        });
                    } catch (IllegalArgumentException ex) {
                        notifyLock = false;
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.value_out_of_range, val), Snackbar.LENGTH_SHORT).show();
                        return;
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
}
