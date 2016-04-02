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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AItem;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;

public class Screen3 extends AScreen implements Observer {

    private PatternControl[] patternControls;

    private LinearLayout container;
    private boolean notifyLock = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_tvep_screen_3, container, false);

        this.container = (LinearLayout) v.findViewById(R.id.bci_tvep_screen_3_container);

        manager.addObserver(this);

        return v;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data == null)
            return;

        ConfigurationTVEP configuration = (ConfigurationTVEP) data;
        patternControls = new PatternControl[configuration.getOutputCount()];

        container.removeAllViews();
        changeValues();
    }

    private void changeValues() {
        ConfigurationTVEP configuration = manager.getSelectedItem();
        if (configuration == null)
            return;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (patternControls[0] == null) {
            if (patternControls.length != configuration.getOutputCount())
                patternControls = new PatternControl[configuration.getOutputCount()];

            for (int i = 0; i < configuration.getOutputCount(); i++) {
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.control_labeled_pattern, null);
                TextView title =  (TextView) layout.findViewById(R.id.labeled_pattern_title);
                PatternControl patternControl = (PatternControl) layout.findViewById(R.id.labeled_pattern_control);

                title.setText((i + 1) + ". Pattern");
                patternControls[i] = patternControl;

                container.addView(layout);
            }
        }

        readValues(configuration.patternList);
    }

    private void readValues(List<ConfigurationTVEP.Pattern> patternList) {
        ConfigurationTVEP configurationTVEP = manager.getSelectedItem();
        int i = 0;
        for (ConfigurationTVEP.Pattern pattern : patternList) {
            PatternControl patternControl = patternControls[i];
            patternControl.setBitCount(configurationTVEP.getPatternLength());
            patternControl.setValue(pattern.getValue());
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
            pattern.setValue(newValue, new AItem.OnValueChanged() {
                @Override
                public void changed() {
                    manager.notifySelectedItemInternalChange();
                }
            });
        }
    }
}
