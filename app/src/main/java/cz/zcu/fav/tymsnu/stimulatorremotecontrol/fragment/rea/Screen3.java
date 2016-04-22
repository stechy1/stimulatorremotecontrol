package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.rea;


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
import android.widget.Spinner;

import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleScreen;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfiguration;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationREA;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.utils.EditTextReader;

public class Screen3 extends ASimpleScreen<ConfigurationREA>
        implements Observer, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinner1;
    private Spinner spinner2;
//    private CheckBox checkBox1;
//    private CheckBox checkBox2;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;

    private boolean notifyLock;
    private boolean editTextChanged;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rea_screen_3, container, false);

        spinner2 = (Spinner) v.findViewById(R.id.rea_screen_3_spinner_sex);
        spinner1 = (Spinner) v.findViewById(R.id.rea_screen_3_spinner_fail);

        spinner1.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.rea_screen_3_fail_type)));
        spinner2.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.rea_screen_3_sex)));

        spinner1.setOnItemSelectedListener(new Spinner1ItemSelectedListener());
        spinner2.setOnItemSelectedListener(new Spinner2ItemSelectedListener());


        editText1 = (EditText) v.findViewById(R.id.rea_screen_3_edit_text_a);
        editText2 = (EditText) v.findViewById(R.id.rea_screen_3_edit_text_w);
        editText3 = (EditText) v.findViewById(R.id.rea_screen_3_edit_text_h);

        Button btnSaveAll = (Button) v.findViewById(R.id.rea_screen_3_button_save_all);
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

    @Override
    public void update(Observable observable, Object data) {
        if (data == null)
            return;

        if (notifyLock) {
            notifyLock = false;
            return;
        }

        ConfigurationREA configuration = (ConfigurationREA) data;

        spinner1.setSelection(configuration.getOnFail());
        spinner2.setSelection(configuration.getSex().ordinal());

        editText1.setText(String.valueOf(configuration.getA()));
        editText2.setText(String.valueOf(configuration.getW()));
        editText3.setText(String.valueOf(configuration.getH()));
    }

    // On spinner1 item selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    // On button save all click
    @Override
    public void onClick(View v) {
        ConfigurationREA configuration = manager.getSelectedItem();

        if (configuration == null)
            return;

        configuration.setA(EditTextReader.readValue(editText1, configuration.getA()), new AConfiguration.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                editTextChanged = true;
                manager.notifySelectedItemInternalChange();
            }
        });
        configuration.setW(EditTextReader.readValue(editText2, configuration.getW()), new AConfiguration.OnValueChanged() {
            @Override
            public void changed() {
                notifyLock = true;
                editTextChanged = true;
                manager.notifySelectedItemInternalChange();
            }
        });
        configuration.setH(EditTextReader.readValue(editText3, configuration.getH()), new AConfiguration.OnValueChanged() {
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


    private final class Spinner1ItemSelectedListener implements Spinner.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ConfigurationREA configuration = manager.getSelectedItem();

            if (configuration == null)
                return;

            configuration.setOnFail(position, new AConfiguration.OnValueChanged() {
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

    private final class Spinner2ItemSelectedListener implements Spinner.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ConfigurationREA configuration = manager.getSelectedItem();

            if (configuration == null)
                return;

            configuration.setSex(ConfigurationREA.Sex.valueOf(position), new AConfiguration.OnValueChanged() {
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
}
