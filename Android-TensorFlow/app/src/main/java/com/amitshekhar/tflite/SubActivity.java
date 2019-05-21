package com.amitshekhar.tflite;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class SubActivity extends AppCompatActivity {
    private static final String TAG = "SubActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Button button1 = (Button) findViewById(R.id.find_restaurant) ;
        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Find Restaurant Click");
                MapActivity mapActivity = new MapActivity();
                Intent intent = new Intent(SubActivity.this,mapActivity.getClass());
                startActivity(intent);
            }
        });
    }
}
