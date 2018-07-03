package p.d064905.mygainzapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WorkoutActivity extends Activity {

    String planID;
    Intent workoutIntent;
    WorkoutSync ws;
    ListView LVWorkout;
    ArrayAdapter<Workout> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        workoutIntent = getIntent();
        planID = workoutIntent.getStringExtra("id");
        LVWorkout = findViewById(R.id.WorkoutList);

        ws = new WorkoutSync(this);
        ws.execute("https://mygainzapp.appspot.com/gainzapp/workouts?plan=" + planID);

        getActionBar().setTitle(workoutIntent.getStringExtra("name"));
    }

    public void fillList(ArrayList<Workout> ALu){
        if (ALu.isEmpty()){
            ALu.add(new Workout("Es sind keine Workouts in diesem Plan vorhanden.", 0, "123456"));
            aa = new ArrayAdapter<Workout>(this,android.R.layout.simple_list_item_1,ALu);
            LVWorkout.setAdapter(aa);
        }else {
            aa = new ArrayAdapter<Workout>(this,android.R.layout.simple_list_item_1,ALu);
            LVWorkout.setAdapter(aa);
            LVWorkout.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
                {
                    Workout actualWO = (Workout)adapter.getItemAtPosition(position);
                    startWorkout(actualWO.id, actualWO.name);
                }
            });
        }
    }

    public void startWorkout(long id, String name){
        Intent intentWO = new Intent(this, ExerciseActivity.class);
        String idUebergabe = Long.toString(id);
        intentWO.putExtra("id", idUebergabe);
        intentWO.putExtra("name", name);
        startActivity(intentWO);
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
        // ausgewählt wurde und springen dann auf den "Übersicht"-Screen
        int id = item.getItemId();
        if (id == R.id.overview) {
            changescreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changescreen() {
        //Intent intent0 = new Intent(this, MainActivity.class);
        //startActivity(intent0);
        finish();
    }
}
