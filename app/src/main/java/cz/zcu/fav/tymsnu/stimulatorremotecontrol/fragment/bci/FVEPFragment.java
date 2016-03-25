package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.Constants;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter.pager.FVEPPagerAdapter;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFvep;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory.ConfigurationFactory;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class FVEPFragment extends ASimpleFragment
        implements ViewPager.OnPageChangeListener {

    private static final String TAG = "FVEPFragment";

    private final Manager<ConfigurationFvep> manager = new Manager<>(new ConfigurationFactory());

    private TextView title;
    private String[] titles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        manager.setWorkingDirectory(createWorkingDirectory());
        View v = inflater.inflate(R.layout.fragment_bci_fvep, container, false);

        title = (TextView) v.findViewById(R.id.bci_fvep_title);
        titles = getResources().getStringArray(R.array.bci_fvep_screen_titles);
        title.setText(titles[0]);

        ViewPager pager = (ViewPager) v.findViewById(R.id.bci_fvep_viewpager);
        pager.setAdapter(buildPagerAdapter());
        pager.addOnPageChangeListener(this);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.bci_fvep_tablayout);
        tabLayout.setupWithViewPager(pager);

        return v;
    }

    private PagerAdapter buildPagerAdapter() {
        return new FVEPPagerAdapter(getChildFragmentManager(), iBtCommunication, manager);
    }

    private File createWorkingDirectory() {
        File baseFolder = getActivity().getFilesDir();
        File bciFolder = new File(baseFolder, Constants.FOLDER_BCI);
        File fvepFolder = new File(bciFolder, Constants.FOLDER_FVEP);
        fvepFolder.mkdirs();

        return fvepFolder;
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
