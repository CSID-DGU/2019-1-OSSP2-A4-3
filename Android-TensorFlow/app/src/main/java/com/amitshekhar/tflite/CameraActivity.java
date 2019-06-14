package com.amitshekhar.tflite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amitshekhar.tflite.Interface.SpecialFoodAdapter;
import com.amitshekhar.tflite.Model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraActivity  extends AppCompatActivity {

    private static final String MODEL_PATH = "mobilenet_quant_v1_224.tflite";
    private static final boolean QUANT = false;
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 299;

    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();
    private Button btnDetectObject, btnToggleCamera, btnSelectAlbum;
    private ImageView imageViewResult;
    private CameraView cameraView;
    private RecyclerView recyclerViewResult;

    private Bitmap img;
    private boolean isFromAlbum = false;

    android.support.v7.widget.Toolbar toolbar;
    private static final String TAG = "CameraActivity";

    String [] result = {" "," "," "};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraView = findViewById(R.id.cameraView);
        imageViewResult = findViewById(R.id.imageViewResult);
        Init();
        ActionToolBar();
        getDataForDectiontionResult();
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bitmap;

                if(isFromAlbum == false) {
                    bitmap = cameraKitImage.getBitmap();
                }
                else{
                    bitmap = img;
                }

                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

                imageViewResult.setImageBitmap(bitmap);

                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);

                System.out.println("Result " + results);
                String[] words = results.toString().split("\\s");

                for (String wo : words ){
                    System.out.println("Result" + wo);
                }

                if(words.length < 3)
                {
                    //resultButton1.setText("인식 가능한 결과가 없습니다");
                    //resultButton1.setVisibility(View.VISIBLE);
                }
                else if(words.length < 6)
                {
                    result[0] = words[1];
                }
                else if(words.length < 9)
                {
                    result[0] = words[1];
                    result[1] = words[4];
                }
                else if(words.length >= 9 )
                {
                    result[0] = words[1];
                    result[1] = words[4];
                    result[2] = words[7];
                }
            }
            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {
            }
        });
        btnSelectAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_album = new Intent(Intent.ACTION_GET_CONTENT);
                intent_album.setType("image/*");
                intent_album.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent_album, 1);
            }
        });

        btnToggleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.toggleFacing();
            }
        });
        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Camera Click");
                cameraView.captureImage();
            }
        });
        initTensorFlowAndLoadModel();
    }

    ArrayList<Food> listResultFood;
    SpecialFoodAdapter resultFoodAdapter;
    private void Init()
    {
        btnToggleCamera = findViewById(R.id.btnToggleCamera);
        btnDetectObject = findViewById(R.id.btnDetectObject);
        btnSelectAlbum = findViewById(R.id.btnSelectAlbum);
        toolbar = findViewById(R.id.toolbarDetection);
        recyclerViewResult = findViewById(R.id.recyclerviewResult);

        listResultFood = new ArrayList<>();
        resultFoodAdapter = new SpecialFoodAdapter(listResultFood,getApplicationContext());
        recyclerViewResult.setHasFixedSize(true);
        recyclerViewResult.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewResult.setAdapter(resultFoodAdapter);

        resultFoodAdapter.setOnItemClickListener(new SpecialFoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion) {
                final Food food = listResultFood.get(postion);
                Intent intent = new Intent(CameraActivity.this,InfoFood.class);
                intent.putExtra("foodInfo", food);
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
    DatabaseReference mData;
    void getDataForDectiontionResult()
    {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "ChildEventListener");
                for(DataSnapshot specialfoodData : dataSnapshot.getChildren()) {
                    Food specialFood = specialfoodData.getValue(Food.class);
                    if(specialFood.getName().equals(result[0]))
                         listResultFood.add(0,specialFood);
                    if(specialFood.getName().equals(result[1]))
                        listResultFood.add(1,specialFood);
                    if(specialFood.getName().equals(result[2]))
                        listResultFood.add(2,specialFood);
                    resultFoodAdapter.notifyDataSetChanged();
                }
                if(listResultFood.size() == 0)
                {
                    //인식이 안 되는 경우에는
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
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
                    //camera activity로 넘어감


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE,
                            QUANT);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }
    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }
}