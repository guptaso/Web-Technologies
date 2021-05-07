package com.example.hw9;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

public class DetailsActivity extends AppCompatActivity  {
   // String endpointUrl = "http://172.17.160.1:8080/apis/posts/";
    String endpointUrl = "http://homework9999.azurewebsites.net/apis/posts/";
    String baseImagePath = "https://image.tmdb.org/t/p/w500";
    private Context context;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String type = intent.getStringExtra("type");
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_details);

        context = getApplicationContext();
        activity = DetailsActivity.this;


        setVideo(id, type);
        setTitle(id, type);
        setCast(id, type);
        setReviews(id, type);
        setRecommend(id, type);

    }

    public void setVideo(String id, String type){
        String url = endpointUrl + "video/" + type + '/' + id;
        Context context = this;
        RequestQueue queue = Volley.newRequestQueue(this); // this = context
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    String videoURL = null;
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("Response", response.toString());
                        JSONArray parsed = null;
                        String videoId = null;
                        try {
                            Log.e("Parsed: ", response.getString("results").toString());
                            parsed = ((JSONObject)response).getJSONArray("results");
                            for (int i = 0; i < parsed.length(); i++){
                                String videoType = parsed.getJSONObject(i).getString("type");
                                if (videoType.equals("Trailer")){
                                    videoId = parsed.getJSONObject(i).getString("key");
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (videoId != null){
                            Log.e("AVAIL: ", "in if");
                            findViewById(R.id.absentYoutube).setVisibility(View.GONE);
                            findViewById(R.id.youtubePlayer).setVisibility(View.VISIBLE);
                            YouTubePlayerView youTubePlayerView = findViewById(R.id.youtubePlayer);
                            getLifecycle().addObserver(youTubePlayerView);
                            String finalVideoId = videoId;
                            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(YouTubePlayer youTubePlayer) {
                                    youTubePlayer.loadVideo(finalVideoId, 0);
                                }
                            });
                        } else {
                            Log.e("AVAIL: ", "in else");
                            findViewById(R.id.absentYoutube).setVisibility(View.VISIBLE);
                            findViewById(R.id.youtubePlayer).setVisibility(View.GONE);
                            ImageView img = findViewById(R.id.absentYoutube);


                            String url = endpointUrl + "detail/" + type + "/" + id;
                            RequestQueue queue = Volley.newRequestQueue(context); // this = context
                            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                                    new Response.Listener<JSONObject>()
                                    {
                                        String backPath;
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // display response
                                            Log.e("Response", response.toString());
                                            try {
                                                Log.e("Parsed: ", response.getString("backdrop_path").toString());
                                                backPath = response.getString("backdrop_path").toString();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            String backdropPath = baseImagePath + backPath;
                                            Log.e("PATH: ", backdropPath);
                                            Glide.with(context).load(backdropPath).into(img);
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

    public void setTitle(String id, String type){
        String url = endpointUrl + "detail/" + type + "/" + id;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    String title;
                    String overview;
                    String genres = "";
                    String year;

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response", response.toString());
                        try {
                            Log.e("TITLE RES: ", response.toString());
                            if (type.equals("movie")){
                                //Log.e("Title Parsed: ", response.getString("name").toString());

                                title = response.getString("title").toString();
                                year = response.getString("release_date").toString();
                                year = year.split("-")[0];

                            } else {
                                title = response.getString("name").toString();
                                year = response.getString("first_air_date");
                                year = year.split("-")[0];

                            }
                            overview = response.getString("overview").toString();
                            //[{"id":35,"name":"Comedy"},{"id":18,"name":"Drama"},{"id":10749,"name":"Romance"}]
                            JSONArray g = ((JSONObject)response).getJSONArray("genres");
                            Log.e("GENRES: ", g.toString());
                            for (int i = 0; i < g.length(); i++){
                                genres += g.getJSONObject(i).getString("name");
                                if (i < g.length()-1){
                                    genres += ", ";
                                }
                            }
                            Log.e("GENRES: ", genres.toString());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        TextView titleView = findViewById(R.id.title);
                        titleView.setText(title);
                        TextView overviewView = findViewById(R.id.overviewText);
                        overviewView.setText(overview);
                        TextView genresView = findViewById(R.id.genresText);
                        genresView.setText(genres);
                        TextView yearView = findViewById(R.id.yearText);
                        yearView.setText(year);
                        setShare(id, type, title);
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

    public void setCast(String id, String type){
        String url = endpointUrl + "cast/" + type + "/" + id;
        RequestQueue queue = Volley.newRequestQueue(context);
        Context context = this;
        RecyclerView recyclerView = findViewById(R.id.cast);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        List<castData> mDataList = new ArrayList<>();

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray parsed = null;
                        String img = null;
                        String name = null;

                        try {
                            Log.e("Cast Parsed: ", response.getString("cast").toString());
                            parsed = ((JSONObject)response).getJSONArray("cast");
                            int min = Math.min(parsed.length(), 6);
                            for (int i=0; i<min; i++){
                                img = baseImagePath + parsed.getJSONObject(i).getString("profile_path");
                                name = parsed.getJSONObject(i).getString("name");
                                castData mData = new castData(img, name);
                                mDataList.add(mData);
                                recycleViewAdapter2 adapter = new recycleViewAdapter2(context, mDataList);
                                recyclerView.setAdapter(adapter);

                            }
                            if (parsed.length() == 0) {
                                findViewById(R.id.castTitle).setVisibility(View.GONE);
                                findViewById(R.id.cast).setVisibility(View.GONE);
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
        queue.add(getRequest);
        recycleViewAdapter2 adapter = new recycleViewAdapter2(context, mDataList);
        recyclerView.setAdapter(adapter);

    }

    public void setReviews(String id, String type){
        String url = endpointUrl + "review/" + type + "/" + id;
        RequestQueue queue = Volley.newRequestQueue(context);
        RecyclerView recyclerView = findViewById(R.id.review);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        List<reviewData> mDataList = new ArrayList<>();


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray parsed = null;
                        String rating;
                        String username;
                        String content;
                        String creation;

                        try {
                            Log.e("REVIEWS: ", response.getString("results").toString());
                            parsed = ((JSONObject)response).getJSONArray("results");
                            int x = parsed.length();
                            Log.e("LENGTH: ", String.valueOf(x));
                            int min = Math.min(parsed.length(), 3);
                            for (int i=0; i<min; i++){
                                rating = parsed.getJSONObject(i).getJSONObject("author_details").getString("rating");
                                if (rating == "null"){
                                    rating = "0";
                                } else {
                                    rating = String.valueOf(Float.parseFloat(rating)/2);
                                }
                                username = parsed.getJSONObject(i).getJSONObject("author_details").getString("username");
                                content = parsed.getJSONObject(i).getString("content");
                                creation = parsed.getJSONObject(i).getString("updated_at");
                                if (creation != "null"){
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    SimpleDateFormat newSDF = new SimpleDateFormat("EEE, MMM dd yyyy");
                                    Date temp = null;
                                    try {
                                        temp = sdf.parse(creation);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    creation = newSDF.format(temp);
                                }
                                Log.e("REV: ", rating + username + content + creation);
                                reviewData mData = new reviewData(rating, username, content, creation);
                                mDataList.add(mData);
                                recycleViewAdapter3 adapter = new recycleViewAdapter3(context, mDataList);
                                recyclerView.setAdapter(adapter);

                            }
                            if (x == 0){
                                findViewById(R.id.reviewTitle).setVisibility(View.GONE);
                                findViewById(R.id.review).setVisibility(View.GONE);
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
        queue.add(getRequest);
        recycleViewAdapter3 adapter = new recycleViewAdapter3(context, mDataList);
        recyclerView.setAdapter(adapter);

    }

    public void setRecommend(String id, String type){
        String URL = endpointUrl + "recommended/" + type + "/" + id;
        LinearLayout gal = findViewById(R.id.recc);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray s1 = null;
                ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
                try {
                    s1 = ((JSONObject)response).getJSONArray("results");
                    int minLength = Math.min(10, s1.length());
                    for (int i = 0; i < minLength; i++){
                        String imgUrl = baseImagePath + s1.getJSONObject(i).getString("poster_path");
                        String mid = s1.getJSONObject(i).getString("id");
                        String mType = type;
                        sliderDataArrayList.add(new SliderData(baseImagePath + s1.getJSONObject(i).getString("poster_path"), id, type));
                        View mview = getLayoutInflater().inflate(R.layout.gallery_item, gal, false);
                        ImageView img = (ImageView) mview.findViewById(R.id.galley_item_img);
                        img.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                openDetailsActivity(mid, mType);
                            }
                        });
                        Glide.with(mview).load(imgUrl).transform(new RoundedCorners(80)).into(img);
                        ImageButton imgBtn = (ImageButton) mview.findViewById(R.id.popupBtn);
                        imgBtn.setVisibility(View.GONE);
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

    public void setShare(String id, String type, String name){
        SharedPreferences sharedPreferences = getSharedPreferences("watchlist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Context context = this;
        if (sharedPreferences.contains(id)){
            findViewById(R.id.add).setVisibility(View.GONE);
            findViewById(R.id.remove).setVisibility(View.VISIBLE);

        } else {
            findViewById(R.id.add).setVisibility(View.VISIBLE);
            findViewById(R.id.remove).setVisibility(View.GONE);
        }
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(id, type);
                editor.commit();
                Toast.makeText(context, name+" was added to Watchlist", Toast.LENGTH_LONG).show();
                findViewById(R.id.add).setVisibility(View.GONE);
                findViewById(R.id.remove).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.remove(id);
                editor.commit();
                Toast.makeText(context, name + " was removed from Watchlist", Toast.LENGTH_LONG).show();
                findViewById(R.id.add).setVisibility(View.VISIBLE);
                findViewById(R.id.remove).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.shareFaceBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/sharer/sharer.php?u=";
                url += "http://www.themoviedb.org/" + type + "/" + id + "%2F&amp;src=sdkpreparse";
                Uri uri = Uri.parse(url);
                Log.e("MSG", url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                startActivity(intent);
            }
        });
        findViewById(R.id.shareTwitt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://twitter.com/intent/tweet?text=Check%20This%20Out!%20";
                url += "http://www.themoviedb.org/" + type + "/" + id + "%2F&amp;src=sdkpreparse";
                Uri uri = Uri.parse(url);
                Log.e("MSG", url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                startActivity(intent);
            }
        });
    }
    public void openDetailsActivity(String id, String type){
        Intent intent = new Intent(DetailsActivity.this, DetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        startActivity(intent);
    }

}
