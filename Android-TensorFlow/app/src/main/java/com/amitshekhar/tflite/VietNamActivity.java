package com.amitshekhar.tflite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    String foodCountry = "";
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viet_nam);
        Init();
        GetFoodCountry();
        ActionToolBar();
        GetDataFood(page);
        ClickFood();
    }
    DatabaseReference vData;
    private void GetDataFood(int page) {
        vData = FirebaseDatabase.getInstance().getReference();
        vData.child("Food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i  =  0;
                for(DataSnapshot vietnamFood : dataSnapshot.getChildren())
                {
                    Food foodVietnamValue = vietnamFood.getValue(Food.class);
                    Log.d(TAG,"Country = "+ (i++) +  foodCountry + "Class Country = " + foodVietnamValue.getCountry());
                    if(foodVietnamValue.getCountry().equals(foodCountry)) {
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
    private void ClickFood(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(VietNamActivity.this,InfoFood.class);
                intent.putExtra("foodInfo", vietNamFoodList.get(i));
                startActivity(intent);
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
    private static final String TAG = "VietnamActivity";
    private void GetFoodCountry() {
        Bundle extras = getIntent().getExtras();
        foodCountry= extras.getString("Country");
    }
    private void Init() {
        toolbar =(android.support.v7.widget.Toolbar) findViewById(R.id.tbvietnamFood);
        listView =(ListView) findViewById(R.id.listVietnamFood);
        vietNamFoodList = new ArrayList<>();
        vietNamFoodAdapter = new VietNamFoodAdapter(getApplicationContext(),vietNamFoodList);
        listView.setAdapter(vietNamFoodAdapter);

    }
}
