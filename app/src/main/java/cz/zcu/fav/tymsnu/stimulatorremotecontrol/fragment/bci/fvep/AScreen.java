package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public abstract class AScreen extends ASimpleFragment {

    protected Manager<ConfigurationFVEP> manager;

    public void setManager(Manager<ConfigurationFVEP> manager) {
        this.manager = manager;
    }
}
