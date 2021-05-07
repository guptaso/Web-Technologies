package com.example.hw9;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    private final List<SliderData> items;

    public SliderAdapter( ArrayList<SliderData> list) {
        this.items = list;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {
        final SliderData sliderItem = items.get(position);

        // for blurred background
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgURL())
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(new ColorDrawable(Color.TRANSPARENT))
                .transform(new BlurTransformation(25, 3))
                .into(viewHolder.bImg);

        // for regular image
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgURL())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.bImg.setOnClickListener((view) -> {
            Intent intent = new Intent(viewHolder.bImg.getContext(), DetailsActivity.class);
            intent.putExtra("id", sliderItem.getId());
            intent.putExtra("type", sliderItem.getType());
            viewHolder.bImg.getContext().startActivity(intent);
        });
    }

    @Override
    public int getCount() {
        return items.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;
        ImageView bImg;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.sliderImg);
            bImg = itemView.findViewById(R.id.bckgrdSliderImg);
            this.itemView = itemView;
        }
    }
}
