package at.roadrunner.android.activity;

import android.app.Activity;
import android.os.Bundle;
import at.roadrunner.android.R;

public class Roadrunner extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}


