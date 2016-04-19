package cz.zcu.fav.tymsnu.stimulatorremotecontrol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.File;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.Constants;
import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;

public class HelpFragment extends ASimpleFragment {

    private WebView browser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);

        browser = (WebView) v;

        browser.loadUrl("file:///android_asset/help.html");

        return v;
    }

    private File getFileWithHelp() {
        File root = getActivity().getFilesDir();

        return new File(root, Constants.FILE_HELP);
    }

}
