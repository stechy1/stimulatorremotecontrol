package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.cvep;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;


public class AScreen extends ASimpleFragment {

    protected Manager<ConfigurationCVEP> manager;

    public void setManager(Manager<ConfigurationCVEP> manager) {
        this.manager = manager;
    }

}
