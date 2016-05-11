package cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.bci.tvep;

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
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory.TVEPFactory;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;


public class TVEPFragment extends ASimpleFragment
        implements ViewPager.OnPageChangeListener {

    private static final String TAG = "FVEPFragment";

    private final Manager<ConfigurationTVEP> manager = new Manager<>(new TVEPFactory());

    private TextView title;
    private String[] titles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        manager.setWorkingDirectory(createWorkingDirectory());
        View v = inflater.inflate(R.layout.fragment_universal, container, false);

        title = (TextView) v.findViewById(R.id.universal_title);
        titles = getResources().getStringArray(R.array.bci_tvep_screen_titles);
        title.setText(titles[0]);

        ViewPager pager = (ViewPager) v.findViewById(R.id.universal_view_pager);
        pager.setAdapter(buildPagerAdapter());
        pager.setOffscreenPageLimit(3);
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

    private PagerAdapter buildPagerAdapter() {
        return new TVEPPagerAdapter(getChildFragmentManager(), iBtCommunication, manager);
    }

    private File createWorkingDirectory() {
        File baseFolder = getActivity().getFilesDir();
        File bciFolder = new File(baseFolder, Constants.FOLDER_BCI);

        return new File(bciFolder, Constants.FOLDER_TVEP);
    }


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

}
