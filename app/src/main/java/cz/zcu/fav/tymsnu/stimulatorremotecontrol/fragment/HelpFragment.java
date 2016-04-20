package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

public class HelpFragment extends ASimpleFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);

        WebView browser = (WebView)v.findViewById(R.id.webView);
        browser.loadUrl("file:///android_asset/help.html");

        return v;
    }
}
