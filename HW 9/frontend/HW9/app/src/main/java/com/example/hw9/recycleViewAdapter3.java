package com.example.hw9;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class recycleViewAdapter3 extends RecyclerView.Adapter<recycleViewAdapter3.MovieDataViewHolder> {

    private Context mContext;
    private List<reviewData> movieDataList;

    recycleViewAdapter3(Context mContext,List <reviewData> movieDataList){
        this.mContext = mContext;
        this.movieDataList = movieDataList;
    }

    @NonNull
    @Override
    public MovieDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout, parent, false);
        return new MovieDataViewHolder(view);
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDataViewHolder holder, int position) {
        String x = "by " + movieDataList.get(position).getUsername() + " on " + movieDataList.get(position).getCreation();
        holder.txt.setText(x);
        holder.txt2.setText(movieDataList.get(position).getRating() + "/5");
        holder.txt3.setText(movieDataList.get(position).getContent());

        holder.cardView.setOnClickListener((view) -> {
            Intent intent = new Intent(holder.cardView.getContext(), reviewActivity.class);
            intent.putExtra("txt1", movieDataList.get(position).getRating() + "/5");
            intent.putExtra("txt2", x);
            intent.putExtra("txt3", movieDataList.get(position).getContent());
            holder.cardView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieDataList.size();
    }

    class MovieDataViewHolder extends RecyclerView.ViewHolder{
        TextView txt;
        TextView txt2;
        TextView txt3;
        CardView cardView;
        View myView;
        Context context;

        MovieDataViewHolder(View itemView){
            super(itemView);

            txt = itemView.findViewById(R.id.reviewText1);
            txt2 = itemView.findViewById(R.id.reviewText2);
            txt3 = itemView.findViewById(R.id.reviewText3);

            cardView = itemView.findViewById(R.id.cardView);

            myView = itemView;


        }
    }
}
