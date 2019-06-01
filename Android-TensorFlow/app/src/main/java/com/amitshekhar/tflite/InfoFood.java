package com.amitshekhar.tflite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.amitshekhar.tflite.Model.Food;
import com.squareup.picasso.Picasso;

public class InfoFood extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbarFood;
    ImageView imageView;
    TextView txtfoodName;
    TextView txtfoodCountry;
    TextView txtDescription;
    TextView txtMake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        Init();
        ActionToolbar();
        GetInformation();
    }
    private void GetInformation() {
        Food food = (Food) getIntent().getSerializableExtra("foodInfo");
        String foodName = food.getName();
        String foodCountry = food.getCountry();
        String foodDescription = food.getDescription();
        String foodMake = food.getYoutubeLink();
        String foodImage = food.getImage();

        txtfoodName.setText(foodName);
        txtfoodCountry.setText(foodCountry);
        txtDescription.setText(foodDescription);
        Picasso.with(getApplicationContext()).load(foodImage).into(imageView);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarFood);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarFood.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void Init() {
        toolbarFood = findViewById(R.id.foodinfo);
        imageView = findViewById(R.id.infoFoodImage);
        txtfoodName = findViewById(R.id.infoFoodName);
        txtfoodCountry = findViewById(R.id.infoFoodCountry);
        txtDescription = findViewById(R.id.infoFoodDescription);
        txtMake = findViewById(R.id.infoFoodMake);
    }
}
