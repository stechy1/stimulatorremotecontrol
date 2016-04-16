package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.rea;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationREA;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class AScreen extends ASimpleFragment {

    protected static Manager<ConfigurationREA> manager;

    public void setManager(Manager<ConfigurationREA> manager) {
        AScreen.manager = manager;
    }
}
