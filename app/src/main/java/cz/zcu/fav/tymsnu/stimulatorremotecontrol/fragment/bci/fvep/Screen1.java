package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.bci.fvep;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter.FVEPScreen1ListViewAdapter;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.ConfigurationFvep;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public class Screen1 extends AScreen
        implements  AdapterView.OnItemClickListener, Observer, View.OnClickListener {

    private static final String TAG = "fvep-Screen1";

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bci_fvep_screen_1, container, false);

        listView = (ListView) v.findViewById(R.id.container_listview);
        listView.setAdapter(buildAdapter());
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);

        Button buttonNewConiguration = (Button) v.findViewById(R.id.btn_new_configuration);
        buttonNewConiguration.setOnClickListener(new NewConfigurationListener());

        Button buttonSaveAll = (Button) v.findViewById(R.id.btn_save_all);
        buttonSaveAll.setOnClickListener(new SaveAllConfigurationsListener());

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.bci_fvep_fab);
        fab.setOnClickListener(this);

        manager.addObserver(this);

        return v;
    }

    // Kliknutí na položu v listView
    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        ConfigurationFvep configuration = (ConfigurationFvep) listView.getItemAtPosition(position);
        manager.select(configuration, new Manager.Callback() {
            @Override
            public void callback(Object object) {
                ImageView img = (ImageView) view.findViewById(R.id.control_list_view_image);
                img.setImageResource(R.drawable.checkbox_marked_outline);
            }
        });

        ((FVEPScreen1ListViewAdapter) listView.getAdapter()).notifyDataSetChanged();
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
        final ConfigurationFvep configuration = manager.itemList.get(listPosition);

        switch (item.getItemId()) {
            case R.id.context_duplicate:
                showInputDialog(new DialogCallback() {
                    @Override
                    public void callback(String res) {
                        try {
                            ConfigurationFvep duplicated = manager.duplicate(configuration, res);
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
                /*final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.context_set_name);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String configName = input.getText().toString();
                        try {
                            manager.rename(configuration, configName);
                            Log.i(TAG, "Nazev schematu: " + configName);
                        } catch (IllegalArgumentException ex) {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.illegal_input), Snackbar.LENGTH_SHORT).show();
                        }
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
                });*/
                break;
        }
        return super.onContextItemSelected(item);
    }

    private ListAdapter buildAdapter() {
        return new FVEPScreen1ListViewAdapter(getContext(), manager.itemList);
    }

    // Při aktualizaci datasetu v manageru (Změna schématu, změna nastavení výstupů...)
    @Override
    public void update(Observable observable, Object data) {
        ((FVEPScreen1ListViewAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    // FAB onClick
    @Override
    public void onClick(View v) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), "Spouštím stimulaci", Snackbar.LENGTH_SHORT).show();
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
            /*final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.context_set_name);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String configName = input.getText().toString();
                    try {
                        manager.create(configName);
                        Log.i(TAG, "Nazev schematu: " + configName);
                    } catch (IllegalArgumentException ex) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.illegal_input), Snackbar.LENGTH_SHORT).show();
                    }
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
            });*/
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

    private interface DialogCallback {
        void callback(String res);
    }
}
