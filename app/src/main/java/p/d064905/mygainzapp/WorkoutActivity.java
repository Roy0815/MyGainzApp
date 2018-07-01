package p.d064905.mygainzapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WorkoutActivity extends Activity {

    Button cancelworkoutbutton;
    Button endworkoutbutton;
    TextView textview;
    WorkoutSync workoutsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        cancelworkoutbutton = findViewById(R.id.workoutabbrechenButton);
        endworkoutbutton = findViewById(R.id.workoutbeendenButton);
        textview = findViewById(R.id.textView2);
        aktualisieren();
    }

    //PopUp Menü in Action Bar reinladen als 3 Menüpunkte oben rechts
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popupmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Wir prüfen, ob Menü-Element mit der ID "overview"
        // ausgewählt wurde und geben eine Meldung aus
        int id = item.getItemId();
        if (id == R.id.overview) {
            changescreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void aktualisieren() {
        workoutsync = new WorkoutSync(textview);
        workoutsync.execute("https://mygainzapp.appspot.com/gainzapp/exercises?workout=5630742793027584");
    }

    public void changescreen() {
        Intent intent0 = new Intent(this, MainActivity.class);
        startActivity(intent0);
    }

}
