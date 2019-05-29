package com.amitshekhar.tflite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.amitshekhar.tflite.Interface.JapanFoodAdapter;
import com.amitshekhar.tflite.Model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JapanActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    ListView listView;
    JapanFoodAdapter japanFoodAdapter;
    ArrayList<Food> japanFoodList;
    int iDcoutry_food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_japan);
        Init();
        GetIdFood();
        ActionToolBar();
        GetDataFood();
    }
    DatabaseReference mData;
    private static final String TAG = "JapanActivity";
    private void GetDataFood() {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot japanFood : dataSnapshot.getChildren())
                {
                    Food foodOfJapan = japanFood.getValue(Food.class);
                    if(foodOfJapan.getCountryID().equals(String.valueOf(iDcoutry_food))) {
                        japanFoodList.add(foodOfJapan);
                        japanFoodAdapter.notifyDataSetChanged();
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
        toolbar =(android.support.v7.widget.Toolbar) findViewById(R.id.tbjapanFood);
        listView = (ListView) findViewById(R.id.listJapanFood);
        japanFoodList = new ArrayList<>();
        japanFoodAdapter = new JapanFoodAdapter(getApplicationContext(),japanFoodList);
        listView.setAdapter(japanFoodAdapter);
    }
}
