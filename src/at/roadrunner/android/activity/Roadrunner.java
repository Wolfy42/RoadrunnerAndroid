package at.roadrunner.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import at.roadrunner.android.R;

public class Roadrunner extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadrunner);
    }
    
    /*
     * Event loadItem
     */
    public void loadItem(View view) {
    	
    }
    
    /*
     * Event unloadItem
     */
    public void unloadItem(View view) {
    	
    }
    
    /*
     * inflate menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.roadrunner_menu, menu);
        return true;
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


