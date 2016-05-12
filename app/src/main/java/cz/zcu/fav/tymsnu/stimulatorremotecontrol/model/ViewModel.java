package cz.zcu.fav.tymsnu.stimulatorremotecontrol.model;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.BR;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.factory.ERPFactory;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;
import me.tatarka.bindingcollectionadapter.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;


public class ViewModel<T extends AConfiguration<T>> {

    private final Manager<T> manager;

    public ViewModel(Manager<T> manager) {
        this.manager = manager;
    }

    public final ItemViewSelector<T> multipleItemViews = new ItemViewSelector<T>() {
        @Override
        public void select(ItemView itemView, int position, T item) {
            itemView.setBindingVariable(BR.config)
                    .setLayoutRes(position == 0
                            ? R.layout.fragment_universal_screen_1
                            : position == 1
                            ? R.layout.fragment_erp_screen_2 : R.layout.fragment_universal_screen_3);
        }

        // Pouze pro listView
        @Override
        public int viewTypeCount() {
            return 3;
        }
    };

    /**
     * Define page titles for a ViewPager
     */
    public final BindingViewPagerAdapter.PageTitles<T> pageTitles = new BindingViewPagerAdapter.PageTitles<T>() {
        @Override
        public CharSequence getPageTitle(int position, T item) {
            switch (position) {
                case 0:
                    return "Konfigurace hlavni";
                case 1:
                    return "Konfigurace 2";
                case 2:
                    return "Konfigurace 3";
                default:
                    return "Konfigurace unknown";
            }
        }
    };



}
