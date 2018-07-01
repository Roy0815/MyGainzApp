package p.d064905.mygainzapp;


import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madeya on 11.01.2018.
 */

public class PlanSync extends AsyncTask<String,Integer,String> {

    TextView AusgabeA;
    ListView AusgabeD;
    String Name;
    Boolean Active;
    ArrayAdapter<Plan> mAdapter;
    ArrayList<Plan> mArray;

    public PlanSync(TextView v, ListView w){
        AusgabeA= v;
        AusgabeD=w;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mArray = new ArrayList<>();
        try {
            JSONArray result = new JSONArray(s);
            JSONObject message;
            for (int i = 0; i < result.length(); i++) {
                message = result.getJSONObject(i);

                Name = message.getString("name");
                Active = message.getBoolean("active");
                if (Active=true){
                    AusgabeA.setText(Name);
                }
                if (Active=false){
                    mArray.add(new Plan(Name,Active));
                }
            }
           //Hier muss noch was angepasst werden   mAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mArray);
            AusgabeD.setAdapter(mAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected String doInBackground(String... urls) {
        String jsonString = "";
        for (String urlString:urls) {
            try {
                URL url = new URL(urlString);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = "";
                while ((line = reader.readLine()) != null){
                    jsonString += line;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return jsonString;
    }
}

