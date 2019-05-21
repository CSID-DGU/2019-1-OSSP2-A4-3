package com.amitshekhar.tflite;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    public Bitmap img;
    public boolean isFromAlbum = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button take_picture = (Button) findViewById(R.id.take_picture);
        take_picture.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_camera = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent_camera);
            }
        });
        //todo : recipe 선택
        //아직 어떻게 할지 모르겠음

        //todo : 앨범에서 사진 선택
        Button openimg = (Button) findViewById(R.id.select_album);
        openimg.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Intent intent_album = new Intent(Intent.ACTION_GET_CONTENT);
                intent_album.setType("image/*");
                intent_album.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent_album, 1);
            }
        });
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

