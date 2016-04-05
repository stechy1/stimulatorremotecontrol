package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.tvep;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter.TVEPScreen1ListViewAdapter;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationTVEP;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.packet.TVEPPacketHandler;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class Screen1 extends AScreen 
        implements AdapterView.OnItemClickListener, Observer {

    private static final String TAG = "fvep-Screen1";

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_universal_screen_1, container, false);

        listView = (ListView) v.findViewById(R.id.universal_screen_1_container_listview);
        listView.setAdapter(buildAdapter());
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);

        ImageButton buttonNewConiguration = (ImageButton) v.findViewById(R.id.universal_screen_1_btn_new_configuration);
        buttonNewConiguration.setOnClickListener(new NewConfigurationListener());

        ImageButton buttonSaveAll = (ImageButton) v.findViewById(R.id.universal_screen_1_btn_save_all);
        buttonSaveAll.setOnClickListener(new SaveAllConfigurationsListener());

        ImageButton buttonPlay = (ImageButton) v.findViewById(R.id.universal_screen_1_btn_play);
        buttonPlay.setOnClickListener(new PlayConfigurationListener());

        //manager.addObserver(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        manager.addObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        manager.deleteObserver(this);
    }

    // Kliknutí na položu v listView
    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        ConfigurationTVEP configuration = (ConfigurationTVEP) listView.getItemAtPosition(position);
        manager.select(configuration, new Manager.Callback() {
            @Override
            public void callback(Object object) {
                ImageView img = (ImageView) view.findViewById(R.id.control_list_view_image);
                img.setImageResource(R.drawable.checkbox_marked_outline);
            }
        });

        ((TVEPScreen1ListViewAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    // ListView onCreateContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu_crud, menu);
        menu.setHeaderTitle(R.string.context_options);
    }

    // ListView onContextItemSelected
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int listPosition = info.position;
        final ConfigurationTVEP configuration = manager.itemList.get(listPosition);

        switch (item.getItemId()) {
            case R.id.context_duplicate:
                showInputDialog(new DialogCallback() {
                    @Override
                    public void callback(String res) {
                        try {
                            ConfigurationTVEP duplicated = manager.duplicate(configuration, res);
                            manager.add(duplicated);
                            manager.notifyValueChanged();
                        } catch (IllegalArgumentException ex) {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.illegal_input), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
                return true;
            case R.id.context_delete:
                manager.delete(configuration, new Manager.Callback() {
                    @Override
                    public void callback(Object object) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.configuration_was_deleted), Snackbar.LENGTH_SHORT).show();
                        listView.requestLayout();
                    }
                });
                return true;
            case R.id.context_rename:
                showInputDialog(new DialogCallback() {
                    @Override
                    public void callback(String configName) {
                        try {
                            manager.rename(configuration, configName);
                            Log.i(TAG, "Nazev schematu: " + configName);
                        } catch (IllegalArgumentException ex) {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.illegal_input), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }

    private ListAdapter buildAdapter() {
        return new TVEPScreen1ListViewAdapter(getContext(), manager.itemList);
    }

    // Při aktualizaci datasetu v manageru (Změna schématu, změna nastavení výstupů...)
    @Override
    public void update(Observable observable, Object data) {
        ((TVEPScreen1ListViewAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    private void showInputDialog(final DialogCallback callback) {
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.context_set_name);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                callback.callback(name);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.show();

        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
    }

    private final class NewConfigurationListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            showInputDialog(new DialogCallback() {
                @Override
                public void callback(String configName) {
                    try {
                        manager.create(configName);
                        Log.i(TAG, "Nazev schematu: " + configName);
                    } catch (IllegalArgumentException ex) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.illegal_input), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private final class SaveAllConfigurationsListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            manager.saveAll(new Manager.Callback() {
                @Override
                public void callback(Object object) {
                    Integer count = (Integer) object;
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.count_saved_configuration, count), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    private final class PlayConfigurationListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ConfigurationTVEP configuration = manager.getSelectedItem();
            if (configuration == null)
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Vyberte konfiguraci pro spusteni stimulace", Snackbar.LENGTH_LONG).show();
            else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Spouštím stimulaci...", Snackbar.LENGTH_LONG).show();
                List<Packet> packets = new TVEPPacketHandler(configuration).getPackets();
                for (Packet packet : packets) {
                    Log.i(TAG, packet.toString());
                    if (!iBtCommunication.write(packet.getValue())) {
                        break;
                    }
                }
            }
        }
    }

    private interface DialogCallback {
        void callback(String res);
    }
    
}
