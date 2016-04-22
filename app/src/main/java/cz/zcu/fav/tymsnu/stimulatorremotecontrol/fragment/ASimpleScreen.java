package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.AConfiguration;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class ASimpleScreen<T extends AConfiguration<T>> extends ASimpleFragment {

    protected Manager<T> manager;

    public void setManager(Manager<T> manager) {
        this.manager = manager;
    }

}
