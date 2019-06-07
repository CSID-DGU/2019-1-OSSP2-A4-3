package com.amitshekhar.tflite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraActivity  extends AppCompatActivity {

    private static final String MODEL_PATH = "mobilenet_quant_v1_224.tflite";
    private static final boolean QUANT = true;
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 299;

    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();
    private Button btnDetectObject, btnToggleCamera, btnSelectAlbum;
    private Button resultButton1,resultButton2,resultButton3;
    private ImageView imageViewResult;
    private CameraView cameraView;

    private Bitmap img;
    private boolean isFromAlbum = false;

    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraView = findViewById(R.id.cameraView);
        imageViewResult = findViewById(R.id.imageViewResult);


        final Intent result_button_click = new Intent(this, CameraActivity.class);
        btnToggleCamera = findViewById(R.id.btnToggleCamera);
        btnDetectObject = findViewById(R.id.btnDetectObject);
        btnSelectAlbum = findViewById(R.id.btnSelectAlbum);
        resultButton1 = findViewById(R.id.result1);
        resultButton2 = findViewById(R.id.result2);
        resultButton3 = findViewById(R.id.result3);
        toolbar = findViewById(R.id.toolbarDetection);
        ActionToolBar();
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
                resultButton1.setText(words[1]+ " " + words[2]);
                resultButton2.setText(words[5]+ " " + words[6]);
                resultButton3.setText(words[9]+ " " + words[10]);


                resultButton1.setVisibility(View.VISIBLE);
                resultButton2.setVisibility(View.VISIBLE);
                resultButton3.setVisibility(View.VISIBLE);

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });


        //result버튼 눌럿을 때 이동
        resultButton1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(result_button_click);
            }
        });
        resultButton2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(result_button_click);
            }
        });
        resultButton3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(result_button_click);
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
                cameraView.captureImage();
            }
        });

        initTensorFlowAndLoadModel();
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
