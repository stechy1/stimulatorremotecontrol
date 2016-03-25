package cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.IBtCommunication;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep.AScreen;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep.Screen1;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep.Screen2;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep.Screen3;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFvep;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class FVEPPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_COUNT = 3;


    private final IBtCommunication btCommunication;
    private final Manager<ConfigurationFvep> manager;

    public FVEPPagerAdapter(FragmentManager fm, IBtCommunication btCommunication, Manager<ConfigurationFvep> manager) {
        super(fm);

        this.btCommunication = btCommunication;
        this.manager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        AScreen fragment;
        switch (position) {
            case 0:
                fragment = new Screen1();
                break;
            case 1:
                fragment = new Screen2();
                break;
            case 2:
                fragment = new Screen3();
                break;
            default:
                fragment = new Screen1();
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
