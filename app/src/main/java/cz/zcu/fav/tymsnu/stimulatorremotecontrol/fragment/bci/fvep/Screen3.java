package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleScreen;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfiguration;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.EditTextReader;

public class Screen3 extends ASimpleScreen<ConfigurationFVEP>
        implements AdapterView.OnItemSelectedListener, View.OnClickListener, Observer {

    private static final int PULSE_UP = 0;
    private static final int PULSE_DOWN = 1;
    private static final int FREQUENCY = 2;
    private static final int DUTY_CYCLE = 3;
    private static final int BRIGHTNESS = 4;

    private String outputText;

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
                getResources().getStringArray(R.array.bci_fvep_screen_3_output_types)));
        spinner.setOnItemSelectedListener(this);

        Button btnSave = (Button) v.findViewById(R.id.universal_screen_3_button_save_output);
        btnSave.setOnClickListener(this);

        outputContainer = (LinearLayout) v.findViewById(R.id.universal_screen_3_linearlayout);
        //inputs = new EditText[1];

        outputText = getResources().getString(R.string.output_title);

        fillInputs();

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
        if (notifyLock) return;

        if (data == null) return;

        spinner.setSelection(outputTypeIndex);

        changeValues();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        outputTypeIndex = position;

        changeValues();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        ConfigurationFVEP configuration = manager.getSelectedItem();
        if (configuration == null)
            return;

        writeValues(configuration.outputList);
    }

    private void changeValues() {
        ConfigurationFVEP configuration = manager.getSelectedItem();
        if (configuration == null)
            return;

        final List<ConfigurationFVEP.Output> outputs = configuration.outputList;
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

            title.setText(String.valueOf(i + outputText));

            views[i] = layout;
            inputs[i] = input;

            outputContainer.addView(layout);
        }
    }

    private void readValues(List<ConfigurationFVEP.Output> outputs) {
        switch (outputTypeIndex) {
            case PULSE_UP:
                for (int i = 0; i < visible; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    inputs[i].setText(String.valueOf(output.puls.getUp()));
                }
                break;
            case PULSE_DOWN:
                for (int i = 0; i < visible; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    inputs[i].setText(String.valueOf(output.puls.getDown()));
                }
                break;

            case FREQUENCY:
                for (int i = 0; i < visible; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    inputs[i].setText(String.valueOf(output.getFrequency()));
                }
                break;

            case DUTY_CYCLE:
                for (int i = 0; i < visible; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    inputs[i].setText(String.valueOf(output.getDutyCycle()));
                }
                break;

            case BRIGHTNESS:
                for (int i = 0; i < visible; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    inputs[i].setText(String.valueOf(output.getBrightness()));
                }
                break;
        }
    }

    private void writeValues(List<ConfigurationFVEP.Output> outputs) {
        switch (outputTypeIndex) {
            case PULSE_UP:
                for (int i = 0; i < visible; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
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
                    ConfigurationFVEP.Output output = outputs.get(i);
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

            case FREQUENCY:
                for (int i = 0; i < visible; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    int val = EditTextReader.readValue(inputs[i], output.getFrequency());
                    try {
                        output.setFrequency(val, new AConfiguration.OnValueChanged() {
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
            case DUTY_CYCLE:
                for (int i = 0; i < visible; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    int val = EditTextReader.readValue(inputs[i], output.getDutyCycle());
                    try {
                        output.setDutyCycle(val, new AConfiguration.OnValueChanged() {
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
                    ConfigurationFVEP.Output output = outputs.get(i);
                    int val = EditTextReader.readValue(inputs[i], output.getBrightness());
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
