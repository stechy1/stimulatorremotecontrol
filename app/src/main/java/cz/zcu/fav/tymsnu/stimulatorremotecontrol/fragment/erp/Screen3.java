package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Observable;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.SchemeManager;

public final class Screen3 extends ASimpleFragment
        implements SchemeManager.OnSchemeChangeListener, View.OnClickListener {

    private static final String TAG = "Screen3";

    private final SchemeManager schemeManager = SchemeManager.getINSTANCE();

    private Spinner randomSpinner;
    private Spinner edgeSpinner;
    private EditText outputCountEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_erp_screen_3, container, false);

        randomSpinner = (Spinner) v.findViewById(R.id.erp_screen_3_spinner_random);
        edgeSpinner = (Spinner) v.findViewById(R.id.erp_screen_3_spinner_edge);
        outputCountEditText = (EditText) v.findViewById(R.id.erp_screen_3_edittext_output_count);

        Button saveButton = (Button) v.findViewById(R.id.erp_screen_3_button_save);
        saveButton.setOnClickListener(this);

        Scheme scheme = schemeManager.getSelectedScheme();
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
        outputCountEditText.setText(scheme.getOutputCount() + "");
    }

    private final AdapterView.OnItemSelectedListener randomSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Scheme scheme = schemeManager.getSelectedScheme();

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
            Scheme scheme = schemeManager.getSelectedScheme();

            if (scheme == null)
                return;

            scheme.setEdge(Scheme.Edge.valueOf(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void update(Observable observable, Object data) {
        Scheme scheme = (Scheme) data;

        if (scheme == null)
            return;

        readValues(scheme);
    }

    @Override
    public void onClick(View v) {
        Scheme scheme = schemeManager.getSelectedScheme();
        if (scheme == null)
            return;

        try {
            int val = Integer.parseInt(outputCountEditText.getText().toString());
            scheme.setOutputCount(val);
            schemeManager.notifyValueChanged();
        } catch (IllegalArgumentException ex) {
            Toast.makeText(getContext(), getString(R.string.exception_out_of_range), Toast.LENGTH_SHORT).show();
            Log.e(TAG, ex.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), getString(R.string.exception_general), Toast.LENGTH_SHORT).show();
            Log.e(TAG, ex.toString());
        }
    }
}
