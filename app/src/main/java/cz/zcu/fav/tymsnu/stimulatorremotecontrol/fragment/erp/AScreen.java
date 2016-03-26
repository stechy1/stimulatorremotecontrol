package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;


import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class AScreen extends ASimpleFragment {

    protected Manager<Scheme> schemeManager;


    public void setSchemeManager(Manager<Scheme> schemeManager) {
        this.schemeManager = schemeManager;
    }
}
