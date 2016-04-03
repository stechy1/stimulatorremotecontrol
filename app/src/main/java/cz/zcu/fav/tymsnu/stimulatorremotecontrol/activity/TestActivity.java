package cz.zcu.fav.tymsnu.stimulatorremotecontrol.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import cz.zcu.fav.tymsnu.stimulatorremotecontrol.R;


public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        LinearLayout root = (LinearLayout) findViewById(R.id.root);

        //PatternControl patternControl = (PatternControl) root.findViewById(R.id.testPatternControl);

//        }
    }

}
