package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.tvep;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.control.PatternControl;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfiguration;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;

public class Screen3 extends AScreen implements Observer {

    private final PatternControl[] patternControls = new PatternControl[8];
    private final View[] views = new View[8];

    private LinearLayout outputContainer;
    private String outText;
    private boolean notifyLock = false;
    private int visible = 8;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_tvep_screen_3, container, false);

        this.outputContainer = (LinearLayout) v.findViewById(R.id.bci_tvep_screen_3_container);

        outText = getString(R.string.pattern);

        fillInputs();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        manager.addObserver(this);

        ConfigurationTVEP configuration = manager.getSelectedItem();
        if (configuration == null) return;

        changeValues();
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

        if (notifyLock)
            return;

        changeValues();
    }

    private void changeValues() {
        ConfigurationTVEP configuration = manager.getSelectedItem();
        if (configuration == null)
            return;

        final List<ConfigurationTVEP.Pattern> outputs = configuration.patternList;
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
            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.control_labeled_pattern, null);
            TextView title = (TextView) layout.findViewById(R.id.labeled_pattern_title);
            PatternControl input = (PatternControl) layout.findViewById(R.id.labeled_pattern_control);

            title.setText(String.valueOf(i + outText));

            views[i] = layout;
            patternControls[i] = input;

            outputContainer.addView(layout);
        }
    }

    private void readValues(List<ConfigurationTVEP.Pattern> patternList) {
        ConfigurationTVEP configurationTVEP = manager.getSelectedItem();
        int i = 0;
        for (ConfigurationTVEP.Pattern pattern : patternList) {
            PatternControl patternControl = patternControls[i];
            patternControl.setBitCount(configurationTVEP.getPatternLength());
            patternControl.setValue(pattern.getValue(), false);
            patternControl.setOnValueChangeListener(new OnPatternValueChangeListener(pattern));

            i++;
        }
    }

    private final class OnPatternValueChangeListener implements PatternControl.ValueChangeListener {

        private final ConfigurationTVEP.Pattern pattern;

        public OnPatternValueChangeListener(ConfigurationTVEP.Pattern pattern) {
            this.pattern = pattern;
        }

        @Override
        public void change(int oldValue, int newValue) {
            pattern.setValue(newValue, new AConfiguration.OnValueChanged() {
                @Override
                public void changed() {
                    notifyLock = true;
                    manager.notifySelectedItemInternalChange();
                    notifyLock = false;
                }
            });
        }
    }
}
