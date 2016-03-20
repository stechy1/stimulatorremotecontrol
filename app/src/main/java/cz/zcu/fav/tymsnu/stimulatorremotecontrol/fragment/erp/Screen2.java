package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;

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

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Output;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.SchemeManager;

public final class Screen2 extends ASimpleFragment
        implements AdapterView.OnItemSelectedListener, SchemeManager.OnSchemeChangeListener, View.OnClickListener {

    private static final int PULSE_UP = 0;
    private static final int PULSE_DOWN = 1;
    private static final int DISTRIBUTION_VALUE = 2;
    private static final int DISTRIBUTION_DELAY = 3;
    private static final int BRIGHTNESS = 4;

    private final SchemeManager schemeManager = SchemeManager.getINSTANCE();

    private String outText;

    private EditText[] inputs;

    private LinearLayout outputs;
    private Spinner spinner;
    private int outputTypeIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_erp_screen_2, container, false);

        spinner = (Spinner) v.findViewById(R.id.erp_screen_2_spinner_output_type);
        spinner.setOnItemSelectedListener(this);

        // TODO odebrat tlačítko uložit a udělat synchronizaci hodnot při jejich změně
        Button btnSave = (Button) v.findViewById(R.id.erp_screen_2_button_save_output);
        btnSave.setOnClickListener(this);

        outputs = (LinearLayout) v.findViewById(R.id.erp_screen_2_linearlayout);
        inputs = new EditText[1];

        outText = getResources().getString(R.string.erp_screen_2_output);

        schemeManager.addObserver(this);

        return v;
    }

    // Při změně schématu
    @Override
    public void update(Observable observable, Object object) {
        Scheme scheme = (Scheme) object;
        spinner.setSelection(PULSE_UP);

        inputs = new EditText[scheme.getOutputCount()];

        outputs.removeAllViews();
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
        final Scheme scheme = schemeManager.getSelectedScheme();
        if (scheme == null)
            return;

        final List<Output> outputs = scheme.getOutputList();

        writeValues(outputs);
    }

    private void changeValues() {
        Scheme scheme = schemeManager.getSelectedScheme();
        if (scheme == null)
            return;

        final Context context = getContext();
        if (context == null)
            return;

        final List<Output> outputs = scheme.getOutputList();
        final LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        if (inputs[0] == null) {
            if (inputs.length != scheme.getOutputCount())
                inputs = new EditText[scheme.getOutputCount()];

            for (int i = 0; i < scheme.getOutputCount(); i++) {
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                TextView textView = new TextView(context);
                textView.setText(i + outText);

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

    private void readValues(List<Output> outputs) {
        int count = outputs.size();
        switch (outputTypeIndex) {
            case PULSE_UP:
                for (int i = 0; i < count; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText("" + output.puls.getUp());
                }
                break;
            case PULSE_DOWN:
                for (int i = 0; i < count; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText("" + output.puls.getDown());
                }
                break;

            case DISTRIBUTION_VALUE:
                for (int i = 0; i < count; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText("" + output.distribution.getValue());
                }
                break;
            case DISTRIBUTION_DELAY:
                for (int i = 0; i < count; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText("" + output.distribution.getDelay());
                }
                break;

            case BRIGHTNESS:
                for (int i = 0; i < count; i++) {
                    Output output = outputs.get(i);
                    inputs[i].setText("" + output.getBrightness());
                }
                break;
        }
    }

    private void writeValues(List<Output> outputs) {
        int count = outputs.size();
        switch (outputTypeIndex) {
            case PULSE_UP:
                for (int i = 0; i < count; i++) {
                    int val = Integer.parseInt(inputs[i].getText().toString());
                    Output output = outputs.get(i);
                    output.puls.setUp(val);
                }
                break;
            case PULSE_DOWN:
                for (int i = 0; i < count; i++) {
                    int val = Integer.parseInt(inputs[i].getText().toString());
                    Output output = outputs.get(i);
                    output.puls.setDown(val);
                }
                break;

            case DISTRIBUTION_VALUE:
                for (int i = 0; i < count; i++) {
                    int val = Integer.parseInt(inputs[i].getText().toString());
                    Output output = outputs.get(i);
                    if (output.canUpdateDistribution(outputs, val))
                        output.distribution.setValue(val);
                    else
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Distribution > 100", Snackbar.LENGTH_SHORT).show();

                }
                break;
            case DISTRIBUTION_DELAY:
                for (int i = 0; i < count; i++) {
                    int val = Integer.parseInt(inputs[i].getText().toString());
                    Output output = outputs.get(i);
                    output.distribution.setDelay(val);
                }
                break;

            case BRIGHTNESS:
                for (int i = 0; i < count; i++) {
                    int val = Integer.parseInt(inputs[i].getText().toString());
                    Output output = outputs.get(i);
                    output.setBrightness(val);
                }
                break;
        }
    }



}
