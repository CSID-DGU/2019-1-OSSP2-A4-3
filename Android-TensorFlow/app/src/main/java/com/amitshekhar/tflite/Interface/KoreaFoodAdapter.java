package com.amitshekhar.tflite.Interface;

import android.content.Context;
import android.content.Intent;
import android.sax.TextElementListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amitshekhar.tflite.Model.Food;
import com.amitshekhar.tflite.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class KoreaFoodAdapter  extends BaseAdapter {
    Context context;
    ArrayList<Food> foodArrayList;
    private static final String TAG = "Korea";
    public KoreaFoodAdapter(Context context, ArrayList<Food> foodArrayList) {
        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Food> getFoodArrayList() {
        return foodArrayList;
    }

    public void setFoodArrayList(ArrayList<Food> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }

    @Override
    public int getCount() {
        return foodArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolderKorea{
        public TextView foodName,foodCountry,foodDescrip;
        public ImageView foodImage;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderKorea viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolderKorea();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_food,null);
            viewHolder.foodName = view.findViewById(R.id.foodName);
            viewHolder.foodCountry = view.findViewById(R.id.foodCountry);
            viewHolder.foodDescrip = view.findViewById(R.id.foodDescrip);
            viewHolder.foodImage = view.findViewById(R.id.foodImage);
            view.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolderKorea) view.getTag();
        }
        Food foodKorea = (Food) getItem(i);
        viewHolder.foodName.setText(foodKorea.getName());
        viewHolder.foodCountry.setText(foodKorea.getCountry());
        viewHolder.foodName.setText(foodKorea.getName());
        viewHolder.foodDescrip.setMaxLines(2);
        viewHolder.foodDescrip.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.foodDescrip.setText(foodKorea.getDescription());
        Picasso.with(context).load(foodKorea.getImage()).into(viewHolder.foodImage);
        return view;
    }
}
