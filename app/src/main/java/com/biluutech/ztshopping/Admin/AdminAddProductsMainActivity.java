package com.biluutech.ztshopping.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.biluutech.ztshopping.R;

public class AdminAddProductsMainActivity extends AppCompatActivity {

    private CardView adminElectronicsCardview, adminMenCardview, adminWomenCardview, adminBabyCardview, adminToysCardview, adminGroceryCardview;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_products_main);

        toolbar = findViewById(R.id.adminAddProductsMainToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adminElectronicsCardview = findViewById(R.id.admin_electronics_cardview);
        adminMenCardview = findViewById(R.id.admin_men_cardview);
        adminWomenCardview = findViewById(R.id.admin_women_cardview);
        adminBabyCardview = findViewById(R.id.admin_baby_cardview);
        adminToysCardview = findViewById(R.id.admin_toys_cardview);
        adminGroceryCardview = findViewById(R.id.admin_grocery_cardview);

        adminElectronicsCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddProductsMainActivity.this, AdminAddProductSelectSubcategoryActivity.class);
                intent.putExtra("category", "electronics");
                startActivity(intent);
            }
        });

        adminMenCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddProductsMainActivity.this, AdminAddProductSelectSubcategoryActivity.class);
                intent.putExtra("category", "men");
                startActivity(intent);
            }
        });

        adminWomenCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddProductsMainActivity.this, AdminAddProductSelectSubcategoryActivity.class);
                intent.putExtra("category", "women");
                startActivity(intent);
            }
        });

        adminBabyCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddProductsMainActivity.this, AdminAddProductSelectSubcategoryActivity.class);
                intent.putExtra("category", "baby");
                startActivity(intent);
            }
        });

        adminToysCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddProductsMainActivity.this, AdminAddProductSelectSubcategoryActivity.class);
                intent.putExtra("category", "toys");
                startActivity(intent);
            }
        });

        adminGroceryCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddProductsMainActivity.this, AdminAddProductSelectSubcategoryActivity.class);
                intent.putExtra("category", "grocery");
                startActivity(intent);
            }
        });

    }
}