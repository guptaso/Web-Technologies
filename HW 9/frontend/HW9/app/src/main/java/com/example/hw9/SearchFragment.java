package com.example.hw9;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


public class SearchFragment extends Fragment  {
    private RecyclerView recyclerView;
    private searchRecyclerAdapter msearchRecyclerAdapter;
    private ArrayList<searchCard> searchCardArrayList;
    String endpointUrl = "http://homework9999.azurewebsites.net/apis/posts/";
    String baseImagePath = "https://image.tmdb.org/t/p/w500";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        msearchRecyclerAdapter = new searchRecyclerAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(msearchRecyclerAdapter);

        setHasOptionsMenu(true);

        view.findViewById(R.id.empty).setVisibility(View.GONE);

        SearchView searchView = (SearchView) view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("NEW: ", newText);

                if (newText.length() == 0){
                    view.findViewById(R.id.empty).setVisibility(View.GONE);
                    view.findViewById(R.id.recycleView).setVisibility(View.GONE);
                } else {
                    filter(newText,view);
                }
                return false;
            }
        });

        return view;

    }


/*
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_meny, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);


                return false;
            }
        });
        //return true;
    }
*/
    private void filter(String txt, View view){
        ArrayList<searchCard> filtered = new ArrayList<>();
        List<searchCard> mDataList = new ArrayList<>();
        Context context = getActivity();

        String url = endpointUrl + "multisearch/" + txt;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int min = Math.min(20, response.length());
                for (int i = 0; i < min; i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String type = jsonObject.getString("media_type");

                        if (type.equals("movie") || type.equals("tv")){

                            final String[] title = {jsonObject.getString("name")};
                            String id = jsonObject.getString("id");
                            String img = baseImagePath + jsonObject.getString("backdrop_path");

                            String newURL = endpointUrl + "detail/" + type + "/" + id;
                            Log.e("SEARCHING: ", newURL);
                            RequestQueue queue1 = Volley.newRequestQueue(getActivity());
                            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, newURL, null,
                                    new Response.Listener<JSONObject>()
                                    {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                Log.e("SEARCH RES: ", response.toString());
                                                String rating = response.getString("vote_average").toString();
                                                if (rating == "null"){
                                                    rating = "0";
                                                } else {
                                                    rating = String.valueOf(Float.parseFloat(rating)/2);
                                                }
                                                String year;
                                                if (type.equals("movie")){
                                                    year = response.getString("release_date").toString() ;
                                                    year = "(" +  year.split("-")[0] + ")";
                                                } else {
                                                    year = response.getString("first_air_date").toString();
                                                    year = "(" +  year.split("-")[0] + ")";
                                                }




                                                Log.e("RECEIVED: ", img + type + title[0] + rating + year);
                                                searchCard msearchCard = new searchCard(img, type, title[0], rating, year, id);

                                                mDataList.add(msearchCard);
                                                msearchRecyclerAdapter.notifyDataSetChanged();
                                                msearchRecyclerAdapter.setSearchCardList(mDataList);
                                                recyclerView.setAdapter(msearchRecyclerAdapter);

                                                //searchRecyclerAdapter adapter = new searchRecyclerAdapter(getActivity(), mDataList);
                                                if (mDataList.isEmpty()){
                                                    //Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_LONG).show();
                                                    view.findViewById(R.id.empty).setVisibility(View.VISIBLE);
                                                    view.findViewById(R.id.recycleView).setVisibility(View.GONE);

                                                } else if (txt.length() < 1){
                                                    view.findViewById(R.id.empty).setVisibility(View.GONE);
                                                    view.findViewById(R.id.recycleView).setVisibility(View.GONE);
                                                }
                                                else  {
                                                    //msearchRecyclerAdapter.setSearchCardList(mDataList);
                                                    view.findViewById(R.id.empty).setVisibility(View.GONE);
                                                    view.findViewById(R.id.recycleView).setVisibility(View.VISIBLE);
                                                }



                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
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
                            queue1.add(getRequest);
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                msearchRecyclerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

        if (mDataList.isEmpty()){
            view.findViewById(R.id.empty).setVisibility(View.VISIBLE);
            view.findViewById(R.id.recycleView).setVisibility(View.GONE);

        } else {
            msearchRecyclerAdapter.setSearchCardList(mDataList);
            view.findViewById(R.id.empty).setVisibility(View.GONE);
            view.findViewById(R.id.recycleView).setVisibility(View.VISIBLE);

        }
    }


}

