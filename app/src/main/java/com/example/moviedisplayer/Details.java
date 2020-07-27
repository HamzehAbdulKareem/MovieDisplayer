package com.example.moviedisplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Details extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("ID");

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://api.themoviedb.org/3/movie/"+id+"?api_key=306336cb31c7909d625fb19cfe981f1a&language=en-US";



        final TextView titleText = findViewById(R.id.titleText);
        final TextView popText = findViewById(R.id.popText);
        final TextView overViewText = findViewById(R.id.overViewText);
        final TextView adultText = findViewById(R.id.adultText);
        final TextView stateText = findViewById(R.id.stateText);
        final TextView vAverageText = findViewById(R.id.vAverageText);
         final TextView voteCountsText = findViewById(R.id.vCountText);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                     String title;
                    Double popularity;
                    String overview;
                    Boolean adult;
                    String state;
                    Double voteaverage;
                    Integer votecounts;
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                             title = object.getString("title");
                             popularity = object.getDouble("popularity");
                             overview = object.getString("overview");
                             adult = object.getBoolean("adult");
                             state = object.getString("status");
                             voteaverage = object.getDouble("vote_average");
                             votecounts = object.getInt("vote_count");
                            JSONArray resultsArray = object.getJSONArray("results");
                            for (int i=0; i < resultsArray.length(); i++)
                            {
                                try {
                                    JSONObject oneObject = resultsArray.getJSONObject(i);
                                    String oneObjectsItem = oneObject.getString("title");
                                    Integer id = oneObject.getInt("id");
//                                    titleNames.add(oneObjectsItem);
//                                    ids.add(id);
                                    Log.i("result",oneObjectsItem);
                                } catch (JSONException e) {
                                    // Oops
                                }
                            }
//                            adapter.addItems(titleNames);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("response",response);
                        Log.i("title",title);

                        titleText.setText(titleText.getText() + title);
                        popText.setText(popText.getText() + popularity.toString());
                        overViewText.setText(overViewText.getText() + overview);
                        adultText.setText(adultText.getText() + adult.toString());
                        stateText.setText(stateText.getText() + state);
                        vAverageText.setText(vAverageText.getText() + voteaverage.toString());
                        voteCountsText.setText(voteCountsText.getText() + votecounts.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error","That didnt work " + error);
            }
        });
        queue.add(stringRequest);
    }
}