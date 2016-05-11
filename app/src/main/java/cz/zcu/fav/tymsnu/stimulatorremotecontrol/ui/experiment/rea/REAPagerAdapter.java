package cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.rea;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.IBtCommunication;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.ASimpleScreen;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.SimpleConfigurationFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.rea.Screen2;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.rea.Screen3;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationREA;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class REAPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_COUNT = 3;


    private final IBtCommunication btCommunication;
    private final Manager<ConfigurationREA> manager;

    public REAPagerAdapter(FragmentManager fm, IBtCommunication btCommunication, Manager<ConfigurationREA> manager) {
        super(fm);

        this.btCommunication = btCommunication;
        this.manager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        ASimpleScreen<ConfigurationREA> fragment;
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