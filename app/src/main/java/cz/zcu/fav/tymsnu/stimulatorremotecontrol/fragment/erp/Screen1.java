package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.erp;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Observable;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.adapter.ERPScreen1ListViewAdapter;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.Scheme;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.model.manager.SchemeManager;

public final class Screen1 extends AScreen
        implements AdapterView.OnItemClickListener, SchemeManager.OnSchemeChangeListener {

    private static final String TAG = "Screen1";

    //private final SchemeManager schemeManager = SchemeManager.getINSTANCE();
    private ListView schemeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_erp_screen_1, container, false);

        schemeView = (ListView) v.findViewById(R.id.erp_screen_1_listview_scheme);
        schemeView.setAdapter(buildAdapter());
        schemeView.setOnItemClickListener(this);
        registerForContextMenu(schemeView);

        Button buttonNewScheme = (Button) v.findViewById(R.id.erp_screen_1_new_scheme);
        buttonNewScheme.setOnClickListener(new NewSchemeListener());

        Button buttonSaveAll   = (Button) v.findViewById(R.id.erp_screen_1_save_all_schemes);
        buttonSaveAll.setOnClickListener(new SaveAllSchemesListener());

        schemeManager.addObserver(this);

        return v;
    }

    // ListView onItemClick
    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        Scheme selected = (Scheme) schemeView.getItemAtPosition(position);
        schemeManager.select(selected, new SchemeManager.Callback() {
            @Override
            public void callack(Object object) {
                ImageView img = (ImageView) view.findViewById(R.id.control_scheme_view_image);
                img.setImageResource(R.drawable.checkbox_marked_outline);
            }
        });

        ((ERPScreen1ListViewAdapter) schemeView.getAdapter()).notifyDataSetChanged();
    }

    // ListView onCreateContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.fragment_erp_screen1_listview_context_menu, menu);
        menu.setHeaderTitle(R.string.erp_screen_1_context_menu_title);
    }

    // ListView onContextItemSelected
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int listPosition = info.position;
        Scheme scheme = schemeManager.getSchemeList().get(listPosition);

        switch (item.getItemId()) {
            case R.id.erp_screen_1_context_select:
                schemeManager.select(scheme, new SchemeManager.Callback() {
                    @Override
                    public void callack(Object object) {
                        final View v = info.targetView;
                        ImageView img = (ImageView) v.findViewById(R.id.control_scheme_view_image);
                        img.setImageResource(R.drawable.checkbox_marked_outline);
                    }
                });
                return true;
            case R.id.erp_screen_1_context_delete:
                schemeManager.delete(scheme, new SchemeManager.Callback() {
                    @Override
                    public void callack(Object object) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Schema bylo smazáno", Snackbar.LENGTH_SHORT).show();
                        schemeView.requestLayout();
                    }
                });
                return true;
            case R.id.erp_screen_1_context_saveas:
                schemeManager.save(scheme, new SchemeManager.Callback() {
                    @Override
                    public void callack(Object object) {
                        Scheme scheme = (Scheme) object;
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Schema: " + scheme.getName() + " bylo uloženo", Snackbar.LENGTH_SHORT).show();
                    }
                });
                return true;
        }
        return super.onContextItemSelected(item);
    }


    private ArrayAdapter<Scheme> buildAdapter() {
        return new ERPScreen1ListViewAdapter(getContext(), schemeManager.getSchemeList());
    }

    @Override
    public void update(Observable observable, Object object) {
        Log.i(TAG, "Screen1, data set changed");
        ((ERPScreen1ListViewAdapter) schemeView.getAdapter()).notifyDataSetChanged();
    }

    private final class NewSchemeListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.erp_screen_1_new_schema);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String schemeName = input.getText().toString();
                    Log.i(TAG, "Nazev schematu: " + schemeName);
                    schemeManager.create(schemeName, new SchemeManager.Callback() {
                        @Override
                        public void callack(Object object) {
                            ((ERPScreen1ListViewAdapter) schemeView.getAdapter()).notifyDataSetChanged();
                        }
                    });
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
            schemeManager.saveAll(new SchemeManager.Callback() {
                @Override
                public void callack(Object object) {
                    Integer count = (Integer) object;
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Počet uložených schémat: " + count, Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
