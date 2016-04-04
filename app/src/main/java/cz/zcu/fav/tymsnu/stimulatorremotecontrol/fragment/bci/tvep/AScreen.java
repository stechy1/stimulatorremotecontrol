package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.tvep;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public abstract class AScreen extends ASimpleFragment {

    protected static Manager<ConfigurationTVEP> manager;

    public void setManager(Manager<ConfigurationTVEP> manager) {
        AScreen.manager = manager;
    }
}
