package p.d064905.mygainzapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ExerciseSync extends AsyncTask<String, Integer, String> {

    ExerciseActivity WA;

    public ExerciseSync(ExerciseActivity wa) {
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
        System.out.println("START ONPOSTEXE: " + s);
        String chats = "";
        Uebung u = null;
        ArrayList<Uebung> ALu = new ArrayList<>();
        try {
            JSONArray result = new JSONArray(s);
            JSONObject workout;
            for (int i = 0; i < result.length(); i++) {
                workout = result.getJSONObject(i);

                u = new Uebung(workout.getInt("id"), workout.getString("name"), Integer.parseInt(workout.getString("weight")), Integer.parseInt(workout.getString("repititions")), Integer.parseInt(workout.getString("sets")), Integer.parseInt(workout.getString("break")), Integer.parseInt(workout.getString("increase")));
                System.out.println(workout.getString("name"));
                ALu.add(u);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WA.fillList(ALu);
    }

}