package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;


import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class AScreen extends ASimpleFragment {

    protected static Manager<ConfigurationERP> manager;


    public void setManager(Manager<ConfigurationERP> manager) {
        AScreen.manager = manager;
    }
}
