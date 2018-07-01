package p.d064905.mygainzapp;


import android.os.AsyncTask;
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

/**
 * Created by Madeya on 11.01.2018.
 */

public class PlanSync extends AsyncTask<String,Integer,String> {

    ArrayList<Plan> planarray;
    String Name;
    Boolean Active;

    public PlanSync(ArrayList v){planarray = v;}

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String messages = "";
        try {
            JSONArray result = new JSONArray(s);
            JSONObject message;
            for (int i = 0; i < result.length(); i++) {
                message = result.getJSONObject(i);

                Name = message.getString("name");
                Active = message.getBoolean("active");
                planarray.add(new Plan(Name,Active));
            }
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

