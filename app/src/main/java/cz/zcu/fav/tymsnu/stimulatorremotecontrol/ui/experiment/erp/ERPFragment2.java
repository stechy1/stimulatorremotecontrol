package cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.experiment.erp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.databinding.FragmentUniversalBinding;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationERP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ViewModel;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory.ERPFactory;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.ASimpleFragment;


public class ERPFragment2 extends ASimpleFragment {

    private ViewModel<ConfigurationERP> viewModel;
    private FragmentUniversalBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModel<>(new Manager<>(new ERPFactory()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUniversalBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.universalTablayout.setupWithViewPager(binding.universalViewPager);
        binding.universalTablayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.universalViewPager));
        binding.universalViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.universalTablayout));

        return binding.getRoot();
    }
}
