package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.Constants;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter.pager.ERPPagerAdapter;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.packet.SchemePacketHandler;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.SchemeManager;
import me.relex.circleindicator.CircleIndicator;

public class ERPFragment extends ASimpleFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "ERPFragment";

    private final SchemeManager schemeManager = new SchemeManager();

    private TextView title;
    private String[] titles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        schemeManager.setWorkingDirectory(getActivity().getFilesDir());
        View view = inflater.inflate(R.layout.fragment_erp, container, false);

        title = (TextView) view.findViewById(R.id.erp_title);
        titles = getResources().getStringArray(R.array.erp_screen_titles);
        title.setText(titles[0]);

        ViewPager pager = (ViewPager) view.findViewById(R.id.erp_pager);
        pager.setAdapter(buildAdapter());
        pager.setOffscreenPageLimit(Constants.ERP_SCREEN_COUNT);
        pager.addOnPageChangeListener(this);

        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.erp_fragment_fab);
        fab.setOnClickListener(this);

        return view;
    }

    // FAB onClick
    @Override
    public void onClick(View v) {
        Scheme scheme = schemeManager.getSelectedScheme();
        if (scheme == null)
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Vyberte schema pro spusteni stimulace", Snackbar.LENGTH_LONG).show();
        else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Spouštím stimulaci...", Snackbar.LENGTH_LONG).show();
            List<Packet> packets = new SchemePacketHandler(scheme).getPackets();
            for (Packet packet : packets) {
                Log.i(TAG, packet.toString());
                if (!iBtCommunication.write(packet.getValue())) {
                    break;
                }
            }
        }
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
