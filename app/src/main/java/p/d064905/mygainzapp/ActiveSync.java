package p.d064905.mygainzapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActiveSync extends AsyncTask<String,Integer,String> {
    String Plan;
    String Active;
    RequestQueue mRequestQueue;
    MainActivity MainContext;


    public ActiveSync(Long id, MainActivity c,Boolean a){
        Plan = Long.toString(id);
        MainContext = c;
        Active =Boolean.toString(a);

    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mRequestQueue= Volley.newRequestQueue(MainContext);

        String url = "https://mygainzapp.appspot.com/gainzapp/put/plan";
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        System.out.println(Active);
                        if (Active.equals("false")){
                            MainContext.RefreshDB();
                            System.out.println("update");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "huan");
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String> ();
                if (Active.equals("false")){
                    params.put("id",Plan);
                    params.put("active", "true");
                }
                if (Active.equals("true")){
                    params.put("id",Plan);
                    params.put("active", "false");
                }
                return params;
            }

        };

        mRequestQueue.add(putRequest);
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;

    }
}


