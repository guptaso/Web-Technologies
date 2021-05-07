package com.example.hw9;

import android.content.Context;
import android.content.Intent;
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

public class searchRecyclerAdapter extends RecyclerView.Adapter<searchRecyclerAdapter.searchAdapterViewHolder> {
    private List<searchCard> searchCardList;


    @NonNull
    @Override
    public searchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.searchlist, parent,false);

        return new searchAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchAdapterViewHolder holder, int position) {
        //holder.bind(allRepo[position]);
        holder.txt_year.setText(searchCardList.get(position).getYear());
        holder.txt_type.setText(searchCardList.get(position).getType());
        holder.txt_title.setText(searchCardList.get(position).getTitle());
        holder.txt_rating.setText(searchCardList.get(position).getRating());
        Glide.with(holder.myView)
                .load(searchCardList.get(position).getImg())
                .fitCenter()
                .transform(new RoundedCorners(130))
                .into(holder.img);

        holder.img.setOnClickListener((view) -> {
            Intent intent = new Intent(holder.img.getContext(), DetailsActivity.class);
            intent.putExtra("id", searchCardList.get(position).getId());
            intent.putExtra("type", searchCardList.get(position).getType());
            holder.img.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (searchCardList == null){
            return 0;
        }
        return searchCardList.size();
    }

    public void setSearchCardList(List<searchCard> searchCards){
        searchCardList = searchCards;
        notifyDataSetChanged();
    }

    public class searchAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        TextView txt_type;
        TextView txt_rating;
        TextView txt_year;
        ImageView img;
        View myView;


        searchAdapterViewHolder(View itemView){
            super(itemView);

            txt_rating = itemView.findViewById(R.id.ratingView);
            txt_title = itemView.findViewById(R.id.titleView);
            txt_type = itemView.findViewById(R.id.typeView);
            txt_year = itemView.findViewById(R.id.yearView);

            img = itemView.findViewById(R.id.cvImg);

            myView = itemView;
        }
    }
}
