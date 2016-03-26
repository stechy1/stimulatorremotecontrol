package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter.ERPScreen1ListViewAdapter;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.bytes.Packet;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.handler.packet.SchemePacketHandler;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.Manager;

public final class Screen1 extends AScreen
        implements AdapterView.OnItemClickListener, View.OnClickListener, Observer {

    private static final String TAG = "Screen1";

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_erp_screen_1, container, false);

        listView = (ListView) v.findViewById(R.id.erp_screen_1_listview_scheme);
        listView.setAdapter(buildAdapter());
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);

        Button buttonNewScheme = (Button) v.findViewById(R.id.erp_screen_1_new_scheme);
        buttonNewScheme.setOnClickListener(new NewSchemeListener());

        Button buttonSaveAll   = (Button) v.findViewById(R.id.erp_screen_1_save_all_schemes);
        buttonSaveAll.setOnClickListener(new SaveAllSchemesListener());

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.erp_fab);
        fab.setOnClickListener(this);

        schemeManager.addObserver(this);

        return v;
    }

    // ListView onItemClick
    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        Scheme selected = (Scheme) listView.getItemAtPosition(position);
        schemeManager.select(selected, new Manager.Callback() {
            @Override
            public void callack(Object object) {
                ImageView img = (ImageView) view.findViewById(R.id.control_list_view_image);
                img.setImageResource(R.drawable.checkbox_marked_outline);
            }
        });

        ((ERPScreen1ListViewAdapter) listView.getAdapter()).notifyDataSetChanged();
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
        Scheme scheme = schemeManager.itemList.get(listPosition);

        switch (item.getItemId()) {
            case R.id.context_select:
                schemeManager.select(scheme, new Manager.Callback() {
                    @Override
                    public void callack(Object object) {
                        final View v = info.targetView;
                        ImageView img = (ImageView) v.findViewById(R.id.control_list_view_image);
                        img.setImageResource(R.drawable.checkbox_marked_outline);
                    }
                });
                return true;
            case R.id.context_delete:
                schemeManager.delete(scheme, new Manager.Callback() {
                    @Override
                    public void callack(Object object) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.schema_was_deleted), Snackbar.LENGTH_SHORT).show();
                        listView.requestLayout();
                    }
                });
                return true;
            case R.id.context_save_as:
                schemeManager.save(scheme, new Manager.Callback() {
                    @Override
                    public void callack(Object object) {
                        Scheme scheme = (Scheme) object;
                        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.schema_saved, scheme.getName()), Snackbar.LENGTH_SHORT).show();
                    }
                });
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private ArrayAdapter<Scheme> buildAdapter() {
        return new ERPScreen1ListViewAdapter(getContext(), schemeManager.itemList);
    }

    // Při aktualizaci datasetu v manageru (Změna schématu, změna nastavení výstupů...)
    @Override
    public void update(Observable observable, Object object) {
        Log.i(TAG, "Data update");
        ((ERPScreen1ListViewAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    // FAB onClick
    @Override
    public void onClick(View v) {
        Scheme scheme = (Scheme) schemeManager.getSelectedItem();
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

    private final class NewSchemeListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.context_set_name);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String schemeName = input.getText().toString();
                    try {
                        schemeManager.create(schemeName, new Manager.Callback() {
                            @Override
                            public void callack(Object object) {
                                ((ERPScreen1ListViewAdapter) listView.getAdapter()).notifyDataSetChanged();
                            }
                        });
                        Log.i(TAG, "Nazev schematu: " + schemeName);
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
            });
        }
    }

    private final class SaveAllSchemesListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            schemeManager.saveAll(new Manager.Callback() {
                @Override
                public void callack(Object object) {
                    Integer count = (Integer) object;
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.count_saved_schemes, count), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
