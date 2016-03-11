package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment;


import android.support.v4.app.Fragment;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.Constants;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.IBackPress;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.IBtCommunication;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.IViewSwitcher;


public abstract class ASimpleFragment extends Fragment implements IBackPress {

    protected IBtCommunication iBtCommunication;

    public void setBtCommunication(IBtCommunication iBtCommunication) {
        this.iBtCommunication = iBtCommunication;
    }

    @Override
    public void onBackButtonPressed(IViewSwitcher IViewSwitcher) {
        IViewSwitcher.displayView(Constants.Fragments.HOME);
    }
}
