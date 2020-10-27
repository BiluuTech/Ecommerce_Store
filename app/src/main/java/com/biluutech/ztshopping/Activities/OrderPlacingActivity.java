package com.biluutech.ztshopping.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.biluutech.ztshopping.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderPlacingActivity extends AppCompatActivity {

    private TextView totalPriceTV;
    private String totalPrice;

    private Toolbar toolbar;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placing);


        totalPrice = getIntent().getStringExtra("totalprice");

        totalPriceTV = findViewById(R.id.total_price_TV);

        totalPriceTV.setText("Rs " + totalPrice +"/-");

        toolbar = findViewById(R.id.receiptToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }
}