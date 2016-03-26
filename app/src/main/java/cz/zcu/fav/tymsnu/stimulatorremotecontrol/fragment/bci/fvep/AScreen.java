package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFvep;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public abstract class AScreen extends ASimpleFragment {

    protected Manager<ConfigurationFvep> manager;

    public void setManager(Manager<ConfigurationFvep> manager) {
        this.manager = manager;
    }
}
