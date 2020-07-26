package com.example.moviedisplayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recent extends Fragment {

    Button button;
    String jsonString;
    RecyclerViewAdapter adapter;
    RecyclerViewAdapter.ItemClickListener listener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public recent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recent.
     */
    // TODO: Rename and change types and number of parameters
    public static recent newInstance(String param1, String param2) {
        recent fragment = new recent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recent, container, false);
        final ArrayList<String> titleNames = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://api.themoviedb.org/3/movie/popular?api_key=306336cb31c7909d625fb19cfe981f1a&language=en-US&page=1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray resultsArray = object.getJSONArray("results");
                            for (int i=0; i < resultsArray.length(); i++)
                            {
                                try {
                                    JSONObject oneObject = resultsArray.getJSONObject(i);
                                    String oneObjectsItem = oneObject.getString("title");
                                    titleNames.add(oneObjectsItem);
                                    Log.i("result",oneObjectsItem);
                                } catch (JSONException e) {
                                    // Oops
                                }
                            }
                            adapter.addItems(titleNames);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("response",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error","That didnt work " + error);
            }
        });
        queue.add(stringRequest);




        // set up the RecyclerView
        setOnClickListner();

        final RecyclerView recyclerView = root.findViewById(R.id.recentView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerViewAdapter(getContext(), titleNames,listener);
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }

    private void setOnClickListner() {
        listener = new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getContext(),Details.class);
                startActivity(i);            }
        };
    }


}