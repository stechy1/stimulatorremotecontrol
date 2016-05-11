package cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.rea;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.Constants;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationREA;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory.REAFactory;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.ASimpleFragment;

public class ReactionFragment extends ASimpleFragment
        implements ViewPager.OnPageChangeListener {

    private static final String TAG = "ERPFragment";

    private final Manager<ConfigurationREA> manager = new Manager<>(new REAFactory());

    private TextView title;
    private String[] titles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        manager.setWorkingDirectory(createWorkingDirectory());
        View v = inflater.inflate(R.layout.fragment_universal, container, false);

        title = (TextView) v.findViewById(R.id.universal_title);
        titles = getResources().getStringArray(R.array.rea_screen_titles);
        title.setText(titles[0]);

        ViewPager pager = (ViewPager) v.findViewById(R.id.universal_view_pager);
        pager.setAdapter(buildAdapter());
        pager.setOffscreenPageLimit(Constants.ERP_SCREEN_COUNT);
        pager.addOnPageChangeListener(this);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.universal_tablayout);
        tabLayout.setupWithViewPager(pager);

        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        tabStrip.setEnabled(false);
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }

        return v;
    }

    private PagerAdapter buildAdapter() {
        return(new REAPagerAdapter(getChildFragmentManager(), iBtCommunication, manager));
    }

    private File createWorkingDirectory() {
        File baseFolder = getActivity().getFilesDir();

        return new File(baseFolder, Constants.FOLDER_REA);
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
