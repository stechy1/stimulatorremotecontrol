package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;


import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.SchemeManager;

public class AScreen extends ASimpleFragment {

    protected SchemeManager schemeManager;


    public void setSchemeManager(SchemeManager schemeManager) {
        this.schemeManager = schemeManager;
    }
}
