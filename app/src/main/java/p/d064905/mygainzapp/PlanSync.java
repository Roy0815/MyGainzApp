package p.d064905.mygainzapp;


import android.content.Context;
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

    MainActivity Activity;
    String Name;
    String Active;
    Long ID;
    ArrayList<Plan> pArray;
    ArrayList<Plan> wArray;


    public PlanSync(MainActivity m){
        Activity =m;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pArray = new ArrayList<>();
        wArray = new ArrayList<>();

        try {
            JSONArray result = new JSONArray(s);
            JSONObject message;
            for (int i = 0; i < result.length(); i++) {
                message = result.getJSONObject(i);

                Name = message.getString("name");
                Active = message.getString("active");
                ID= message.getLong("id");
                System.out.println(Name+" "+ID+" "+Active);

                if (Active.equals("true")){
                    wArray.add(new Plan(Name,true,ID));
                }
               if (Active.equals("false")){
                    pArray.add(new Plan(Name,false,ID));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Activity.fillList(pArray,wArray);

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

