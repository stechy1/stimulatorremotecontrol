package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.BuildConfig;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

public class AboutFragment extends ASimpleFragment {

    private static final String TAG = "AboutFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        TextView textView = (TextView) v.findViewById(R.id.about_version);
        textView.setText(getString(R.string.version, BuildConfig.VERSION_NAME));

        return v;
    }

}
