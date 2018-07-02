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

public class WorkoutSync extends AsyncTask<String, Integer, String> {

    WorkoutActivity WA;

    public WorkoutSync(WorkoutActivity wa) {
        WA = wa;
    }

    @Override
    protected String doInBackground(String... urls) {
        String jsonString = "";
        for (String urlString:urls) {
            try {
                URL url = new URL(urlString);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String chats = "";
        Uebung u = null;
        ArrayList<Uebung> ALu = new ArrayList<>();
        try {
            JSONArray result = new JSONArray(s);
            JSONObject workout;
            for (int i = 0; i < result.length(); i++) {
                workout = result.getJSONObject(i);

                u = new Uebung(workout.getInt("id"), workout.getString("name"), Integer.parseInt(workout.getString("weight")), Integer.parseInt(workout.getString("repititions")), Integer.parseInt(workout.getString("sets")), Integer.parseInt(workout.getString("break")), Integer.parseInt(workout.getString("increase")));
                ALu.add(u);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WA.fillList(ALu);
    }

}