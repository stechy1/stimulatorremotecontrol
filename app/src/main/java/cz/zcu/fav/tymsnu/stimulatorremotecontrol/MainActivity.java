package cz.zcu.fav.tymsnu.stimulatorremotecontrol;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.activity.DeviceListActivity;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ASimpleFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.AboutFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.ERPFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment.SettingsFragment;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.service.BluetoothCommunicationService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IViewSwitcher {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private ASimpleFragment fragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private int actViewID = 0;
    private CharSequence title;
    private Menu menu;

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * Member object for the chat services
     */
    private BluetoothCommunicationService mCommunicationService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        setStatus(getString(R.string.title_not_connected));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        title = getTitle();
        displayView(R.id.nav_about);

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);

        //SchemeManager sm = SchemeManager.getINSTANCE();
        //sm.setWorkingDirectory(getFilesDir());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mCommunicationService == null) {
            setupCommunication();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        this.menu = menu;
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_main_connect:
                switch (mCommunicationService.getState()) {
                    case BluetoothCommunicationService.STATE_NONE:
                    case BluetoothCommunicationService.STATE_LISTEN:
                        startActivityForResult(new Intent(this, DeviceListActivity.class), REQUEST_CONNECT_DEVICE);
                    break;
                    case BluetoothCommunicationService.STATE_CONNECTED:
                        mCommunicationService.start();

                        break;
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        connectDevice(data);

                    } catch (Exception ex) {
                        Snackbar.make(findViewById(android.R.id.content), "Zařízení nebylo rozpoznáno", Snackbar.LENGTH_SHORT).show();
                    }
                }
                break;

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupCommunication();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Snackbar.make(findViewById(android.R.id.content), R.string.bt_not_enabled_leaving, Snackbar.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displayView(id);

        title = item.getTitle();
        setTitle(title);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }

    @Override
    public void onBackPressed() {
        if (actViewID != R.id.nav_about) {
            fragment.onBackButtonPressed(this);
        } else
            super.onBackPressed();
    }

    /**
     * Establish connection with other divice
     *
     * @param data An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     */
    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mCommunicationService.connect(device);
    }

    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private void setStatus(int resId) {

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;

        actionBar.setSubtitle(resId);
    }

    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private void setStatus(CharSequence subTitle) {

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;

        actionBar.setSubtitle(subTitle);
    }

    private void setupCommunication() {
        mCommunicationService = new BluetoothCommunicationService(this, handler);
    }

    private final Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothCommunicationService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.bluetooth_disconnected));
                            break;
                        case BluetoothCommunicationService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothCommunicationService.STATE_LISTEN:
                        case BluetoothCommunicationService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.bluetooth_off));
                            break;
                    }
                    break;

                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    break;

                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Snackbar.make(self.findViewById(android.R.id.content), readMessage, Snackbar.LENGTH_LONG).show();
                    break;

                case Constants.MESSAGE_SHOW:
                    Snackbar.make(findViewById(android.R.id.content), msg.getData().getString(Constants.TOAST), Snackbar.LENGTH_SHORT).show();
                    break;
            }

            return true;
        }
    };

    private final Handler handler = new Handler(callback);

    @Override
    public void displayView(int id) {
        if (actViewID == id) {
            return;
        }

        Log.i("displayView()", "" + id);
        ASimpleFragment oldFragment = fragment;
        switch (id) {
            case R.id.nav_item_1:
                fragment = new ERPFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;

            case R.id.nav_about:
                fragment = new AboutFragment();
                break;

            case R.id.nav_item_2:

            case R.id.nav_item_3:

            case R.id.nav_item_4:

            case R.id.nav_item_5:

            case R.id.nav_item_6:

            default:
                fragment = new AboutFragment();
                id = R.id.nav_about;
                break;
        }

        fragment.setBtCommunication(mCommunicationService);
        actViewID = id;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (oldFragment != null)
            transaction.remove(oldFragment);
        transaction.replace(R.id.frame_container, fragment).commit();
    }

    Activity self = this;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.i(TAG, "Bluetooth off");
                        final Snackbar snackbar = Snackbar.make(self.findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE);
                        snackbar.setText("Bluetooth byl vypnut");
                        snackbar.setAction("Zapnout", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mBluetoothAdapter.enable();
                                snackbar.dismiss();
                            }
                        });
                        snackbar.show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.i(TAG, "Bluetooth turning off");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.i(TAG, "Bluetooth on");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.i(TAG, "Bluetooth turning on");
                        break;
                }
            }
        }
    };
}
