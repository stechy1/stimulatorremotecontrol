package cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.ui.ASimpleFragment;

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
