package p.d064905.mygainzapp;

import android.app.ActionBar;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toolbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.support.v4.view.WindowCompat.FEATURE_ACTION_BAR;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView wActive;
    ArrayList<Plan>  mArray;
    ArrayAdapter<Plan>  mAdapter;
    PlanSync Ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wActive = findViewById(R.id.AW);
        mArray = new ArrayList<>();

        mAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mArray);
        wActive.setAdapter(mAdapter);
        wActive.setOnItemClickListener(this);
        onRefresh();

    }

    public void onRefresh() {
        Ps = new PlanSync(mArray);
        Ps.execute("https://mygainzapp.appspot.com/gainzapp/plans");}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //onclick Workout
    }
}
