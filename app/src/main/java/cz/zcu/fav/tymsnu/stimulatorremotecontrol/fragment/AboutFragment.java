package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.BuildConfig;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

public class AboutFragment extends ASimpleFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        WebView browser = (WebView)v.findViewById(R.id.webView);
        browser.loadUrl("file:///android_asset/changelog.html");

        return v;
    }

}
