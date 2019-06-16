package com.amitshekhar.tflite.Interface;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amitshekhar.tflite.Model.Food;
import com.amitshekhar.tflite.Model.ImageUser;
import com.amitshekhar.tflite.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ItemHolder> {

    Context context;
    ArrayList<ImageUser> saveFoodList;

    public AccountAdapter() {
    }

    public AccountAdapter(Context context, ArrayList<ImageUser> saveFoodList) {
        Log.d("TAG", "Account Adapte");
        this.context = context;
        this.saveFoodList = saveFoodList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<ImageUser> getSaveFoodList() {
        return saveFoodList;
    }

    public void setSaveFoodList(ArrayList<ImageUser> saveFoodList) {
        this.saveFoodList = saveFoodList;
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View _view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_account_food,null);
        ItemHolder itemHolder = new ItemHolder(_view,mListener);
        return itemHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ImageUser savedFood = saveFoodList.get(position);
        holder.txtFood_name.setText(savedFood.getNameFood());
        Picasso.with(context).load(savedFood.getLinkImage()).into(holder.imgFood);
        Log.d("TAG", "Account Adapter = " +savedFood.getNameFood()+ savedFood.getLinkImage());
    }
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int postion);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    @Override
    public int getItemCount() {
        return saveFoodList.size();
    }
    public class ItemHolder extends RecyclerView.ViewHolder
    {
        public ImageView imgFood;
        public TextView txtFood_name;
        public ImageView getImgFood() {
            return imgFood;
        }
        public void setImgFood(ImageView imgFood) {
            this.imgFood = imgFood;
        }
        public TextView getTxtFood_name() {
            return txtFood_name;
        }
        public void setTxtFood_name(TextView txtFood_name) {
            this.txtFood_name = txtFood_name;
        }


        public ItemHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imgFood = (ImageView) itemView.findViewById(R.id.accountSaveImage);
            txtFood_name = (TextView) itemView.findViewById(R.id.saveFoodName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener !=  null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
