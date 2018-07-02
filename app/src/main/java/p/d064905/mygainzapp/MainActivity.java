package p.d064905.mygainzapp;

import android.app.ActionBar;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

import static android.support.v4.view.WindowCompat.FEATURE_ACTION_BAR;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    TextView wActive;
    ListView wDeactivated;
    PlanSync Ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wActive = findViewById(R.id.AW);
        wDeactivated= findViewById(R.id.DW);
        onRefresh();
    }

    public void onRefresh() {
        //Aufruf der DB für die Aktiven und Deaktivierten Pläne
        Ps = new PlanSync(wActive,wDeactivated);
        Ps.execute("https://mygainzapp.appspot.com/gainzapp/plans");
    }

    //PopUp Menü in Action Bar reinladen als 3 Menüpunkte oben rechts
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popupmenu, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //onclick Workout
    }

    public void changescreen(View aView) {
        Intent intent2 = new Intent(this, WorkoutActivity.class);
        startActivity(intent2);
    }
}
