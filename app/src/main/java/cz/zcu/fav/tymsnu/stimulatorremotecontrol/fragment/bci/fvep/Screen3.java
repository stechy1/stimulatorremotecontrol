package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFVEP;

public class Screen3 extends AScreen implements AdapterView.OnItemSelectedListener, View.OnClickListener, Observer {

    private static final int PULSE_UP = 0;
    private static final int PULSE_DOWN = 1;
    private static final int FREQUENCY = 2;
    private static final int DUTY_CYCLE = 3;
    private static final int BRIGHTNESS = 4;

    private String stimulText;

    private EditText[] inputs;

    private LinearLayout outputs;
    private Spinner spinner;
    private int outputTypeIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_fvep_screen_3, container, false);

        spinner = (Spinner) v.findViewById(R.id.bci_fvep_screen_3_spinner_output_type);
        spinner.setOnItemSelectedListener(this);

        Button btnSave = (Button) v.findViewById(R.id.bci_fvep_screen_3_button_save_output);
        btnSave.setOnClickListener(this);

        outputs = (LinearLayout) v.findViewById(R.id.bci_fvep_screen_3_linearlayout);
        inputs = new EditText[1];

        stimulText = getResources().getString(R.string.bci_fvep_screen_3_output);

        manager.addObserver(this);

        return v;
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
        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.values_were_saved, configuration.getName()), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data == null) {
            inputs = new EditText[1];
            this.outputs.removeAllViews();
            return;
        }
        ConfigurationFVEP configuration = (ConfigurationFVEP) data;
        spinner.setSelection(PULSE_UP);

        inputs = new EditText[configuration.getOutputCount()];

        outputs.removeAllViews();
        changeValues();
    }

    private void changeValues() {
        ConfigurationFVEP configurationFvep = manager.getSelectedItem();
        if (configurationFvep == null)
            return;

        final Context context = getContext();
        if (context == null)
            return;

        final List<ConfigurationFVEP.Output> outputs = configurationFvep.outputList;
        final LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        if (inputs[0] == null) {
            if (inputs.length != configurationFvep.getOutputCount())
                inputs = new EditText[configurationFvep.getOutputCount()];

            for (int i = 0; i < configurationFvep.getOutputCount(); i++) {
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                TextView textView = new TextView(context);
                textView.setText(i + stimulText);

                EditText editText = new EditText(context);
                editText.setLayoutParams(textLayoutParams);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                inputs[i] = editText;

                layout.addView(textView);
                layout.addView(editText);

                this.outputs.addView(layout);
            }
        }

        readValues(outputs);
    }

    private void readValues(List<ConfigurationFVEP.Output> outputs) {
        int count = outputs.size();
        switch (outputTypeIndex) {
            case PULSE_UP:
                for (int i = 0; i < count; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    inputs[i].setText("" + output.puls.getUp());
                }
                break;
            case PULSE_DOWN:
                for (int i = 0; i < count; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    inputs[i].setText("" + output.puls.getDown());
                }
                break;

            case FREQUENCY:
                for (int i = 0; i < count; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    inputs[i].setText("" + output.getFrequency());
                }
                break;

            case DUTY_CYCLE:
                for (int i = 0; i < count; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    inputs[i].setText("" + output.getDutyCycle());
                }
                break;

            case BRIGHTNESS:
                for (int i = 0; i < count; i++) {
                    ConfigurationFVEP.Output output = outputs.get(i);
                    inputs[i].setText("" + output.getBrightness());
                }
                break;
        }
    }

    private void writeValues(List<ConfigurationFVEP.Output> outputs) {
        int count = outputs.size();
        switch (outputTypeIndex) {
            case PULSE_UP:
                for (int i = 0; i < count; i++) {
                    int val = readValue(inputs[i]);
                    ConfigurationFVEP.Output output = outputs.get(i);
                    output.puls.setUp(val, new AItem.OnValueChanged() {
                        @Override
                        public void changed() {
                            manager.notifySelectedItemInternalChange();
                        }
                    });
                }
                break;
            case PULSE_DOWN:
                for (int i = 0; i < count; i++) {
                    int val = readValue(inputs[i]);
                    ConfigurationFVEP.Output output = outputs.get(i);
                    output.puls.setDown(val, new AItem.OnValueChanged() {
                        @Override
                        public void changed() {
                            manager.notifySelectedItemInternalChange();
                        }
                    });
                }
                break;

            case FREQUENCY:
                for (int i = 0; i < count; i++) {
                    int val = readValue(inputs[i]);
                    ConfigurationFVEP.Output output = outputs.get(i);
                    if (output.isFrequencyInRange(val)) {
                        output.setFrequency(val, new AItem.OnValueChanged() {
                            @Override
                            public void changed() {
                                manager.notifySelectedItemInternalChange();
                            }
                        });
                    }
                    else
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.exception_out_of_range), Snackbar.LENGTH_SHORT).show();
                }
                break;
            case DUTY_CYCLE:
                for (int i = 0; i < count; i++) {
                    int val = readValue(inputs[i]);
                    ConfigurationFVEP.Output output = outputs.get(i);
                    if (output.isDutyCycleInRange(val)) {
                        output.setDutyCycle(val, new AItem.OnValueChanged() {
                            @Override
                            public void changed() {
                                manager.notifySelectedItemInternalChange();
                            }
                        });
                    }
                    else
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.exception_out_of_range), Snackbar.LENGTH_SHORT).show();
                }
                break;

            case BRIGHTNESS:
                for (int i = 0; i < count; i++) {
                    int val = readValue(inputs[i]);
                    ConfigurationFVEP.Output output = outputs.get(i);
                    output.setBrightness(val, new AItem.OnValueChanged() {
                        @Override
                        public void changed() {
                            manager.notifySelectedItemInternalChange();
                        }
                    });
                }
                break;
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
