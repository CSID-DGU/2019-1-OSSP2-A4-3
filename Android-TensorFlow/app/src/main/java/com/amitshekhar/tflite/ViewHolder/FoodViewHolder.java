package com.amitshekhar.tflite.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView food_name;
    public ImageView food_image;

    private AdapterView.OnItemClickListener itemClickLister;

    public FoodViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View view) {

    }
}
