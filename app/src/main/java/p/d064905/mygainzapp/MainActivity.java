package p.d064905.mygainzapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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

public class MainActivity extends Activity {
    ListView wActive;
    ListView wDeactivated;
    PlanSync Ps;
    ActiveSync As;
    Plan ActivePlan;
    ArrayList<Plan> ALactive;
    ArrayList<Plan> ALpassive;
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


    //Aufruf der DB für die Aktiven und Deaktivierten Pläne
    public void RefreshDB() {
        Ps = new PlanSync(this);
        
        Ps.execute("https://mygainzapp.appspot.com/gainzapp/plans");
    }

    //Funktion für die Listausgabe aus PlanSync
    public void fillList(ArrayList<Plan> pArray,ArrayList<Plan> wArray){
        //Aktiven Plan freigeben
        ActivePlan= wArray.get(0);

        //Deaktivierte Pläne ausgeben
        Adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,pArray);
        wDeactivated.setAdapter(Adapter1);

        wDeactivated.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                Plan Ausgewaehlt2 = (Plan)adapter.getItemAtPosition(position);
                SwitchPlans(Ausgewaehlt2);
            }
        });

        //Aktivierter Plan ausgeben
        Adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,wArray);
        wActive.setAdapter(Adapter2);

        wActive.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                Plan Ausgewaehlt = (Plan)adapter.getItemAtPosition(position);
                SwitchSession(Ausgewaehlt);
            }
        });

    }

    //Click auf aktives Workout
    public void SwitchSession(Plan p) {
        Intent intent2 = new Intent(this, WorkoutActivity.class);
        String IDübergabe =Long.toString(p.ID);
        intent2.putExtra("id",IDübergabe);
        intent2.putExtra("name",p.PlanName);
        startActivity(intent2);
    }

    //Deaktivierten Plan Aktivieren
    public void SwitchPlans(Plan p) {
            final Plan P =p;
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Plan Aktivieren")
                    .setMessage("Möchtest du diesen Plan auf aktiv setzen?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Hier wird der Plan auf aktiv gesetzt und der alte Plan deaktiviert und danach geupdated

                            UpdateDB(P);
                            RefreshDB();

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
    }

    public void UpdateDB(Plan p) {
        //Alten Plan Deaktivieren
        System.out.println(ActivePlan.ID+" "+ActivePlan.PlanActive+" wird in der DB deaktiviert");
        As = new ActiveSync(ActivePlan.ID,this,ActivePlan.PlanActive);
        As.execute();

        //Neuen Plan Aktiv setzen
        System.out.println(p.ID+" "+p.PlanActive+" wird in der DB aktiviert");
        As = new ActiveSync(p.ID,this,p.PlanActive);
        As.execute();
        }

    //PopUp Menü in Action Bar reinladen als 3 Menüpunkte oben rechts
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popupmenu, menu);
        return true;
    }
}
