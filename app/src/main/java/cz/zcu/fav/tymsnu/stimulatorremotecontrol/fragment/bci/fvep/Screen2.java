package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

public class Screen2 extends AScreen {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_fvep_screen_2, container, false);



        return v;
    }

}
