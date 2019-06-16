package com.amitshekhar.tflite;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.amitshekhar.tflite.Interface.AccountAdapter;
import com.amitshekhar.tflite.Interface.SpecialFoodAdapter;
import com.amitshekhar.tflite.Model.Account;
import com.amitshekhar.tflite.Model.Food;
import com.amitshekhar.tflite.Model.ImageUser;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    ImageView accountImage;
    TextView accountName;
    TextView accountEmail;
    RecyclerView recyclerViewSaveImage;
    Button signOut;
    AccountAdapter accountAdapter;
    Account account;
    String userID;
    ArrayList<ImageUser> listSavedImage;
    GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        GetAccountInfo();
        Init();
        GetDataSavedImage();
        ActionToolBar();
    }
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
    void Init() {
        userID = account.getId();
        toolbar = findViewById(R.id.toolbarAccout);
        accountName = findViewById(R.id.accountName);
        accountEmail = findViewById(R.id.accountGmail);
        accountImage = findViewById(R.id.accountPhoto);
        recyclerViewSaveImage = findViewById(R.id.recyclerviewAccount);
        accountName.setText(account.getName());
        accountEmail.setText(account.getEmail());
        Picasso.with(AccountActivity.this).load(account.getPhoto()).into(accountImage);
        Log.d("TAG", "Response AccountcActivity = " + account.getEmail() + account.getName() + account.getPhoto());

        signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(i);
                            }
                        });
            }
        });
        listSavedImage = new ArrayList<>();
        accountAdapter = new AccountAdapter(getApplicationContext(),listSavedImage);
        recyclerViewSaveImage.setHasFixedSize(true);
        recyclerViewSaveImage.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewSaveImage.setAdapter(accountAdapter);
        accountAdapter.setOnItemClickListener(new AccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postion) {
                Log.d("TAG", "SavedImage click");
                ImageUser imageUserClick = listSavedImage.get(postion);
                final String nameFood = imageUserClick.getNameFood();
                SearchFoodwithName(nameFood);

            }
        });
    }
    private DatabaseReference databaseReference;
    private void GetDataSavedImage()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(userID)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ImageUser upload = postSnapshot.getValue(ImageUser.class);
                    listSavedImage.add(upload);
                    accountAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("AccountActivity", databaseError.toString());
            }
        });
    }
    private void SearchFoodwithName(String nameFood)
    {
        databaseReference  = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Food").orderByChild("Name").equalTo(nameFood);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Log.d("TAG","Find Sucessfful");
                    Food food = data.getValue(Food.class);
                    Intent intent = new Intent(AccountActivity.this,InfoFood.class);
                    intent.putExtra("foodInfo",food);
                    startActivity(intent);
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
    private void GetAccountInfo() {
        account =  (Account) getIntent().getSerializableExtra("loginAccount");
    }
}
