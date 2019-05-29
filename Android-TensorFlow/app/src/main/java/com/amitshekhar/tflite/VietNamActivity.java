package com.amitshekhar.tflite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


import com.amitshekhar.tflite.Interface.VietNamFoodAdapter;
import com.amitshekhar.tflite.Model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VietNamActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    ListView listView;
    VietNamFoodAdapter vietNamFoodAdapter;
    ArrayList<Food> vietNamFoodList;
    int iDcoutry_food;
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viet_nam);
        Init();
        GetIdFood();
        ActionToolBar();
        GetDataFood();
    }
    DatabaseReference vData;
    private void GetDataFood() {
        vData = FirebaseDatabase.getInstance().getReference();
        vData.child("Food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot vietnamFood : dataSnapshot.getChildren())
                {
                    Food foodVietnamValue = vietnamFood.getValue(Food.class);
                    if(foodVietnamValue.getCountryID().equals(String.valueOf(iDcoutry_food))) {
                        vietNamFoodList.add(foodVietnamValue);
                        vietNamFoodAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void GetIdFood() {
        iDcoutry_food = getIntent().getIntExtra("idCountries",-1);
    }
    private void Init() {
        toolbar =(android.support.v7.widget.Toolbar) findViewById(R.id.tbvietnamFood);
        listView =(ListView) findViewById(R.id.listVietnamFood);
        vietNamFoodList = new ArrayList<>();
        vietNamFoodAdapter = new VietNamFoodAdapter(getApplicationContext(),vietNamFoodList);
        listView.setAdapter(vietNamFoodAdapter);

    }
}
