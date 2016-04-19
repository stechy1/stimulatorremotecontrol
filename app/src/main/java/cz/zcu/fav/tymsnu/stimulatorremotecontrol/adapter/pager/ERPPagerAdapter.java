package cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.Constants;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.IBtCommunication;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleScreen;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.SimpleConfigurationFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp.Screen2;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp.Screen3;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class ERPPagerAdapter extends FragmentStatePagerAdapter {

    private final IBtCommunication iBtCommunication;
    private final Manager<ConfigurationERP> schemeManager;

    public ERPPagerAdapter(FragmentManager fm, IBtCommunication iBtCommunication, Manager<ConfigurationERP> schemeManager) {
        super(fm);

        this.iBtCommunication = iBtCommunication;
        this.schemeManager = schemeManager;
    }

    @Override
    public Fragment getItem(int position) {
        ASimpleScreen<ConfigurationERP> fragment;
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
        }

        fragment.setBtCommunication(iBtCommunication);
        fragment.setManager(schemeManager);

        return fragment;
    }

    @Override
    public int getCount() {
        return Constants.ERP_SCREEN_COUNT;
    }
}
