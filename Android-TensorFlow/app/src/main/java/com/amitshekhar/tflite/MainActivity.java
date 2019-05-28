package com.amitshekhar.tflite;


import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ViewFlipper;

import com.amitshekhar.tflite.Interface.CountryAdapter;
import com.amitshekhar.tflite.Interface.CountryAdapter;
import com.amitshekhar.tflite.Interface.SpecialFoodAdapter;
import com.amitshekhar.tflite.Model.FoodOfCountry;
import com.amitshekhar.tflite.Model.SpecialFood;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestCreator;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    public Bitmap img;
    public boolean isFromAlbum = false;

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMain;
    NavigationView navigationView;
    ListView listViewMain;
    DrawerLayout drawerLayout;


    ArrayList<FoodOfCountry> listCountry;
    CountryAdapter countryAdapter;

    ArrayList<SpecialFood> listSpecialFood;
    SpecialFoodAdapter specialFoodAdapter;

    private static final String TAG = "MainActivity";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Init();

        if(CheckConnectionInt.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDataCountries();
            GetDataSpecialFood();
        }else
        {
            CheckConnectionInt.ShowToast(getApplicationContext(),"Check Internet connection please");
            finish();
        };

//        Button take_picture = (Button) findViewById(R.id.take_picture);
//        take_picture.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent_camera = new Intent(MainActivity.this, CameraActivity.class);
//                startActivity(intent_camera);
//            }
//        });
//        //todo : recipe 선택
//        //아직 어떻게 할지 모르겠음
//
//        //todo : 앨범에서 사진 선택
//        Button openimg = (Button) findViewById(R.id.select_album);
//        openimg.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent_album = new Intent(Intent.ACTION_GET_CONTENT);
//                intent_album.setType("image/*");
//                intent_album.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent_album, 1);
//            }
//        });
    }
    private void ActionViewFlipper(){
        ArrayList<String> advertiseList = new ArrayList<>();
        advertiseList.add("https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2012/10/Food.jpg");
        advertiseList.add("https://img.huffingtonpost.com/asset/585be1aa1600002400bdf2a6.jpeg?ops=scalefit_970_noupscale");
        advertiseList.add("https://gialaianngon.com/wp-content/uploads/2019/01/21.jpg");
        for(int i  = 0; i <  advertiseList.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(advertiseList.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_main_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_main_out);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
        Log.d(TAG, "Animation OK");
    }
    DatabaseReference mData;
    private void GetDataCountries()
    {
        Log.d(TAG, "GetDataCountries Go");
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Country").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "ChildEventListener");
                for(DataSnapshot country : dataSnapshot.getChildren())
                {
                    FoodOfCountry foodOfCountry = country.getValue(FoodOfCountry.class);
                    listCountry.add(foodOfCountry);
                    countryAdapter.notifyDataSetChanged();
                }
                listCountry.add(4,new FoodOfCountry("Detection","http://www.iconarchive.com/download/i99778/designbolts/free-multimedia/Dslr-Camera.ico"));
                listCountry.add(5,new FoodOfCountry("Contact","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3pbMGP9FJgbUnadSQunt7_7l6HmZ0VvkGylCLJlHgwtDxqHxG"));
                listCountry.add(6,new FoodOfCountry("Information","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGJxCs8wi1t7soPzjYVEs7-HMcf79G5-0l2Kisywh8Mzr-eNfY"));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void GetDataSpecialFood()
    {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("SpecialFood").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "ChildEventListener");
                for(DataSnapshot specialfoodData : dataSnapshot.getChildren()) {
                    SpecialFood specialFood = specialfoodData.getValue(SpecialFood.class);
                    listSpecialFood.add(specialFood);
                    specialFoodAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void ActionBar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void Init()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbarmain);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewlipperId);
        recyclerViewMain = (RecyclerView)findViewById(R.id.recyclerview);
        navigationView = (NavigationView) findViewById(R.id.navigationMain);
        listViewMain = (ListView) findViewById(R.id.listviewMain);
        drawerLayout =(DrawerLayout) findViewById(R.id.drawerlayout);

        listCountry = new ArrayList<>();
        listCountry.add(0,new FoodOfCountry("Main","http://chittagongit.com/download/153163"));
        countryAdapter = new CountryAdapter(listCountry,getApplicationContext());
        listViewMain.setAdapter(countryAdapter);

        listSpecialFood = new ArrayList<>();
        specialFoodAdapter = new SpecialFoodAdapter(listSpecialFood,getApplicationContext());
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewMain.setAdapter(specialFoodAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    img = BitmapFactory.decodeStream(in);
                    in.close();

                    isFromAlbum = true;
                    context = this;
                    //camera activity로 넘어감
                    Intent intent_camera = new Intent(MainActivity.this, CameraActivity.class);
                    startActivity(intent_camera);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

