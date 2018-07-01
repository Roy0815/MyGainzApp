package p.d064905.mygainzapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
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

        getActionBar().setTitle("Workout");

        cancelworkoutbutton = findViewById(R.id.workoutabbrechenButton);
        endworkoutbutton = findViewById(R.id.workoutbeendenButton);
        textview = findViewById(R.id.textView2);
        aktualisieren();

    }
    public void aktualisieren() {
        workoutsync = new WorkoutSync(textview);
        workoutsync.execute("https://mygainzapp.appspot.com/gainzapp/exercises?workout=5630742793027584");
    }

}
