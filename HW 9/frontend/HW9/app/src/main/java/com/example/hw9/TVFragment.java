package com.example.hw9;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class TVFragment extends Fragment implements View.OnClickListener{
    String baseUrl = "https://image.tmdb.org/t/p/w500";
    String endpoint = "http://homework9999.azurewebsites.net/apis/posts/";

    public TVFragment(){
        super(R.layout.fragment_tv);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv, container, false);

        getCurrentMovies(view);
        getTopRatedMovies(view, inflater, this);
        getPopularMovies(view, inflater, this);

        TextView footer = (TextView) view.findViewById(R.id.footerP1);
        footer.setOnClickListener(this);


        return view;

    }

    private void getTopRatedMovies(View view, LayoutInflater inflater, View.OnClickListener onClickListener){
        String URL = endpoint + "topRated/tv";
        LinearLayout gal = view.findViewById(R.id.topRated);
        TextView textView = view.findViewById(R.id.mainContent);

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray s1 = null;
                ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
                try {
                    s1 = ((JSONObject)response).getJSONArray("results");
                    Log.e("TOP RATED: ", s1.toString());
                    int minLength = Math.min(10, s1.length());
                    for (int i = 0; i < minLength; i++){
                        String imgUrl = baseUrl + s1.getJSONObject(i).getString("poster_path");
                        String id = s1.getJSONObject(i).getString("id");
                        String name = s1.getJSONObject(i).getString("name");
                        sliderDataArrayList.add(new SliderData(baseUrl + s1.getJSONObject(i).getString("poster_path"), id, "tv"));
                        View mview = inflater.inflate(R.layout.gallery_item, gal, false);
                        ImageView img = (ImageView) mview.findViewById(R.id.galley_item_img);
                        img.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                openDetailsActivity(id, "tv");
                            }
                        });
                        Glide.with(mview).load(imgUrl).transform(new RoundedCorners(80)).into(img);
                        ImageButton imgBtn = (ImageButton) mview.findViewById(R.id.popupBtn);
                        imgBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.e("MSG", "HITTTTTTTTT");
                                PopupMenu popup = new PopupMenu(getActivity(), mview);
                                MenuInflater inflater1 = popup.getMenuInflater();
                                inflater1.inflate(R.menu.popup_menu, popup.getMenu());

                                // CHECK IF ID IS IN SHARED PREFERENCES
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("watchlist", Context.MODE_PRIVATE);
                                Menu edit = popup.getMenu();
                                MenuItem menuItem = edit.findItem(R.id.watchlist);
                                if (sharedPreferences.contains(id)){
                                    menuItem.setTitle("Remove from Watchlist");
                                } else {
                                    menuItem.setTitle("Add to Watchlist");
                                }

                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getItemId()){
                                            case R.id.openTMDB:
                                                String url = "http://www.themoviedb.org/tv/" + id;
                                                Uri uri = Uri.parse(url);
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri.parse(url));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.setPackage("com.android.chrome");
                                                startActivity(intent);
                                                return true;
                                            case R.id.shareFace:
                                                url = "https://www.facebook.com/sharer/sharer.php?u=";
                                                url += "http://www.themoviedb.org/tv/" + id + "%2F&amp;src=sdkpreparse";
                                                uri = Uri.parse(url);
                                                Log.e("MSG", url);
                                                intent = new Intent(Intent.ACTION_VIEW, uri.parse(url));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.setPackage("com.android.chrome");
                                                startActivity(intent);
                                                return true;
                                            case R.id.shareTwitter:
                                                url = "https://twitter.com/intent/tweet?text=Check%20This%20Out!%20";
                                                url += "http://www.themoviedb.org/tv/" + id + "%2F&amp;src=sdkpreparse";
                                                uri = Uri.parse(url);
                                                Log.e("MSG", url);
                                                intent = new Intent(Intent.ACTION_VIEW, uri.parse(url));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.setPackage("com.android.chrome");
                                                startActivity(intent);
                                                return true;
                                            case R.id.watchlist:
                                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("watchlist", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                                Map<String, ?> all = sharedPreferences.getAll();
                                                String current = menuItem.getTitle().toString();
                                                if (current.equals("Add to Watchlist")){
                                                    editor.putString(id, "tv");
                                                    editor.commit();
                                                    Toast.makeText(getActivity(), name+" was added to Watchlist", Toast.LENGTH_LONG).show();
                                                } else {
                                                    editor.remove(id);
                                                    editor.commit();
                                                    Toast.makeText(getActivity(), name+" was removed from Watchlist", Toast.LENGTH_LONG).show();
                                                }
                                                all = sharedPreferences.getAll();
                                                String num = String.valueOf(all.size());
                                                return true;
                                            default:
                                                return false;
                                        }
                                    }
                                });
                                popup.show();
                            }
                        });
                        gal.addView(mview);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);

    }

    private void getCurrentMovies(View view) {
        String URL = endpoint + "trending/tv";

        TextView textView = view.findViewById(R.id.mainContent);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray s1 = null;
                ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
                try {
                    s1 = ((JSONObject)response).getJSONArray("results");
                    int minLength = Math.min(6, s1.length());

                    for (int i = 0; i < minLength; i++){
                        sliderDataArrayList.add(new SliderData(baseUrl + s1.getJSONObject(i).getString("poster_path"), s1.getJSONObject(i).getString("id"), "tv"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SliderView sliderView = view.findViewById(R.id.slider);
                SliderAdapter adapter = new SliderAdapter(sliderDataArrayList);
                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                sliderView.setSliderAdapter(adapter);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);

    }

    private void getPopularMovies(View view, LayoutInflater inflater, View.OnClickListener onClickListener) {
        String URL = endpoint + "popular/tv";

        LinearLayout gal = view.findViewById(R.id.popular);
        TextView textView = view.findViewById(R.id.mainContent);

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray s1 = null;
                ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
                try {
                    s1 = ((JSONObject)response).getJSONArray("results");
                    int minLength = Math.min(10, s1.length());
                    for (int i = 0; i < minLength; i++){
                        String imgUrl = baseUrl + s1.getJSONObject(i).getString("poster_path");
                        String id = s1.getJSONObject(i).getString("id");
                        String name = s1.getJSONObject(i).getString("name");
                        sliderDataArrayList.add(new SliderData(baseUrl + s1.getJSONObject(i).getString("poster_path"),id, "tv"));
                        View mview = inflater.inflate(R.layout.gallery_item, gal, false);
                        ImageView img = (ImageView) mview.findViewById(R.id.galley_item_img);
                        img.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                openDetailsActivity(id, "tv");
                            }
                        });
                        Glide.with(mview).load(imgUrl).transform(new RoundedCorners(80)).into(img);
                        ImageButton imgBtn = (ImageButton) mview.findViewById(R.id.popupBtn);
                        imgBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.e("MSG", "HITTTTTTTTT");
                                PopupMenu popup = new PopupMenu(getActivity(), mview);
                                MenuInflater inflater1 = popup.getMenuInflater();
                                inflater1.inflate(R.menu.popup_menu, popup.getMenu());

                                // CHECK IF ID IS IN SHARED PREFERENCES
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("watchlist", Context.MODE_PRIVATE);
                                Menu edit = popup.getMenu();
                                MenuItem menuItem = edit.findItem(R.id.watchlist);
                                if (sharedPreferences.contains(id)){
                                    menuItem.setTitle("Remove from Watchlist");
                                } else {
                                    menuItem.setTitle("Add to Watchlist");
                                }

                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getItemId()){
                                            case R.id.openTMDB:
                                                String url = "http://www.themoviedb.org/tv/" + id;
                                                Uri uri = Uri.parse(url);
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri.parse(url));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.setPackage("com.android.chrome");
                                                startActivity(intent);
                                                return true;
                                            case R.id.shareFace:
                                                url = "https://www.facebook.com/sharer/sharer.php?u=";
                                                url += "http://www.themoviedb.org/tv/" + id + "%2F&amp;src=sdkpreparse";
                                                uri = Uri.parse(url);
                                                Log.e("MSG", url);
                                                intent = new Intent(Intent.ACTION_VIEW, uri.parse(url));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.setPackage("com.android.chrome");
                                                startActivity(intent);
                                                return true;
                                            case R.id.shareTwitter:
                                                url = "https://twitter.com/intent/tweet?text=Check%20This%20Out!%20";
                                                url += "http://www.themoviedb.org/tv/" + id + "%2F&amp;src=sdkpreparse";
                                                uri = Uri.parse(url);
                                                Log.e("MSG", url);
                                                intent = new Intent(Intent.ACTION_VIEW, uri.parse(url));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.setPackage("com.android.chrome");
                                                startActivity(intent);
                                                return true;
                                            case R.id.watchlist:
                                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("watchlist", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                                Map<String, ?> all = sharedPreferences.getAll();
                                                String current = menuItem.getTitle().toString();
                                                if (current.equals("Add to Watchlist")){
                                                    editor.putString(id, "tv");
                                                    editor.commit();
                                                    Toast.makeText(getActivity(), name+" was added to Watchlist", Toast.LENGTH_LONG).show();
                                                } else {
                                                    editor.remove(id);
                                                    editor.commit();
                                                    Toast.makeText(getActivity(), name+" was removed from Watchlist", Toast.LENGTH_LONG).show();
                                                }
                                                all = sharedPreferences.getAll();
                                                String num = String.valueOf(all.size());
                                                return true;
                                            default:
                                                return false;
                                        }
                                    }
                                });
                                popup.show();
                            }
                        });
                        gal.addView(mview);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);

    }


    public void openDetailsActivity(String id, String type){
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.footerP1:
                String url = "http://www.themoviedb.org/";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                startActivity(intent);
        }

    }
}
