package com.amitshekhar.tflite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.IDNA;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SearchEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import java.text.SimpleDateFormat;

import com.amitshekhar.tflite.Model.Food;
import com.amitshekhar.tflite.Model.ImageUser;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InfoFood extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbarFood;
    ImageView imageView;
    TextView txtfoodName;
    TextView txtfoodCountry;
    TextView txtDescription;
    TextView txtMake;
    Button button;
    Button btSearch;
    Button btSave;


    private final static int mWidth = 512;
    private final static int mLength = 512;
    private StorageReference mStorageRef;
    private FirebaseAuth auth;
    String TAG = "Info";
    Food searchFoodWithName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        auth = FirebaseAuth.getInstance();

        Init();
        ActionToolbar();
        GetInformation();
    }

    String foodName = "";
    String foodImage = "";
    String userID;

    private void GetInformation() {

        Food food = (Food) getIntent().getSerializableExtra("foodInfo");
        foodName = food.getName();
        String foodCountry = food.getCountry();
        String foodDescription = food.getDescription();
        final String foodMake = food.getYoutubeLink();
        foodImage = food.getImage();

        txtfoodName.setText(foodName);
        txtfoodCountry.setText(foodCountry);
        txtDescription.setText(foodDescription);
        Picasso.with(getApplicationContext()).load(foodImage).into(imageView);
        btSave = (Button) findViewById(R.id.infoFoodSave);
        button=(Button)findViewById(R.id.youtubeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoFood.this,YoutubeView.class);
                intent.putExtra("YoutubeLink", foodMake);
                startActivity(intent);
            }
        });
        btSearch = (Button) findViewById(R.id.infoFoodSearch);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapActivity mapActivity = new MapActivity();
                Intent intent = new Intent(InfoFood.this,mapActivity.getClass());
                intent.putExtra("SearchKey",foodName);
                Log.d(TAG, "Send" + foodName);
                startActivity(intent);
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the signed in user
                FirebaseUser user = auth.getCurrentUser();
                userID = user.getUid();
                ImageUser imageUser = new ImageUser(foodImage,foodName);
                Log.d(TAG, "onClick: Uploading Image."  + userID);
                if(userID != null){
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Log.d(TAG, "onClick: Uploading Image."  + userID + formatter.format(currentTime));
                    String CurrentTime= formatter.format(currentTime).replaceAll("/","");
                    //CurrentTime = CurrentTime.replaceAll(":","");
                    databaseReference.child(CurrentTime).setValue(imageUser);
                    Toast.makeText(getApplicationContext(),"Image Saved",Toast.LENGTH_SHORT).show();
                }else
                {
                    QuesLogin();
                }
            }
        });
    }
    private void QuesLogin()
    {
        AlertDialog.Builder alb = new AlertDialog.Builder(this);
        alb.setTitle("알림!");
        alb.setIcon(R.mipmap.ic_launcher);
        alb.setMessage("이 기능을 하기 위해서 먼저 로그인을 해주십시오! 로그인 화면을 이동합니까?");
        alb.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(InfoFood.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        alb.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alb.show();
    }
    private DatabaseReference databaseReference;
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
