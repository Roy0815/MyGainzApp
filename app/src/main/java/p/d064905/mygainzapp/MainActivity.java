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

public class MainActivity extends Activity  {
    ListView wActive;
    ListView wDeactivated;
    PlanSync Ps;
    ArrayAdapter<Plan> Adapter1;
    ArrayAdapter<Plan> Adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wActive = findViewById(R.id.AW);
        wDeactivated= findViewById(R.id.DW);
        RefreshDB();
    }

    //Funktion für die Listausgabe aus PlanSync
    public void fillList(ArrayList<Plan> pArray,ArrayList<Plan> wArray){

        Adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,pArray);
        wDeactivated.setAdapter(Adapter1);

        Adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,wArray);
        wActive.setAdapter(Adapter2);

    }
    //Aufruf der DB für die Aktiven und Deaktivierten Pläne
    public void RefreshDB() {
        Ps = new PlanSync(this);
        Ps.execute("https://mygainzapp.appspot.com/gainzapp/plans");
    }

    //PopUp Menü in Action Bar reinladen als 3 Menüpunkte oben rechts
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popupmenu, menu);
        return true;
    }

    public void changescreen(View aView) {
        Intent intent2 = new Intent(this, WorkoutActivity.class);
        startActivity(intent2);
    }
}
