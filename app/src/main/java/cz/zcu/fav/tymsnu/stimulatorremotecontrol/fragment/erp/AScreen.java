package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;


import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class AScreen extends ASimpleFragment {

    protected Manager<ConfigurationERP> schemeManager;


    public void setSchemeManager(Manager<ConfigurationERP> schemeManager) {
        this.schemeManager = schemeManager;
    }
}
