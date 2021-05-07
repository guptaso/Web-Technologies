package com.example.hw9;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

public class recyclerViewAdapter4 extends RecyclerView.Adapter<recyclerViewAdapter4.MovieDataViewHolder> {

    private Context mContext;
    private List<movieData> movieDataList;

    recyclerViewAdapter4(Context mContext, List <movieData> movieDataList){
        this.mContext = mContext;
        this.movieDataList = movieDataList;
    }

    @NonNull
    @Override
    public MovieDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid, parent, false);
        return new MovieDataViewHolder(view);
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDataViewHolder holder, int position) {
        Glide.with(holder.myView)
                .load(movieDataList.get(position).getImg())
                .fitCenter()
                .transform(new RoundedCorners(70))
                .into(holder.img);
        holder.txt.setText(movieDataList.get(position).getName());
        holder.remove.setOnClickListener((view) -> {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("watchlist", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(movieDataList.get(position).getId());
            editor.commit();
            Toast.makeText(mContext, movieDataList.get(position).getName() +" was removed from Watchlist", Toast.LENGTH_LONG).show();
        });
        holder.img.setOnClickListener((view) -> {
            Intent intent = new Intent(holder.img.getContext(), DetailsActivity.class);
            intent.putExtra("id", movieDataList.get(position).getId());
            intent.putExtra("type", movieDataList.get(position).getName());
            holder.img.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return movieDataList.size();
    }

    class MovieDataViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txt;
        View myView;
        TextView remove;

        MovieDataViewHolder(View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.cvImg);
            txt = itemView.findViewById(R.id.textView);
            myView = itemView;
            remove = itemView.findViewById(R.id.removeBtn);
        }
    }
}
