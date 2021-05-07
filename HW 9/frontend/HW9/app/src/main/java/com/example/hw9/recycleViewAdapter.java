package com.example.hw9;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.MovieDataViewHolder> {

    private Context mContext;
    private List<movieData> movieDataList;

    recycleViewAdapter(Context mContext,List <movieData> movieDataList){
        this.mContext = mContext;
        this.movieDataList = movieDataList;
    }

    @NonNull
    @Override
    public MovieDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, parent, false);
        return new MovieDataViewHolder(view);
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDataViewHolder holder, int position) {
        Glide.with(holder.myView)
                .load(movieDataList.get(position).getImg())
                .fitCenter()
                .into(holder.img);
        holder.txt.setText(movieDataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return movieDataList.size();
    }

    class MovieDataViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txt;
        View myView;

        MovieDataViewHolder(View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.cvImg);
            txt = itemView.findViewById(R.id.textView);
            myView = itemView;
        }
    }
}
