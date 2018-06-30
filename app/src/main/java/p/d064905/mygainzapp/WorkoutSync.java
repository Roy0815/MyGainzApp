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

public class WorkoutSync extends AsyncTask<String, Integer, String> {

    TextView chat;

    public WorkoutSync(TextView t) {
        chat = t;
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
        try {
            JSONArray result = new JSONArray(s);
            JSONObject workout;
            System.out.println("JSON" + result);
            for (int i = 0; i < result.length(); i++) {
                workout = result.getJSONObject(i);

                //alle Variablen testweise auslegen
                chats = chats + "Name " + workout.getString("name") + "\n\n";
                chats = chats + "Gewicht " + workout.getString("weight") + "\n\n";
                chats = chats + "Pause " + workout.getString("break") + "\n\n";
                chats = chats + "Sets " + workout.getString("sets") + "\n\n";
                chats = chats + "Wiederholungen " + workout.getString("repititions") + "\n\n";
                chats = chats + "Steigerung " + workout.getString("increase") + "\n\n";

                // aus eingelesenen Daten eine lokale Übung erstellen
                new Uebung(workout.getInt("id"), workout.getString("name"), workout.getInt("weight"), workout.getInt("repititions"), workout.getInt("sets"), workout.getInt("break"), workout.getInt("increase"));
            }
        } catch (JSONException e) {
            chat.setText("kein bisheriger Chatverlauf");
            e.printStackTrace();
        }
        chat.setText(chats);
    }
}