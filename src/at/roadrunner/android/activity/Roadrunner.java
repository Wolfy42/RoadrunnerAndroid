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
    
    
    /*
     *  TODO:
     *  
     *  bei APP start -> couchDB starten (meldung wenn nicht installiert)
     *  Einstellungen Dialog -> server IP einstellen
     *  local.ini editieren:
     *      - admin user anlegen
     *      - datenbank anlegen
     *      - datenbank von server replizieren (gefiltert)
     *      
     *  
     */
    
}


