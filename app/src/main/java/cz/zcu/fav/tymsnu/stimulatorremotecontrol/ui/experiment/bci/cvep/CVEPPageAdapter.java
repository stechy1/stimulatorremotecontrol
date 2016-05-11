package cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.bci.cvep;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.IBtCommunication;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.ASimpleScreen;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.SimpleConfigurationFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.bci.cvep.Screen2;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.bci.cvep.Screen3;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationCVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;


public class CVEPPageAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_COUNT = 3;


    private final IBtCommunication btCommunication;
    private final Manager<ConfigurationCVEP> manager;

    public CVEPPageAdapter(FragmentManager fm, IBtCommunication btCommunication, Manager<ConfigurationCVEP> manager) {
        super(fm);

        this.btCommunication = btCommunication;
        this.manager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        ASimpleScreen<ConfigurationCVEP> fragment;
        switch (position) {
            case 0:
                fragment = new SimpleConfigurationFragment<>();
                break;
            case 1:
                fragment = new Screen2();
                break;
            case 2:
                fragment = new Screen3();
                break;
            default:
                fragment = new SimpleConfigurationFragment<>();
                break;
        }

        fragment.setBtCommunication(btCommunication);
        fragment.setManager(manager);

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

}
