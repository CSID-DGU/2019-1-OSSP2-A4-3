package com.amitshekhar.tflite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amitshekhar.tflite.Interface.AccountAdapter;
import com.amitshekhar.tflite.Model.Account;
import com.amitshekhar.tflite.Model.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {

    ImageView accountImage;
    TextView accountName;
    TextView accountEmail;
    RecyclerView recyclerViewSaveImage;
    Button signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Init();
    }
    ArrayList<Food> savedFood;
    AccountAdapter accountAdapter;
    Account account;
    void Init() {
        GetAccountInfo();
        accountName = findViewById(R.id.accountName);
        accountEmail = findViewById(R.id.accountGmail);
        accountImage = findViewById(R.id.accountPhoto);
        recyclerViewSaveImage = findViewById(R.id.recyclerviewAccount);
        signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        account = new Account();
        savedFood = account.getListSavePhoto();
        accountAdapter = new AccountAdapter(getApplicationContext(),savedFood);
        recyclerViewSaveImage.setHasFixedSize(true);
        recyclerViewSaveImage.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        recyclerViewSaveImage.setAdapter(accountAdapter);
        accountAdapter.setOnItemClickListener(new AccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion) {
                Intent intent = new Intent(AccountActivity.this,InfoFood.class);
                intent.putExtra("foodInfo", savedFood.get(postion));
                startActivity(intent);
            }
        });
        accountName.setText(account.getName());
        accountEmail.setText(account.getEmail());
        Picasso.with(AccountActivity.this).load(account.getPhoto()).into(accountImage);
    }
    private void GetAccountInfo() {
        account =  (Account) getIntent().getSerializableExtra("accountInfo");
    }
}
