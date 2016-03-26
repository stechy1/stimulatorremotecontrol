package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.Constants;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter.pager.ERPPagerAdapter;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory.SchemeFactory;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class ERPFragment extends ASimpleFragment implements ViewPager.OnPageChangeListener {

    private static final String TAG = "ERPFragment";

    private final Manager<Scheme> schemeManager = new Manager<>(new SchemeFactory());

    private TextView title;
    private String[] titles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        schemeManager.setWorkingDirectory(getActivity().getFilesDir());
        View v = inflater.inflate(R.layout.fragment_erp, container, false);

        title = (TextView) v.findViewById(R.id.erp_title);
        titles = getResources().getStringArray(R.array.erp_screen_titles);
        title.setText(titles[0]);

        ViewPager pager = (ViewPager) v.findViewById(R.id.erp_pager);
        pager.setAdapter(buildAdapter());
        pager.setOffscreenPageLimit(Constants.ERP_SCREEN_COUNT);
        pager.addOnPageChangeListener(this);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.erp_tablayout);
        tabLayout.setupWithViewPager(pager);

        return v;
    }

    private PagerAdapter buildAdapter() {
        return(new ERPPagerAdapter(getChildFragmentManager(), iBtCommunication, schemeManager));
    }

    // region ViewPager page changed
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        title.setText(titles[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    // endregion
}
