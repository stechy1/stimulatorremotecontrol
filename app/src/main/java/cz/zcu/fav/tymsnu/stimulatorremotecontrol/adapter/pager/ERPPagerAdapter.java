package cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.Constants;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.IBtCommunication;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp.AScreen;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp.Screen1;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp.Screen2;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp.Screen3;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.SchemeManager;

public class ERPPagerAdapter extends FragmentStatePagerAdapter {

    private final IBtCommunication iBtCommunication;
    private final SchemeManager schemeManager;

    public ERPPagerAdapter(FragmentManager fm, IBtCommunication iBtCommunication, SchemeManager schemeManager) {
        super(fm);

        this.iBtCommunication = iBtCommunication;
        this.schemeManager = schemeManager;
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
        }

        fragment.setBtCommunication(iBtCommunication);
        fragment.setSchemeManager(schemeManager);

        return fragment;
    }

    @Override
    public int getCount() {
        return Constants.ERP_SCREEN_COUNT;
    }
}
