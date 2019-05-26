package com.amitshekhar.tflite.Interface;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amitshekhar.tflite.Model.FoodOfCountry;
import com.amitshekhar.tflite.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class CountryAdapter extends BaseAdapter {
    ArrayList<FoodOfCountry> arrayListCountry = new ArrayList<>();
    Context context;
    private static final String TAG = "CountryAdapter";
    public CountryAdapter(ArrayList<FoodOfCountry> arrayListCountry, Context context) {
        this.arrayListCountry = arrayListCountry;
        this.context = context;
    }
    public ArrayList<FoodOfCountry> getArrayListCountry() {
        return arrayListCountry;
    }
    public void setArrayListCountry(ArrayList<FoodOfCountry> arrayListCountry) {
        this.arrayListCountry = arrayListCountry;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return arrayListCountry.size();
    }
    @Override
    public Object getItem(int i) {
        return arrayListCountry.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtCountry_name;
        ImageView imgCountry_image;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_country,null);
            viewHolder.txtCountry_name = view.findViewById(R.id.textViewCountry);
            viewHolder.imgCountry_image = view.findViewById(R.id.imageViewCountry);
            view.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        FoodOfCountry foodOfCountry = (FoodOfCountry) getItem(i);
        viewHolder.txtCountry_name.setText(foodOfCountry.getName());
        Log.d(TAG, foodOfCountry.getImage());
        Picasso.with(context).load(foodOfCountry.getImage()).into(viewHolder.imgCountry_image);
        return view;
    }
}
