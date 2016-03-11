package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

public class SettingsFragment extends ASimpleFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        Button btn = (Button) view.findViewById(R.id.btn_send);
        Button btn_lang = (Button) view.findViewById(R.id.btn_change_language);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence data = ((EditText) view.findViewById(R.id.input_text)).getText();
                iBtCommunication.write(data.toString().getBytes());
            }
        });

        btn_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            }
        });

        return view;
    }

}
