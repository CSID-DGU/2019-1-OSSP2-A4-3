package com.amitshekhar.tflite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

import com.amitshekhar.tflite.Interface.KoreaFoodAdapter;
import com.amitshekhar.tflite.Model.Food;
import com.amitshekhar.tflite.Model.FoodOfCountry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KoreaActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    ListView listView;
    KoreaFoodAdapter koreaFoodAdapter;
    ArrayList<Food> koreaFoodList;
    int iDcoutry_food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korea);
        Init();
        GetIdFood();
        ActionToolBar();
        GetDataFood();
    }
    DatabaseReference mData;
    private void GetDataFood() {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot koreaFood : dataSnapshot.getChildren())
                {
                    Food foodOfKorea = koreaFood.getValue(Food.class);
                    if(foodOfKorea.getCountryID().equals(String.valueOf(iDcoutry_food))) {
                        koreaFoodList.add(foodOfKorea);
                        koreaFoodAdapter.notifyDataSetChanged();
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
        toolbar =(android.support.v7.widget.Toolbar) findViewById(R.id.tbkoreaFood);
        listView = findViewById(R.id.listKoreaFood);
        koreaFoodList = new ArrayList<>();
        koreaFoodAdapter = new KoreaFoodAdapter(getApplicationContext(),koreaFoodList);
        listView.setAdapter(koreaFoodAdapter);
    }
}
