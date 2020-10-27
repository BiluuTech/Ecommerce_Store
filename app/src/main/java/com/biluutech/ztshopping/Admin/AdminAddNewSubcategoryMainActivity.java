package com.biluutech.ztshopping.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.biluutech.ztshopping.R;

public class AdminAddNewSubcategoryMainActivity extends AppCompatActivity {

    private CardView adminAddingSubcategoryElectronicsCardview, adminAddingSubcategoryMenCardview, adminAddingSubcategoryWomenCardview, adminAddingSubcategoryBabyCardview, adminAddingSubcategoryToysCardview, adminAddingSubcategoryGroceryCardview;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_subcategory_main);

        toolbar = findViewById(R.id.adminAddNewSubcategoryMainMainToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adminAddingSubcategoryElectronicsCardview = findViewById(R.id.admin_adding_subcategory_electronics_cardview);
        adminAddingSubcategoryMenCardview = findViewById(R.id.admin_adding_subcategory_men_cardview);
        adminAddingSubcategoryWomenCardview = findViewById(R.id.admin_adding_subcategory_women_cardview);
        adminAddingSubcategoryBabyCardview = findViewById(R.id.admin_adding_subcategory_baby_cardview);
        adminAddingSubcategoryToysCardview = findViewById(R.id.admin_adding_subcategory_toys_cardview);
        adminAddingSubcategoryGroceryCardview = findViewById(R.id.admin_adding_subcategory_grocery_cardview);


        adminAddingSubcategoryElectronicsCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddNewSubcategoryMainActivity.this, AdminAddNewSubcategoryActivity.class);
                intent.putExtra("category", "electronics");
                startActivity(intent);
            }
        });

        adminAddingSubcategoryMenCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddNewSubcategoryMainActivity.this, AdminAddNewSubcategoryActivity.class);
                intent.putExtra("category", "men");
                startActivity(intent);
            }
        });

        adminAddingSubcategoryWomenCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddNewSubcategoryMainActivity.this, AdminAddNewSubcategoryActivity.class);
                intent.putExtra("category", "women");
                startActivity(intent);
            }
        });

        adminAddingSubcategoryBabyCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddNewSubcategoryMainActivity.this, AdminAddNewSubcategoryActivity.class);
                intent.putExtra("category", "baby");
                startActivity(intent);
            }
        });

        adminAddingSubcategoryToysCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddNewSubcategoryMainActivity.this, AdminAddNewSubcategoryActivity.class);
                intent.putExtra("category", "toys");
                startActivity(intent);
            }
        });

        adminAddingSubcategoryGroceryCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminAddNewSubcategoryMainActivity.this, AdminAddNewSubcategoryActivity.class);
                intent.putExtra("category", "grocery");
                startActivity(intent);
            }
        });


    }
}