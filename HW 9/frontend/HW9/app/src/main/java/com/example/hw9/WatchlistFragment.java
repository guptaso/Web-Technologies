package com.example.hw9;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarException;

public class WatchlistFragment extends Fragment {
    String endpoint = "http://homework9999.azurewebsites.net/apis/posts/";

    // Bottom Nav Stuff
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        boolean initRecycle = false;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("watchlist", Context.MODE_PRIVATE);
        Map<String, ?> all = sharedPreferences.getAll();
        if (all.size() == 0) {
            view.findViewById(R.id.emptyFrag).setVisibility(View.VISIBLE);
            view.findViewById(R.id.recycleView).setVisibility(View.GONE);
            initRecycle = false;
        } else {
            view.findViewById(R.id.emptyFrag).setVisibility(View.GONE);
            view.findViewById(R.id.recycleView).setVisibility(View.VISIBLE);
            initRecycle = true;
        }

        if (initRecycle == true){
            // initialize recyclerview
            RecyclerView recyclerView = view.findViewById(R.id.recycleView);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            recyclerView.setLayoutManager(gridLayoutManager);

            // Create the list and get the image url
            List<movieData> mDataList = new ArrayList<>();
            for (String id : all.keySet()){
                Log.e("MSG: ", id + ": " + all.get(id).toString());
                String myUrl = endpoint + "detail/";
                if (all.get(id).toString().equals("movie")) {
                    myUrl += "movie/" + id.toString();
                } else {
                    myUrl += "tv/" + id.toString();
                }

                // get image url
                RequestQueue queue = Volley.newRequestQueue(getActivity()); // this = context
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null,
                        new Response.Listener<JSONObject>()
                        {
                            String imgUrl = "https://image.tmdb.org/t/p/w500";
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.e("Response", response.toString());
                                try {
                                    Log.e("Parsed: ", response.getString("poster_path").toString());
                                    imgUrl += response.getString("poster_path").toString();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                movieData mData = new movieData(id, imgUrl, all.get(id).toString());
                                Log.e("DATA: ", mData.toString());
                                mDataList.add(mData);
                                recyclerViewAdapter4 adapter = new recyclerViewAdapter4(getContext(), mDataList);
                                recyclerView.setAdapter(adapter);

                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error.Response", "response");
                            }
                        }
                );
                queue.add(getRequest);

            }
            Log.e("LIST: ", mDataList.toString());
            recyclerViewAdapter4 adapter = new recyclerViewAdapter4(getContext(), mDataList);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }



}
