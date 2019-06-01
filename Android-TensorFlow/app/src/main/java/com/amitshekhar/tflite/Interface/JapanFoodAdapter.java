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

public class JapanFoodAdapter  extends BaseAdapter {

    Context context;
    ArrayList<Food> foodJapanList;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public ArrayList<Food> getFoodJapanList() {
        return foodJapanList;
    }

    public void setFoodJapanList(ArrayList<Food> foodJapanList) {
        this.foodJapanList = foodJapanList;
    }

    private static final String TAG = "Japan";
    public JapanFoodAdapter(Context context, ArrayList<Food> foodJapanList) {
        this.context = context;
        this.foodJapanList = foodJapanList;
    }
    @Override
    public int getCount() {
        return foodJapanList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodJapanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolderJP{
        public TextView foodName,foodCountry,foodDescrip;
        public ImageView foodImage;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderJP viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolderJP();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_food,null);
            viewHolder.foodName = view.findViewById(R.id.foodName);
            viewHolder.foodCountry = view.findViewById(R.id.foodCountry);
            viewHolder.foodDescrip = view.findViewById(R.id.foodDescrip);
            viewHolder.foodImage = view.findViewById(R.id.foodImage);
            view.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolderJP) view.getTag();
        }
        Food foodJapan = (Food) getItem(i);
        viewHolder.foodName.setText(foodJapan.getName());
        viewHolder.foodCountry.setText(foodJapan.getCountry());
        viewHolder.foodName.setText(foodJapan.getName());
        viewHolder.foodDescrip.setMaxLines(2);
        viewHolder.foodDescrip.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.foodDescrip.setText(foodJapan.getDescription());
        Log.d(TAG,"Japanfood Adapter" +  foodJapan.getImage());
        Picasso.with(context).load(foodJapan.getImage()).error(R.drawable.newimage).into(viewHolder.foodImage);
        return view;
    }
}
